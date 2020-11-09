package pl.dernovyi.homework8backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dernovyi.homework8backend.exception.NoteNotFoundException;
import pl.dernovyi.homework8backend.model.Note;
import pl.dernovyi.homework8backend.repo.NoteRepo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/notes")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

    private NoteRepo noteRepo;
    @Autowired
    public NoteController(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(){

        List<Note> allNotes = noteRepo.findAll();
        return new ResponseEntity<>(allNotes, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Note> addNewNote(@RequestBody  Note note){
        Note newNote = new Note();
        newNote.setText(newNote.getText());
        return new ResponseEntity<>(noteRepo.save(note), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id){
        return new ResponseEntity<>(noteRepo.findById(id)
                .orElseThrow(()-> new NoteNotFoundException(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNote(@RequestBody Note newNote, @PathVariable Long id ) {

        return new ResponseEntity(noteRepo.findById(id)
        .map(note -> {
            note.setText(newNote.getText());
            note.setUpdatedOn(LocalDateTime.now());
            return noteRepo.save(note);
        }).orElseThrow(() -> new NoteNotFoundException(id)), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void removeNote(@PathVariable Long id){
        noteRepo.deleteById(id);
    }
}
