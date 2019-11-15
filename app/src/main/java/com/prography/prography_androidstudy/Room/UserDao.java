package com.prography.prography_androidstudy.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    /* Insert User */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    /* User 검색 with Email */
    @Query("SELECT * FROM users WHERE email = (:email)")
    List<User> findUser(String email);

    /* User 검색 with Email & PW For Login*/
    @Query("SELECT * FROM users WHERE email = (:email) AND pw = (:pw)")
    List<User> userLogin(String email, String pw);

    /* User 검색 with id in DB*/
    @Query("SELECT * FROM users WHERE id = :id")
    List<User> findUserwithID(int id);

    /* Delete User */
    @Delete
    void deleteUsers(User... users);

    /* Update User */
    @Update(onConflict = OnConflictStrategy.ABORT)
    void updateUsers(User... users);


}
