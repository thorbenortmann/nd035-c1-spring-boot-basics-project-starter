package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    List<Note> getNotesForUser(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription}, userid=#{userid} WHERE noteid=#{noteid}")
    void updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteid} AND userid=#{userid}")
    void deleteById(Integer noteid, Integer userid);
}
