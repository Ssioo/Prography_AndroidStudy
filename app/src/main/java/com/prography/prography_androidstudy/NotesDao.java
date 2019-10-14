package com.prography.prography_androidstudy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.prography.prography_androidstudy.Note;

import java.util.List;

@Dao
public interface NotesDao {

    /*Insert Note*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note... notes);

    /* Note 검색 with id in DB */
    @Query("SELECT * FROM notes WHERE id = :id")
    List<Note> findNoteswithID(int id);

    /* Note 전부 검색 */
    @Query("SELECT * FROM notes")
    List<Note> findAllNotes();

    /* Delete Note */
    @Delete
    void DeleteNotes(Note... notes);
}
