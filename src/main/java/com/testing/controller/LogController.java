package com.testing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testing.entity.Log;
import com.testing.services.LogService;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService service;
    
    @PostMapping("/save")
    public ResponseEntity<Mono<Log>> postMethodName(@RequestBody Log log) {
        return ResponseEntity.ok(service.save(log));
    }
    
}
