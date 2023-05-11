package net.ictcampus.mobapp_trading_sim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "username")
    String username;
    @ColumnInfo(name = "password")
    String password;

    public User() {
        //Empty Constructor For Firebase
    }


    public User(String username, String password)
    {
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.password = password;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}