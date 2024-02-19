package org.example.pp_3_1_4_bootstrap.controllers;

import org.example.pp_3_1_4_bootstrap.models.User;
import org.example.pp_3_1_4_bootstrap.repositories.RoleRepository;
import org.example.pp_3_1_4_bootstrap.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String userList(Model model, Authentication authentication) {
        model.addAttribute("allUsers", userService.allUsers());
        UserDetails user = userService.loadUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }

    @PostMapping
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin";
        }
//        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "registration";
//        }
        if (!userService.saveUser(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "admin";
        }
        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "redirect:/admin";
    }

    @PatchMapping("/edit")//
    public String update(@ModelAttribute("editedUser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin";
        } else {

            userService.updateUser(user);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
