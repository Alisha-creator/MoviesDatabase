package alisha.digipodium.roomdatabasedemo1;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete Query
    @Delete
    void delete(MainData mainData);

    //Delete all query
    @Delete
    void reset(List<MainData> mainData);

    //udpate query
    @Query("UPDATE table_name SET text =:sText WHERE ID =:sID")
    void update(int sID,String sText);

    //Get al data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();

}
