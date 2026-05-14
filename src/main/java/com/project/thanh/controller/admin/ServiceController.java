package com.project.thanh.controller.admin;

import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.thanh.domain.Service;
import com.project.thanh.service.ServiceService;

@Controller
@RequestMapping("/admin/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // =========================
    // LIST SERVICE
    // =========================

    @GetMapping
    public String getServicePage(Model model) {

        List<Service> services = serviceService.getAllServices();

        model.addAttribute("services", services);

        return "admin/service/show";
    }

    // =========================
    // CREATE PAGE
    // =========================

    @GetMapping("/create")
    public String getCreateServicePage(Model model) {

        model.addAttribute("newService", new Service());

        return "admin/service/create";
    }

    // =========================
    // CREATE SERVICE
    // =========================

    @PostMapping("/create")
    public String handleCreateService(
            @ModelAttribute("newService") Service newService) {

        newService.setActive(true);

        serviceService.saveService(newService);

        return "redirect:/admin/services";
    }

    // =========================
    // DELETE SERVICE
    // =========================

    // @GetMapping("/delete/{id}")
    // public String deleteService(@PathVariable Long id) {

    // serviceService.deleteServiceById(id);

    // return "redirect:/admin/services";
    // }
}