package uk.gov.hmcts.cp.casedoc.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.hmcts.cp.casedoc.domain.Note;
import uk.gov.hmcts.cp.casedoc.repo.NoteRepository;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

  private final NoteRepository repo;

  public NoteController(NoteRepository repo) {
    this.repo = repo;
  }

  @PostMapping
  public ResponseEntity<Note> create(@RequestBody Map<String, String> req) {
    String text = req.getOrDefault("text", "").trim();
    if (text.isBlank()) {
      throw new IllegalArgumentException("text is required");
    }
    Note n = new Note();
    n.setText(text);
    Note s = repo.save(n);
    return ResponseEntity.created(URI.create("/api/v1/notes/" + s.getId())).body(s);
  }

  @GetMapping("{id}")
  public ResponseEntity<Note> get(@PathVariable UUID id) {
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}
