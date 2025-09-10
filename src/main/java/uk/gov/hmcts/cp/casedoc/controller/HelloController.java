package uk.gov.hmcts.cp.casedoc.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

  @GetMapping("/hello")
  public ResponseEntity<Map<String, Object>> hello(@RequestParam(defaultValue = "World") String name) {
    return ResponseEntity.ok(Map.of("message", "Hello, " + name + "!", "at", Instant.now().toString()));
  }
}
