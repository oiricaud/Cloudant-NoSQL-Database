package com.restuarants.smart.cloudanat_no_sql_database;

import java.util.Date;
import java.util.UUID;

/**
 * Created by oscarricaud on 11/25/16.
 */

public class User {
    private String _id;
    private String firstName;
    private String lastName;
    private Date creationDate;
    private int age;
    public User()
    {
        new User("firstNameTest", "lastNameTest", new Date(), -1);
    }
    public User(String firstName, String lastName, Date creationDate, int age) {
        this._id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = creationDate;
        this.age = age;
    }
    public String getId() {
        return _id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}

