package uk.gov.hmcts.cp.casedoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.hmcts.cp.casedoc.domain.Answer;

@RestController
@RequestMapping("/api/v1/answers")
public class AnswerController {

  @GetMapping("/example")
  public ResponseEntity<Answer> example() {
    return ResponseEntity.ok(new Answer(
      "This is a placeholder answer.",
      List.of("https://example.org/source1", "https://example.org/source2")
    ));
  }
}
