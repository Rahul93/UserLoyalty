package com.acko.template.threadlocal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestContext {
  private String traceId;
}
