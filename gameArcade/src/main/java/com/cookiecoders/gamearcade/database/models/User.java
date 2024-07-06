package com.cookiecoders.gamearcade.database.models;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;


    // Getters
    public Integer getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }

    // Setters
    public void setId(Integer id){
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }

}