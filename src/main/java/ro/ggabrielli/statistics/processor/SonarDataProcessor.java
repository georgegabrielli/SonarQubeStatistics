package ro.ggabrielli.statistics.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.domain.component.Path;
import ro.ggabrielli.statistics.domain.elasticsearch.ESModule;
import ro.ggabrielli.statistics.domain.enumerated.IssueStatus;
import ro.ggabrielli.statistics.domain.enumerated.IssueType;
import ro.ggabrielli.statistics.domain.enumerated.ProjectScope;
import ro.ggabrielli.statistics.domain.sonarqube.Issue;
import ro.ggabrielli.statistics.domain.sonarqube.Project;
import ro.ggabrielli.statistics.repository.elasticsearch.ESModuleRepository;
import ro.ggabrielli.statistics.repository.jpa.component.ComponentRepository;

import ro.ggabrielli.statistics.repository.jpa.component.PathRepository;
import ro.ggabrielli.statistics.repository.jpa.sonarqube.IssueRepository;
import ro.ggabrielli.statistics.repository.jpa.sonarqube.ProjectRepository;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
public class SonarDataProcessor {

    private final ESModuleRepository esModuleRepository;

    private final IssueRepository issueRepository;

    private final ProjectRepository projectRepository;

    private final PathRepository pathRepository;

    public SonarDataProcessor(@Autowired IssueRepository issueRepository,
                              @Autowired ProjectRepository projectRepository,
                              @Autowired ESModuleRepository esModuleRepository,
                              @Autowired PathRepository pathRepository) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.esModuleRepository = esModuleRepository;
        this.pathRepository = pathRepository;
    }

    public void performDataUpdate() {
        List<Issue> issues = issueRepository.findAll();
        List<Project> files = projectRepository.findAll();
        List<Path> paths = pathRepository.findAll();

        List<Project> filesToBeAnalyzed = files.stream().filter(file -> fileNeedsAnalysis(file, paths)).collect(Collectors.toList());


        Map<String, Set<String>> directoryFilesUuidMap = getDirectoryFilesUuidMap(filesToBeAnalyzed);

        for (Map.Entry<String, Set<String>> entry : directoryFilesUuidMap.entrySet()) {
            Set<String> directoryFiles = entry.getValue();
            if (!CollectionUtils.isEmpty(directoryFiles)) {
                long codeSmells = 0;
                long vulnerabilities = 0;
                long bugs = 0;
                for (String f : directoryFiles) {
                    List<Issue> fileIssues = issues.stream()
                            .filter(issue -> f.equals(issue.getComponentUUID())
                                    && IssueStatus.OPEN.getStatus().equals(issue.getStatus()))
                            .collect(Collectors.toList());

                    codeSmells += countIssues(fileIssues, IssueType.CODE_SMELL);
                    vulnerabilities += countIssues(fileIssues, IssueType.VULNERABILITY);
                    bugs += countIssues(fileIssues, IssueType.BUG);
                }

                ESModule module = ESModule.builder()
                        .groupName(entry.getKey())
                        .bugs(bugs)
                        .codeSmells(codeSmells)
                        .vulnerabilities(vulnerabilities)
                        .fileDate(new Date())
                        .build();

                esModuleRepository.save(module);
            }
        }
    }

    private boolean fileNeedsAnalysis(Project file, List<Path> paths) {
        List<String> pathsName = paths.stream().map(Path::getValue).collect(Collectors.toList());

        return pathsName.contains(file.getName());
    }

    private long countIssues(List<Issue> fileIssues, IssueType issueType) {
        return fileIssues.stream()
                .filter(issue -> issueType.getType() == issue.getIssueType())
                .count();
    }

    private Map<String, Set<String>> getDirectoryFilesUuidMap(List<Project> files) {
        Map<String, Set<String>> directoryFilesUuidMap = new HashMap<>();

        List<Project> directories = files.stream()
                .filter(dir -> ProjectScope.DIRECTORY.getScope().equals(dir.getScope()))
                .collect(Collectors.toList());

        for (Project directory : directories) {
            directoryFilesUuidMap.put(directory.getUuid(), new HashSet<>());
        }
        List<Project> file = files.stream()
                .filter(dir -> ProjectScope.FILE.getScope().equals(dir.getScope()))
                .collect(Collectors.toList());

        for (Project f : file) {
            String[] paths = f.getPathUuid().split("\\.");
            String correspondingDirectory = paths[paths.length - 1];

            directoryFilesUuidMap.get(correspondingDirectory).add(f.getUuid());
        }
        return directoryFilesUuidMap;
    }
}
