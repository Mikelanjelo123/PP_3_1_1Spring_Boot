package web.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;


@Controller
public class UsersController {
    @Autowired
    private final UserService userService;

    private UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String listUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/add")
    public String addUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user-add";
        }
        userService.add(user);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") int id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam("id") int id, @ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user-edit";
        }
        User existingUser = userService.findById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        userService.update(existingUser);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/";
    }
}