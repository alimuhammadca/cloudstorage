package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }

    public void addNote(Note note) {
        noteMapper.insert(note);
    }

    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }
}
