package uk.gov.hmcts.cp.casedoc.config;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component("correlationMdcFilter")
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestContextFilter implements Filter {

  private static final String CLUSTER = System.getenv().getOrDefault("CLUSTER_NAME", "local");

  private static final String REGION = System.getenv().getOrDefault("REGION", "local");

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
    try {
      HttpServletRequest r = (HttpServletRequest) req;
      String cid = r.getHeader("X-Correlation-Id");
      if (cid == null || cid.isBlank()) {
        cid = UUID.randomUUID().toString();
      }
      MDC.put("correlationId", cid);
      MDC.put("cluster", CLUSTER);
      MDC.put("region", REGION);
      MDC.put("path", r.getRequestURI());
      chain.doFilter(req, res);
    } finally {
      MDC.clear();
    }
  }
}
