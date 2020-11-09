package pl.dernovyi.homework8backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dernovyi.homework8backend.model.Note;



@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
}
