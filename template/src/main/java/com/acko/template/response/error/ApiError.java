package com.acko.template.response.error;

import lombok.Builder;

@Builder
public class ApiError {
    private Boolean success;
    private String traceId;
    private ErrorObject error;
}
