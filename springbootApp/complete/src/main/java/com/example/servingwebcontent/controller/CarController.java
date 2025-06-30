package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.service.CarList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CarController {
    private final CarList carList;

    @Autowired
    public CarController(CarList carList) {
        this.carList = carList;
    }

    @GetMapping("/cars")
    public String showCars(Model model) {
        model.addAttribute("cars", carList.getAllCarsPage(PageRequest.of(0, 10)).getContent());
        model.addAttribute("car", new Car());
        return "car/car-list";
    }

    @GetMapping("/cars/search")
    public String searchCars(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Car> carPage = carList.searchCarsByBrandOrModelPage(keyword, PageRequest.of(page, size));
        model.addAttribute("cars", carPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", carPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "car/car-list";
    }

    @GetMapping("/cars/status")
    public String filterByStatus(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        if (status == null || status.isEmpty()) {
            Page<Car> carPage = carList.getAllCarsPage(PageRequest.of(page, size));
            model.addAttribute("cars", carPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", carPage.getTotalPages());
            model.addAttribute("status", status);
            return "car/car-list";
        }
        Page<Car> carPage = carList.getCarsByStatusPage(status, PageRequest.of(page, size));
        model.addAttribute("cars", carPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", carPage.getTotalPages());
        model.addAttribute("status", status);
        return "car/car-list";
    }

    @GetMapping("/cars/quantity")
    public String filterByQuantity(
            @RequestParam(required = false) String quantityStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        if (quantityStatus == null || quantityStatus.isEmpty()) {
            Page<Car> carPage = carList.getAllCarsPage(PageRequest.of(page, size));
            model.addAttribute("cars", carPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", carPage.getTotalPages());
            model.addAttribute("quantityStatus", quantityStatus);
            return "car/car-list";
        }
        Page<Car> carPage = carList.getCarsByQuantityStatusPage(quantityStatus, PageRequest.of(page, size));
        model.addAttribute("cars", carPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", carPage.getTotalPages());
        model.addAttribute("quantityStatus", quantityStatus);
        return "car/car-list";
    }

    @GetMapping("/cars/add")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "car/add-car";
    }

    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute @Valid Car car, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("car", car);
            return "car/add-car";
        }
        try {
            carList.addCar(car);
            redirectAttributes.addFlashAttribute("message", "Car added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cars";
    }

    @GetMapping("/cars/edit/{carId}")
    public String showEditForm(@PathVariable String carId, Model model) {
        Car car = carList.findCar(carId);
        if (car == null) return "redirect:/cars";
        model.addAttribute("car", car);
        return "car/edit-car";
    }

    @PostMapping("/cars/edit")
    public String editCar(@ModelAttribute @Valid Car car, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("car", car);
            return "car/edit-car";
        }
        try {
            carList.updateCar(
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPrice(),
                car.getStatus(),
                car.getImportDate(),
                car.getQuantity()
            );
            redirectAttributes.addFlashAttribute("message", "Car updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cars";
    }

    @GetMapping("/cars/delete/{carId}")
    public String deleteCar(@PathVariable String carId, RedirectAttributes redirectAttributes) {
        try {
            carList.deleteCar(carId);
            redirectAttributes.addFlashAttribute("message", "Car deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cars";
    }

    @GetMapping("/cars/detail/{carId}")
    public String showCarDetail(@PathVariable String carId, Model model) {
        Car car = carList.findCar(carId);
        if (car == null) return "redirect:/cars";
        model.addAttribute("car", car);
        return "car/car-detail";
    }
}