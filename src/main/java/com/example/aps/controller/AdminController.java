package com.example.aps.controller;

import com.example.aps.entity.User;
import com.example.aps.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PutMapping(value = "/{id}/add-operator")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void addOperatorRole(@PathVariable Long id) {
        userService.addOperatorRole(id);
    }
}
