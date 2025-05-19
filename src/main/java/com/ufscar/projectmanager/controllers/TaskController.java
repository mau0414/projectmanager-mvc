package com.ufscar.projectmanager.controllers;

import com.ufscar.projectmanager.dto.ProjectRequest;
import com.ufscar.projectmanager.dto.TaskRequest;
import com.ufscar.projectmanager.models.Project;
import com.ufscar.projectmanager.models.Task;
import com.ufscar.projectmanager.models.TaskStatus;
import com.ufscar.projectmanager.repositories.ProjectRepository;
import com.ufscar.projectmanager.repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects/{id}/tasks")
    public String index(@PathVariable Long id, Model model) {

        List<Task> tasks = this.taskRepository.findByProjectId(id);
        List<Task> todoTasks = new ArrayList<>();
        List<Task> inProgressTasks = new ArrayList<>();
        List<Task> doneTasks = new ArrayList<>();

        Optional<Project> optional = this.projectRepository.findById(id);
        Project project = optional.get();
        model.addAttribute("project", project);

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

    @GetMapping("/projects/{id}/tasks/new")
    public ModelAndView newTask(@PathVariable Long id, TaskRequest taskRequest) {

        ModelAndView mv = new ModelAndView("tasks/new");
        mv.addObject("projectId", id);

        return mv;
    }

    @PostMapping("projects/{id}/tasks")
    public String create(@PathVariable Long id, @Valid TaskRequest taskRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("\n*****ERRO NO FORMULARIO. TENTE NOVAMENTE!*****\n");
//            return "tasks/new";
        } else {
            Optional<Project> optional = this.projectRepository.findById(id);
            Project project = optional.get();
            Task task = taskRequest.toModel(project);
            taskRepository.save(task);
        }

        return "redirect:/projects/" + id + "/tasks";
    }

    @GetMapping("/projects/{projectId}/tasks/{id}/progress")
    public String updateInProgress(@PathVariable Long projectId, @PathVariable Long id) {

        Optional<Task> optional = this.taskRepository.findById(id);
        Task task = optional.get();
        task.setStatus(TaskStatus.INPROGRESS);
        this.taskRepository.save(task);

        return "redirect:/projects/" + projectId + "/tasks";
    }
}


