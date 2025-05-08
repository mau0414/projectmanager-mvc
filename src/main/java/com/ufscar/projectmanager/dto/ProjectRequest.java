package com.ufscar.projectmanager.dto;

import com.ufscar.projectmanager.models.Project;

import java.time.LocalDate;

public class ProjectRequest {

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

    public LocalDate getStartDate(LocalDate startDate) {
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

    public Project toModel() {

        Project project = new Project();
        project.setTitle(this.title);
        project.setDescription(this.description);
        project.setStartDate(this.startDate);
        project.setEndDate(this.endDate);

        return project;
    }

    @Override
    public String toString() {
        return "project title = " + this.getTitle() + " " + this.getEndDate();
    }


}
