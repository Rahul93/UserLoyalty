package com.acko.template.threadlocal;

import java.util.UUID;

public class ThreadContext {

  private static ThreadLocal<RequestContext> requestContext = new ThreadLocal<>();

  private ThreadContext() {}

  public static ThreadLocal<RequestContext> getRequestContext() {
    return requestContext;
  }

  public static void setThreadContext() {
    String traceId = UUID.randomUUID().toString();
    requestContext.set(new RequestContext(traceId));
    LoggerContext.init(traceId);
  }

  public static void clear() {
    requestContext.remove();
    LoggerContext.clear();
  }
}
