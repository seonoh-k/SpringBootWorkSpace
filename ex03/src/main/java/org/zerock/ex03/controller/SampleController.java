package org.zerock.ex03.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/sample")
@Controller
public class SampleController {

    @GetMapping("/ex1")
    public void ex1() {
        log.info("ex1");
    }
}
