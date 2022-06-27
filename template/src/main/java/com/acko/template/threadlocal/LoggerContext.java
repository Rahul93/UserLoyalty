package com.acko.template.threadlocal;

public class LoggerContext {
  public static final String TRACE_ID = "trace_id";

  private LoggerContext() {}

  public static void init(final String traceId) {
    org.apache.logging.log4j.ThreadContext.put(TRACE_ID, traceId);
  }

  public static void clear() {
    org.apache.logging.log4j.ThreadContext.clearAll();
  }
}
