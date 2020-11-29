package alisha.digipodium.roomdatabasedemo1;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Add Database entites
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    //create Database instance
    private static RoomDB database;

    //Define database name
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){
        //check condition
        if (database == null){
            //when database is null
            //Initialize databse
            database = Room.databaseBuilder(context.getApplicationContext()
            ,RoomDB.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }

        //Return Database
        return database;
    }

    //create Dao
    public  abstract MainDao mainDao();
}
