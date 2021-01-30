package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public List<Note> getNotesForUser(String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        return this.noteMapper.getNotesForUser(userId);
    }

    public void addNote(NoteForm noteForm, String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        var newNote = new Note(null, noteForm.getTitle(), noteForm.getDescription(), userId);
        this.noteMapper.insertNote(newNote);
    }

    public void updateNote(NoteForm noteForm, String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        var noteId = Integer.parseInt(noteForm.getNoteId());
        var newNote = new Note(noteId, noteForm.getTitle(), noteForm.getDescription(), userId);
        this.noteMapper.updateNote(newNote);
    }

    public void deleteNoteById(int noteId) {
        this.noteMapper.deleteById(noteId);
    }
}
