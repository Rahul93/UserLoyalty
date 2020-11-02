package com.acko.template.controller;

import com.acko.template.constant.Constants;
import com.acko.template.response.success.ApiSuccess;
import io.micrometer.core.lang.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = Constants.API_V1)
public class TemplateController {

    @GetMapping(
        value = Constants.EVENT_GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccess<Object>> get(@RequestParam Integer a) {
        return ApiSuccess.ok(new HashMap<>());
    }

    @PostMapping(
        value = Constants.EVENT_PUSH,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccess<Object>> post(@RequestBody @NonNull HashMap<String, String> hm) {
        return ApiSuccess.ok(hm);
    }
}
