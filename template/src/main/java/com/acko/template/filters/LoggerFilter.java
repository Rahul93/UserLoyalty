package com.acko.template.filters;

import com.acko.template.threadlocal.ThreadContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Log4j2
public class LoggerFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    try {
      if (servletRequest instanceof HttpServletRequest) {
        ThreadContext.setThreadContext();
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      ThreadContext.clear();
    }
  }

  @Override
  public void destroy() {}
}
