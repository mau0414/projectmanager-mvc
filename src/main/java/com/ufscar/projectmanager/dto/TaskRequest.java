package com.ufscar.projectmanager.dto;

import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.models.Task;
import com.ufscar.projectmanager.models.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskRequest {

    @NotBlank
    @NotNull
    private String title;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private Project project;

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

    public Project getProject() { return project; }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task toModel(Project project) {

        Task task = new Task();
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setStartDate(this.startDate);
        task.setEndDate(this.endDate);
        task.setStatus(TaskStatus.TODO);
        task.setProject(project);
        return task;
    }

    @Override
    public String toString() {
        return "task title = " + this.getTitle() + " " + this.getEndDate();
    }

}
