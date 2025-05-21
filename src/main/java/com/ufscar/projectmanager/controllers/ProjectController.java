package com.ufscar.projectmanager.controllers;

import com.ufscar.projectmanager.dto.ProjectRequest;
import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.models.User;
import com.ufscar.projectmanager.repositories.ProjectRepository;
import com.ufscar.projectmanager.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addName(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            Optional<User> optional = this.userRepository.findById(userId);
            String name = optional.get().getName();
            model.addAttribute("name", name);
        }
    }

    @GetMapping("")
    public String index(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        List<Project> projects = this.projectRepository.findByUserId(userId);
        model.addAttribute("projects", projects);

        return "projects/index";
    }

    @GetMapping("/new")
    public ModelAndView newProject(ProjectRequest projectRequest) {

        ModelAndView mv = new ModelAndView("projects/new");
        return mv;
    }

    @PostMapping("")
    public String create(HttpSession session, @Valid ProjectRequest projectRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("\n*****ERRO NO FORMULARIO. TENTE NOVAMENTE!*****\n");
            return "projects/new";
        } else {
            Long userId = (Long) session.getAttribute("userId");
            User user = this.userRepository.findById(userId).get();
            Project project = projectRequest.toModel(user);
            projectRepository.save(project);
        }

        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id, ProjectRequest projectRequest) {

        Optional<Project> optional = this.projectRepository.findById(id);

        if (optional.isPresent()) {
            Project project = optional.get();
            projectRequest.fromModel(project);
            ModelAndView mv = new ModelAndView("projects/show");
            mv.addObject("projectId", id);
            return mv;
        } else {
            return  new ModelAndView("redirect:/projects");
        }
    }

    // should be a PUT
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid ProjectRequest projectRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            System.out.println("\n*****ERRO NO FORMULARIO DE EDICAO. TENTE NOVAMENTE!*****\n");
            return "projects/show";

        } else {

            Optional<Project> optional = this.projectRepository.findById(id);

            if (optional.isPresent()){
                Project project = optional.get();
                projectRequest.updateModel(project);
                this.projectRepository.save(project);

            } else {

                System.out.println("\n*****ERRO NO FORMULARIO DE EDICAO. TENTE NOVAMENTE!*****\n");

            }
            return "redirect:/projects";
        }
    }

    // Should be a DELETE
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        try {
            this.projectRepository.deleteById(id);
        } catch(Exception e) {
            System.out.println(e);
        }

        return "redirect:/projects";
    }

    @GetMapping("/open/{id}")
    public String open(@PathVariable Long id, HttpSession session) {
        session.setAttribute("projectId", id);
        return "redirect:/tasks";
    }

}
