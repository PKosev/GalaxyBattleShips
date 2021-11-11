package com.example.exam.model.binding;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;

public class UserRegisterBindingModel {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean emailExist;
    private Boolean usernameExist;

    public UserRegisterBindingModel() {
    }

    @Size(min = 3, max = 10)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Size(min = 5, max = 20)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Size(min = 3)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Size(min = 3)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getEmailExist() {
        return emailExist;
    }

    public void setEmailExist(Boolean emailExist) {
        this.emailExist = emailExist;
    }

    public Boolean getUsernameExist() {
        return usernameExist;
    }

    public void setUsernameExist(Boolean usernameExist) {
        this.usernameExist = usernameExist;
    }
}
