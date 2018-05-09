package ro.ggabrielli.statistics.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ggabrielli.statistics.repository.jpa.IssueRepository;
import ro.ggabrielli.statistics.repository.jpa.ProjectRepository;

/**
 * Created by gobi on 5/9/2018.
 */
@Component
public class SonarDataProcessor {

    private IssueRepository issueRepository;

    private ProjectRepository projectRepository;


    public SonarDataProcessor(@Autowired IssueRepository issueRepository, @Autowired ProjectRepository projectRepository){
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
    }

    public void performDataUpdate() {
        issueRepository.findAll();
        projectRepository.findAll();
    }
}
