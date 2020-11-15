package pl.dernovyi.homework8backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.dernovyi.homework8backend.exception.NoteNotFoundException;
import pl.dernovyi.homework8backend.model.Note;
import pl.dernovyi.homework8backend.repo.NoteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private NoteRepo noteRepo;

    @Autowired
    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public List<Note> findAllNotes(){
        return noteRepo.findAll();
    }

    public Note  addNewNote(Note note){
        return noteRepo.save(note);
    }

    public Optional<Note> findNoteById(Long id){
       return noteRepo.findById(id);
    }

    public void delete(Long id){
        noteRepo.deleteById(id);
    }

}
