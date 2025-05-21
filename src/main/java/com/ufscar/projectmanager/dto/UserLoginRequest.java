package com.ufscar.projectmanager.dto;

import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserLoginRequest {

    @NotBlank
    @NotNull
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User toModel() {

        User user = new User();
        user.setName(this.name);

        return user;
    }
}
