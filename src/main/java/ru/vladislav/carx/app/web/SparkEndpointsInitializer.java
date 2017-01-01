package ru.vladislav.carx.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladislav.carx.app.controller.EndpointsController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

import static spark.Spark.*;

@Service
public class SparkEndpointsInitializer {

    private static final String OK = "OK";

    private final EndpointsController controller;

    @Autowired
    public SparkEndpointsInitializer(EndpointsController controller) {
        this.controller = controller;
    }

    @PostConstruct
    public void construct(){
        put("/data",
                (req, res) -> controller.storeData( UUID.fromString(req.queryParams("uuid")), req.queryParams("json") ),
                (res) -> OK);

        put("/statistic",
                (req, res) -> controller.storeStatistic( UUID.fromString(req.queryParams("uuid")), Long.parseLong(req.queryParams("activity")) ),
                (res) -> OK);

        get("/data/:uuid", (req, res) -> controller.getLastData(UUID.fromString(req.params("uuid")) ));
    }

    @PreDestroy
    public void destroy(){
        stop();
    }

}
