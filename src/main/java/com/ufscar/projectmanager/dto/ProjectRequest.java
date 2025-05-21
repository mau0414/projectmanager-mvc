package com.ufscar.projectmanager.dto;

import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProjectRequest {

    @NotBlank
    @NotNull
    private String title;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Project toModel(User user) {

        Project project = new Project();
        project.setTitle(this.title);
        project.setDescription(this.description);
        project.setStartDate(this.startDate);
        project.setEndDate(this.endDate);
        project.setUser(user);

        return project;
    }

    public void fromModel(Project project) {
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
    }

    public void updateModel(Project project) {
        project.setTitle(this.title);
        project.setDescription(this.description);
        if (this.startDate != null) project.setStartDate(this.startDate);
        if (this.endDate != null) project.setEndDate(this.endDate);
    }

    @Override
    public String toString() {
        return "project title = " + this.getTitle() + " " + this.getEndDate();
    }


}
