package com.acko.template.controller;

import com.acko.template.constant.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = Constants.API_V1)
public class TemplateController {

    @GetMapping(
        value = Constants.EVENT_GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HashMap<String, String>> get() {
        return ResponseEntity.ok(new HashMap<>());
    }

}
