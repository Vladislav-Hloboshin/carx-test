package ru.vladislav.carx.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class SchedulerController {

    @Value("${scheduler.disabled}")
    private boolean disabled;

    private final AnalyticalDataController analyticalDataController;

    @Autowired
    public SchedulerController(AnalyticalDataController analyticalDataController) {
        this.analyticalDataController = analyticalDataController;
    }

    @Scheduled(fixedDelayString = "${scheduler.fixedDelay}")
    public void runAnalyticalDataCreation(){
        if(disabled) return;
        analyticalDataController.createAnalyticalData();
    }

}
