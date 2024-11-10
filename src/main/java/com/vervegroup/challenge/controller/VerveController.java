package com.vervegroup.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vervegroup.challenge.service.VerveService;

@RestController
@Validated
public class VerveController {

    @Autowired
    private VerveService verveService;

    @GetMapping("/api/verve/accept")
    public ResponseEntity<String> accept(@RequestParam("id") int id,
                         @RequestParam(value = "endpoint", required = false) String endpoint) {

        verveService.handleRequest(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
