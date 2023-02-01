package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chuistov.springboot.crud.entities.Role;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.security.UserDetailsImpl;
import ru.chuistov.springboot.crud.services.RoleService;
import ru.chuistov.springboot.crud.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("authorizedUser", getAuthorizedUser());
        model.addAttribute("users", userService.findAll());

        // Creating a user with default role "user" and sending it
        // to the page just in case one wants to create new user
        User newUser = new User();
        newUser.getRoles().add(roleService.findAll().get(1));
        model.addAttribute("newUser", newUser);

        // Creating a user for editing user inside the view
        /*User updatedUser = new User();
        model.addAttribute("updatedUser", updatedUser);*/

        model.addAttribute("roles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "userRest";
    }

    private User getAuthorizedUser() {
        return ((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }

    @PostMapping("/admin")
    public String finishCreateUser(@ModelAttribute("newUser") User newUser) {
        userService.save(newUser);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/edit")
    public String finishUpdateUser(HttpServletRequest request,
                                   @RequestParam(value = "user-id", required = false) Long id,
                                   @RequestParam(value = "user-name", required = false) String firstName,
                                   @RequestParam(value = "user-last-name", required = false) String lastName,
                                   @RequestParam(value = "user-age", required = false) Integer age,
                                   @RequestParam(value = "user-email", required = false) String email,
                                   @RequestParam(value = "user-password", required = false) String password/*,
                                   @RequestParam(value = "role-admin", required = false) boolean roleAdmin,
                                   @RequestParam(value = "role-user", required = false) boolean roleUser*/) {

        System.out.println(request.getParameter("roles"));

        List<Role> roles = new ArrayList<>();
        if (request.getParameter("roles").equals("ROLE_ADMIN")) {
            roles.add(roleService.findRoleByRoleName("ROLE_ADMIN"));
        }
        roles.add(roleService.findRoleByRoleName("ROLE_USER"));

        userService.update(
                new User((long)id, firstName, lastName,
                        (int)age, email, password, roles));
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}