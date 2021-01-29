package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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

    public void addNote(String noteTitle, String noteDescription, String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        this.noteMapper.insert(new Note(null, noteTitle, noteDescription, userId));
    }

    public void deleteNoteById(int noteId) {
        this.noteMapper.deleteById(noteId);
    }
}
