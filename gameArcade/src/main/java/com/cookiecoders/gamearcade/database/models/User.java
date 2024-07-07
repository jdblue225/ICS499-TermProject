package com.cookiecoders.gamearcade.database.models;

import java.util.Date;

public class User {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String usertype;
    private Date createdAt;


    public User() {}
    public User(String username, String firstname, String lastname, String email, String password, String usertype) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.usertype = usertype;
    }

    public User(Integer id, String username, String firstname, String lastname, String email, String password, String usertype, Date createdAt) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.usertype = usertype;
        this.createdAt = createdAt;
    }

    // Getters
    public Integer getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getLastname(){
        return this.lastname;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public String getUsertype(){
        return this.usertype;
    }
    public Date getCreatedAt(){
        return this.createdAt;
    }

    // Setters
    public void setUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.usertype = user.getUsertype();
        this.createdAt = user.getCreatedAt();
    }
    public void setId(Integer id){
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setUserType(String usertype){
        this.usertype = usertype;
    }
    public void setCreatedAt(Date date){
        this.createdAt = date;
    }

}