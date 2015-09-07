package org.canary.serinus.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public final class RootController {

    @RequestMapping("/")
    public String root() {
        return "Hello, this is Serinus!";
    }
}