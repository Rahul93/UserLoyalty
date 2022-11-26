package com.acko.template.controller;

import com.acko.template.constant.Constants;
import com.acko.template.response.success.ApiSuccess;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*") // security issue
@RestController
@Log4j2
@RequestMapping(value = Constants.HEALTH_CHECK)
public class HealthCheckController {
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccess<Object>> getHeartBeat() {
        return ApiSuccess.ok("lub dub");
    }

}
