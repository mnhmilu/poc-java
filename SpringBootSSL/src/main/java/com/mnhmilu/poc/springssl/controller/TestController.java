package com.mnhmilu.poc.springssl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/echo")
    public ResponseEntity<String> getEcho(HttpServletRequest request) {
       // log.info("Echo | Current Date Time = {}, IP = {}", DateTimeUtils.currentTimeToFiFormat(), getClientIp(request)); some test recor
        return ResponseEntity.ok("Ok");
    }
}
