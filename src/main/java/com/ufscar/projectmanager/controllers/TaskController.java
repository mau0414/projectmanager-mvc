package com.ufscar.projectmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

//@Controller
public class TaskController {

    public String index(Model model) {


        return "tasks/index";

    }
}


