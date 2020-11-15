package pl.dernovyi.homework8backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dernovyi.homework8backend.exception.NoteNotFoundException;
import pl.dernovyi.homework8backend.model.Note;
import pl.dernovyi.homework8backend.repo.NoteRepo;
import pl.dernovyi.homework8backend.service.NoteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/notes")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

   private  NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(){

        List<Note> allNotes = noteService.findAllNotes();
        return new ResponseEntity<>(allNotes, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Note> addNewNote(@RequestBody  Note note){
        if(note.getText().length()<3 ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(noteService.addNewNote(note), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id){
        Optional<Note> byId = noteService.findNoteById(id);
        return byId.map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNote(@RequestBody Note newNote, @PathVariable Long id ) {

        return new ResponseEntity(noteService.findNoteById(id)
        .map(note -> {
            note.setText(newNote.getText());
            note.setUpdatedOn(LocalDateTime.now());
            return noteService.addNewNote(note);
        }).orElseThrow(() -> new NoteNotFoundException(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void removeNote(@PathVariable Long id){
        noteService.delete(id);
    }
}
