/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import lp3.cemiterio.consts.Permission;

public class User {
    private int id;
    private final String email;
    private String name;
    private Permission permission;
    
    public User(String email) {
        this.email = email;
    }

    public User(int id, String email, String name, Permission permission) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return permission;
    }    
}
