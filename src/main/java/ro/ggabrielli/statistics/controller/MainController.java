package ro.ggabrielli.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ggabrielli.statistics.domain.elasticsearch.Car;
import ro.ggabrielli.statistics.processor.SonarDataProcessor;
import ro.ggabrielli.statistics.repository.elasticsearch.CarRepository;

import java.util.Date;

/**
 * Created by gobi on 5/9/2018.
 */
@RestController
public class MainController {

    @Autowired
    private SonarDataProcessor sonarDataProcessor;



    @RequestMapping(value = "/triggerUpdate", method = RequestMethod.GET)
    public void triggerUpdate(){
        sonarDataProcessor.performDataUpdate();
    }
}
