package com.ufscar.projectmanager.controllers;

import com.ufscar.projectmanager.dto.ProjectRequest;
import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public String index(Model model) {
        List<Project> projects = this.projectRepository.findAll();
        model.addAttribute("projects", projects);
        model.addAttribute("name", "mauricio");

        return "projects/index";
    }

    @GetMapping("projects/new")
    public String newProject(Model model) {

        return "projects/new";
    }

    @PostMapping("/projects")
    public String create(ProjectRequest request) {

        Project project = request.toModel();
        projectRepository.save(project);

        return "redirect:/projects";
    }
}
