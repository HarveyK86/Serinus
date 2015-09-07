package org.canary.serinus.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public final class RootController {

    private static final String ROOT_MESSAGE = "Hello, this is Serinus";

    private static final Logger LOGGER = Logger.getLogger(RootController.class);

    private RootController() {

        super();

        LOGGER.trace("constructed");
    }

    @RequestMapping("/")
    public String root() {

        LOGGER.info("root");

        return ROOT_MESSAGE;
    }
}