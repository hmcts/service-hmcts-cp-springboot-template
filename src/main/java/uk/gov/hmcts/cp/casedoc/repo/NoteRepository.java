package uk.gov.hmcts.cp.casedoc.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.gov.hmcts.cp.casedoc.domain.Note;

public interface NoteRepository extends JpaRepository<Note, UUID> { }
