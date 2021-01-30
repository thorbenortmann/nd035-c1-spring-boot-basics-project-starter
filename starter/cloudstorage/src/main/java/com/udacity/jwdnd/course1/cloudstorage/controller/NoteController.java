package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("add-note")
    public String addNote(Authentication authentication,
                          @ModelAttribute("newNote") NoteForm newNote,
                          Model model) {

        var userName = authentication.getName();

        if (newNote.getNoteId().isEmpty()) {
            this.noteService.addNote(newNote, userName);
        } else {
            this.noteService.updateNote(newNote, userName);
        }

        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("delete-note/{noteId}")
    public String deleteNote(Authentication authentication,
                             @PathVariable int noteId,
                             Model model) {
        //TODO: Users should only be able to delete their own notes.
        this.noteService.deleteNoteById(noteId);
        model.addAttribute("success", true);
        return "result";
    }
}
