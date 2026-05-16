package com.mitienda.repuestos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String mostrarAdmin() {
        return "forward:/admin.html";   // ← Esto es lo importante
    }
}