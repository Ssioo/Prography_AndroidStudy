package com.prography.prography_androidstudy.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodosDao {

    /*Insert Todo*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo... todos);

    /* Todo 검색 with id in DB */
    @Query("SELECT * FROM Todo WHERE id = :id")
    Todo findNotewithID(int id);

    /* Todo 검색 with Title in DB */
    @Query("SELECT * FROM Todo WHERE title LIKE :title")
    List<Todo> findNoteswithTitle(String title);

    /* Todo 전부 검색 */
    @Query("SELECT * FROM Todo")
    List<Todo> findAllNotes();

    /* Delete Todo */
    @Delete
    void DeleteNotes(Todo... todos);
}
