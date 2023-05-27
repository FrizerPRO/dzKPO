package kpo.dz2.controller;

import jakarta.validation.Valid;
import kpo.dz2.model.DishDTO;
import kpo.dz2.service.DishService;
import kpo.dz2.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/menu")
public class MenuController {

    private final DishService dishService;

    public MenuController(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("dishs", dishService.findAllNotEmpty());
        return "menu/list";
    }
}
