package com.onetwo.mongddang.domain.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/cicdtest")
public class controller {

    @GetMapping("")
    public String cicdTest() {
        log.info("ci/cd test start!");
        return "Server communication is working well. Congratulations!";
    }
}
