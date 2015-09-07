package org.canary.serinus;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Serinus {

    private static final Logger LOGGER = Logger.getLogger(Serinus.class);

    protected Serinus() {

        super();

        LOGGER.trace("constructed");
    }

    public static final void main(final String[] args) {

        LOGGER.info("main[args=" + args + "]");

        SpringApplication.run(Serinus.class, args);
    }
}