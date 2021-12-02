package com.example.aps.controller;

import com.example.aps.entity.User;
import com.example.aps.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/admin/users")
@ResponseBody
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserService userService;

//    Администратор может
//•	смотреть список пользователей +
//•	назначать пользователям права оператора+/-

//    Администратор НЕ может

//•	создавать заявки+
//•	просматривать заявки+
//•	редактировать заявки+
//•	принимать заявки+
//•	отклонять заявки+

    @GetMapping("/")
    public List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoles(@RequestBody User user, @PathVariable int id) {
        log.info("update Role {} with id={}", user, id);
        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
    }
}
