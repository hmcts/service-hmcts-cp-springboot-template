package uk.gov.hmcts.cp.casedoc.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {

  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(nullable = false)
  private String text;

  @PrePersist
  public void pre() {
    if (id == null) {
      id = UUID.randomUUID();
    }
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String t) {
    this.text = t;
  }
}
