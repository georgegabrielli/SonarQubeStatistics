package ro.ggabrielli.statistics.processor;

import jdk.nashorn.internal.ir.Block;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.domain.component.Path;
import ro.ggabrielli.statistics.domain.elasticsearch.ESModule;
import ro.ggabrielli.statistics.domain.enumerated.IssueSeverity;
import ro.ggabrielli.statistics.domain.enumerated.IssueStatus;
import ro.ggabrielli.statistics.domain.enumerated.IssueType;
import ro.ggabrielli.statistics.domain.enumerated.ProjectScope;
import ro.ggabrielli.statistics.domain.sonarqube.Issue;
import ro.ggabrielli.statistics.domain.sonarqube.Project;
import ro.ggabrielli.statistics.repository.elasticsearch.ESModuleRepository;

import ro.ggabrielli.statistics.repository.jpa.component.GroupRepository;
import ro.ggabrielli.statistics.repository.jpa.component.PathRepository;
import ro.ggabrielli.statistics.repository.jpa.sonarqube.IssueRepository;
import ro.ggabrielli.statistics.repository.jpa.sonarqube.ProjectRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SonarDataProcessor {

    private final ESModuleRepository esModuleRepository;

    private final IssueRepository issueRepository;

    private final ProjectRepository projectRepository;

    private final PathRepository pathRepository;

    private final GroupRepository groupRepository;

    public SonarDataProcessor (
            @Autowired IssueRepository issueRepository,
            @Autowired ProjectRepository projectRepository,
            @Autowired ESModuleRepository esModuleRepository,
            @Autowired PathRepository pathRepository,
            @Autowired GroupRepository groupRepository) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.esModuleRepository = esModuleRepository;
        this.pathRepository = pathRepository;
        this.groupRepository = groupRepository;
    }

    public void performDataUpdate () {
        List<Issue> issues = issueRepository.findAll();
        List<Project> files = projectRepository.findAll();
        List<Path> paths = pathRepository.findAll();


        List<Project> filesToBeAnalyzed = getFilesNeededForAnalysis(files, paths);

        Map<String, Set<String>> directoryFilesUuidMap = getDirectoryFilesUuidMap(files, filesToBeAnalyzed);

        Map<String, Set<String>> componentFilesUuidMap = getComponentFilesUuidMap(directoryFilesUuidMap);

        checkForSameDayAnalysis();

        for (Map.Entry<String, Set<String>> entry : componentFilesUuidMap.entrySet()) {
            Set<String> directoryFiles = entry.getValue();
            if (!CollectionUtils.isEmpty(directoryFiles)) {
                long codeSmells = 0;
                long vulnerabilities = 0;
                long bugs = 0;
                long majorIssues = 0;
                long blockers = 0;
                long minorIssues = 0;
                long info = 0;
                for (String f : directoryFiles) {
                    List<Issue> fileIssues = issues.stream()
                                                   .filter(issue -> f.equals(issue.getComponentUUID())
                                                                    && IssueStatus.OPEN.getStatus().equals(issue.getStatus()))
                                                   .collect(Collectors.toList());

                    codeSmells += countIssues(fileIssues, IssueType.CODE_SMELL);
                    vulnerabilities += countIssues(fileIssues, IssueType.VULNERABILITY);
                    bugs += countIssues(fileIssues, IssueType.BUG);

                    majorIssues += countSeverities(fileIssues, IssueSeverity.MAJOR);
                    blockers += countSeverities(fileIssues, IssueSeverity.BLOCKER);
                    minorIssues += countSeverities(fileIssues, IssueSeverity.MINOR);
                    info += countSeverities(fileIssues, IssueSeverity.INFO);
                }

                ESModule module = ESModule.builder()
                                          .groupName(entry.getKey())
                                          .bugs(bugs)
                                          .codeSmells(codeSmells)
                                          .vulnerabilities(vulnerabilities)
                                          .majorIssues(majorIssues)
                                          .blockers(blockers)
                                          .minorIssues(minorIssues)
                                           .info(info)
                                          .fileDate(new Date())
                                          .build();

                esModuleRepository.save(module);
            }
        }
    }

    private long countSeverities (List<Issue> fileIssues, IssueSeverity severity) {
        return fileIssues.stream().filter(issue -> severity.getSeverity().equals(issue.getSeverity())).count();
    }

    private void checkForSameDayAnalysis () {
        Iterable<ESModule> modules = esModuleRepository.findAll();

        for (ESModule module : modules) {
            if (DateUtils.isSameDay(module.getFileDate(), new Date())) {
                esModuleRepository.delete(module);
            }
        }
    }

    private List<Project> getFilesNeededForAnalysis (List<Project> files, List<Path> paths) {
        return files.stream()
                    .filter(file -> fileNeedsAnalysis(file, paths))
                    .collect(Collectors.toList());
    }

    private Map<String, Set<String>> getComponentFilesUuidMap (Map<String, Set<String>> directoryFilesUuidMap) {
        Map<String, Set<String>> componentFilesUuidMap = new HashMap<>();

        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            componentFilesUuidMap.put(group.getName(), new HashSet<>());
            for (Path path : group.getPaths()) {
                if (directoryFilesUuidMap.containsKey(path.getValue())) {
                    componentFilesUuidMap.get(group.getName()).addAll(directoryFilesUuidMap.get(path.getValue()));
                }
            }
        }
        return componentFilesUuidMap;
    }

    private boolean fileNeedsAnalysis (Project file, List<Path> paths) {
        List<String> pathsName = paths.stream().map(Path::getValue).collect(Collectors.toList());

        return pathsName.contains(file.getName());
    }

    private long countIssues (List<Issue> fileIssues, IssueType issueType) {
        return fileIssues.stream()
                         .filter(issue -> issueType.getType() == issue.getIssueType())
                         .count();
    }

    private Map<String, Set<String>> getDirectoryFilesUuidMap (List<Project> files, List<Project> filesToBeAnalyzed) {
        Map<String, Set<String>> directoryFilesUuidMap = new HashMap<>();
        Map<String, String> directoryNameUuidMap = new HashMap<>();

        List<Project> directories = filesToBeAnalyzed.stream()
                                                     .filter(dir -> ProjectScope.DIRECTORY.getScope().equals(dir.getScope()))
                                                     .collect(Collectors.toList());

        for (Project directory : directories) {
            directoryFilesUuidMap.put(directory.getName(), new HashSet<>());
            directoryNameUuidMap.put(directory.getUuid(), directory.getName());
        }
        List<Project> file = files.stream()
                                  .filter(dir -> ProjectScope.FILE.getScope().equals(dir.getScope()))
                                  .collect(Collectors.toList());

        for (Project f : file) {
            String[] paths = f.getPathUuid().split("\\.");
            String correspondingDirectory = paths[paths.length - 1];

            if (isDirectoryUnderAnalysis(filesToBeAnalyzed, correspondingDirectory)) {
                directoryFilesUuidMap.get(directoryNameUuidMap.get(correspondingDirectory)).add(f.getUuid());
            }
        }
        return directoryFilesUuidMap;
    }

    private boolean isDirectoryUnderAnalysis (List<Project> filesToBeAnalyzed, String correspondingDirectory) {
        return filesToBeAnalyzed.stream().filter(file -> correspondingDirectory.equals(file.getUuid())).count() > 0;
    }
}
