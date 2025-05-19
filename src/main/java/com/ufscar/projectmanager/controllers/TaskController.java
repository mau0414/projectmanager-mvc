package com.ufscar.projectmanager.controllers;

import com.ufscar.projectmanager.dto.ProjectRequest;
import com.ufscar.projectmanager.dto.TaskRequest;
import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.models.Task;
import com.ufscar.projectmanager.models.TaskStatus;
import com.ufscar.projectmanager.repositories.ProjectRepository;
import com.ufscar.projectmanager.repositories.TaskRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @ModelAttribute
    public void addProject(HttpSession session, Model model) {
        Long projectId = (Long) session.getAttribute("projectId");

        if (projectId != null) {
            Optional<Project> optional = this.projectRepository.findById(projectId);
            Project project = optional.get();
            model.addAttribute("project", project);
        }
    }

    @GetMapping("")
    public String index(HttpSession session, Model model) {

        Long projectId = (Long) session.getAttribute("projectId");
        List<Task> tasks = this.taskRepository.findByProjectId(projectId);
        List<Task> todoTasks = new ArrayList<>();
        List<Task> inProgressTasks = new ArrayList<>();
        List<Task> doneTasks = new ArrayList<>();

        for (Task task : tasks) {
            TaskStatus status = task.getStatus();
            if (status == TaskStatus.TODO) todoTasks.add(task);
            else if (status == TaskStatus.INPROGRESS) inProgressTasks.add(task);
            else if (status == TaskStatus.DONE) doneTasks.add(task);
        }

        model.addAttribute("todoTasks", todoTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("doneTasks", doneTasks);

        return "tasks/index";
    }

    @GetMapping("/new")
    public ModelAndView newTask(TaskRequest taskRequest) {

        ModelAndView mv = new ModelAndView("tasks/new");
        return mv;
    }

    @PostMapping("")
    public String create(HttpSession session, @Valid TaskRequest taskRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("\n*****ERRO NO FORMULARIO. TENTE NOVAMENTE!*****\n");
            return "tasks/new";
        } else {
            Long projectId = (Long) session.getAttribute("projectId");
            Optional<Project> optional = this.projectRepository.findById(projectId);
            Project project = optional.get();
            Task task = taskRequest.toModel(project);
            taskRepository.save(task);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/{id}/progress")
    public String updateInProgress(@PathVariable Long id) {

        Optional<Task> optional = this.taskRepository.findById(id);
        Task task = optional.get();
        task.setStatus(TaskStatus.INPROGRESS);
        this.taskRepository.save(task);

        return "redirect:/tasks";
    }

    @GetMapping("/{id}/done")
    public String updateInDone(@PathVariable Long id) {

        Optional<Task> optional = this.taskRepository.findById(id);
        Task task = optional.get();
        task.setStatus(TaskStatus.DONE);
        this.taskRepository.save(task);

        return "redirect:/tasks";
    }

    // Should be a DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        try {
            this.taskRepository.deleteById(id);
        } catch(Exception e) {
            System.out.println(e);
        }

        return "redirect:/tasks";
    }
}


