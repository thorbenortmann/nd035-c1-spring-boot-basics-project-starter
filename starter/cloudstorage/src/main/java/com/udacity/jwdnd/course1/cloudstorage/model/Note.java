package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private int userid;

    public Note(Integer noteid, String notetitle, String notedescription, int userid) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Integer getNoteId() {
        return noteid;
    }

    public void setNoteId(Integer noteId) {
        this.noteid = noteId;
    }

    public String getNoteTitle() {
        return notetitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.notetitle = noteTitle;
    }

    public String getNoteDescription() {
        return notedescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.notedescription = noteDescription;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userid) {
        this.userid = userid;
    }
}
