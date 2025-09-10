package uk.gov.hmcts.cp.casedoc.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("case-document-knowledge-service")
        .description("Primary API (used by the UI): answers questions with citations")
        .version("0.1.0")
        .license(new License().name("MIT")))
      .servers(List.of(new Server().url("/")));
  }
}
