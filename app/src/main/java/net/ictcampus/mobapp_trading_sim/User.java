package net.ictcampus.mobapp_trading_sim;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="user_id")
    @NonNull
    public int uid;

    @ColumnInfo(name = "first_name")
    @NonNull
    public String firstName;

    @ColumnInfo(name = "last_name")
    @NonNull
    public String lastName;

    @ColumnInfo(name = "username")
    @NonNull
    String username;
    @ColumnInfo(name = "password")
    @NonNull
    String password;


    public User(String username, String password)
    {
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.password = password;
    }
}