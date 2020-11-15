package pl.dernovyi.homework8backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.dernovyi.homework8backend.model.Note;
import pl.dernovyi.homework8backend.service.NoteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class NoteControllerTestMVC {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    NoteController noteController;

    @Test
    void test_get_all_notes_success() throws Exception {
        Mockito.when(noteService.findAllNotes()).thenReturn(getListNotes());
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Is.is(1)))
                .andExpect(jsonPath("$[0].text", Is.is("Hello World")))
                .andExpect(jsonPath("$[1].id", Is.is(2)))
                .andExpect(jsonPath("$[1].text", Is.is("New text...")))
        ;

    }

    @Test
    void test_add_new_note() throws Exception {
        Mockito.when(noteService.addNewNote(Mockito.any(Note.class))).thenReturn(getNote());

        mockMvc.perform(post("/notes")
                .content(new ObjectMapper().writeValueAsString(getNote()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text", Is.is("Hello World")));

    }
    @Test
    void test_add_short_note() throws Exception {
        Note note = new Note("Yo");
        note.setId(1l);
        Mockito.when(noteService.addNewNote(Mockito.any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/notes")
                .content(new ObjectMapper().writeValueAsString(note))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void test_get_by_id_success() throws Exception {
        Mockito.when(noteService.findNoteById(Mockito.any(Long.class))).thenReturn(Optional.of(getNote()));
        mockMvc.perform(get("/notes/{id}", 0))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.text", Is.is("Hello World")));
    }

    @Test
    void test_get_by_id_fail_404_not_found() throws Exception {
        mockMvc.perform(get("/notes/{id}", 2))
                .andExpect(status().isNotFound());
    }
    @Test
    void test_replace_note() throws Exception {
        Mockito.when(noteService.findNoteById(getNote().getId())).thenReturn(Optional.of(getNote()));
        Mockito.when(noteService.addNewNote(Mockito.any(Note.class))).thenReturn(getNote());
        mockMvc.perform(put("/notes/{id}", getNote().getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(getNote())))
                .andExpect(status().isOk());
        Mockito.verify(noteService, times(1)).addNewNote(getNote());
        Mockito.verify(noteService, times(1)).findNoteById(getNote().getId());
        verifyNoMoreInteractions(noteService);
    }



    @Test
    void test_remove_note_by_id() throws Exception {
        doNothing().when(noteService).delete(getNote().getId());
        mockMvc.perform(
                delete("/notes/{id}", getNote().getId()))
                .andExpect(status().isOk());

        Mockito.verify(noteService, times(1)).delete(getNote().getId());
        verifyNoMoreInteractions(noteService);
    }

    private List<Note> getListNotes(){
        List<Note> noteList = new ArrayList<>();
        Note n1 = new Note("Hello World");
        Note n2 = new Note("New text...");
        n1.setId(1L);
        n2.setId(2L);
        noteList.add(n1);
        noteList.add(n2);
        return noteList;
    }
    private Note getNote(){
        Note n1 = new Note("Hello World");
        n1.setId(1L);
        return n1;
    }
}
