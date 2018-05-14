package ro.ggabrielli.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ggabrielli.statistics.processor.SonarDataProcessor;

@RestController
public class MainController {

    private SonarDataProcessor sonarDataProcessor;

    public MainController(@Autowired SonarDataProcessor sonarDataProcessor){
        this.sonarDataProcessor = sonarDataProcessor;
    }

    @RequestMapping(value = "/triggerUpdate", method = RequestMethod.GET)
    public void triggerUpdate(){
        sonarDataProcessor.performDataUpdate();
    }
}
