package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Car;
import com.example.servingwebcontent.service.CarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("cars", carList.getAllCars());
        model.addAttribute("car", new Car()); // Thêm đối tượng mới để hỗ trợ form
        return "car/car-list";
    }

    @GetMapping("/cars/add")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "car/add-car";
    }

    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute Car car, Model model, RedirectAttributes redirectAttributes) {
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
        if (car == null) {
            return "redirect:/cars";
        }
        model.addAttribute("car", car);
        return "car/edit-car";
    }

    @PostMapping("/cars/edit")
    public String editCar(@ModelAttribute Car car, RedirectAttributes redirectAttributes) {
        try {
            if (car.getPrice() < 0) {
                throw new IllegalArgumentException("Price cannot be negative!");
            }
            if (car.getYear() < 1900) {
                throw new IllegalArgumentException("Year must be at least 1900!");
            }
            carList.updateCar(
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPrice(),
                car.getStatus(),
                car.getImportDate()
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

    // Thêm: Lọc xe theo năm
    @GetMapping("/cars/year/{year}")
    public String showCarsByYear(@PathVariable int year, Model model) {
        try {
            model.addAttribute("cars", carList.getCarsByYear(year));
            model.addAttribute("message", "Cars filtered by year " + year);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "car/car-list";
    }

    // Thêm: Lọc xe theo khoảng giá
    @GetMapping("/cars/price")
    public String showCarsByPrice(@RequestParam double minPrice, @RequestParam double maxPrice, Model model) {
        try {
            model.addAttribute("cars", carList.getCarsByPriceRange(minPrice, maxPrice));
            model.addAttribute("message", "Cars filtered by price range " + minPrice + " - " + maxPrice + " VND");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "car/car-list";
    }

    // Thêm: Lọc xe theo trạng thái
    @GetMapping("/cars/status")
    public String showCarsByStatus(@RequestParam String status, Model model) {
        try {
            model.addAttribute("cars", carList.getCarsByStatus(status));
            model.addAttribute("message", "Cars filtered by status " + status);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "car/car-list";
    }
}