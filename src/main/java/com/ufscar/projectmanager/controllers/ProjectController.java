package com.ufscar.projectmanager.controllers;

import com.ufscar.projectmanager.dto.ProjectRequest;
import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.repositories.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView newProject(ProjectRequest projectRequest) {

        ModelAndView mv = new ModelAndView("projects/new");
        return mv;
    }

    @PostMapping("/projects")
    public String create(@Valid ProjectRequest projectRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("\n*****ERRO NO FORMULARIO. TENTE NOVAMENTE!*****\n");
            return "projects/new";
        } else {
            Project project = projectRequest.toModel();
            System.out.println(project);
            projectRepository.save(project);
        }

        return "redirect:/projects";
    }
}
