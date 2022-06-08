package com.laayouni.onlineexamination.entities;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String password;
    private List<Test> ownedTests;
    private List<Test> passedTests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Test> getOwnedTests() {
        return ownedTests;
    }

    public void setOwnedTests(List<Test> ownedTests) {
        this.ownedTests = ownedTests;
    }

    public List<Test> getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(List<Test> passedTests) {
        this.passedTests = passedTests;
    }
}
