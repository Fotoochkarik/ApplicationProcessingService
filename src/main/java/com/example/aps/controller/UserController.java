package com.example.aps.controller;

import com.example.aps.AuthUser;
import com.example.aps.entity.Request;
import com.example.aps.repository.UserRepository;
import com.example.aps.service.RequestService;
import com.example.aps.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/requests")
@ResponseBody
@PreAuthorize("hasAuthority('USER')")
public class UserController {
//    Пользователь может

//•	создавать заявки+
//•	просматривать созданные им заявки+
//•	редактировать созданные им заявки в статусе «черновик»+
//•	отправлять заявки на рассмотрение оператору.+

//    Пользователь НЕ может:

    //• редактировать отправленные на рассмотрение заявки+
//•	видеть заявки других пользователей+
//•	принимать заявки+
//•	отклонять заявки+
//•	назначать права+
//•	смотреть список пользователей+
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public List<Request> getAll(@AuthenticationPrincipal AuthUser user) {
        log.info("getAll for user {}", user);
        return requestService.getAll(user.id());
    }

//    @GetMapping("/add")
//    public void add(@AuthenticationPrincipal AuthUser authUser) {
//        log.info("User {}", authUser.id());
//        for (int i = 0; i < 3; i++) {
//            requestService.save(new Request(null, "A" + i, userRepository.findById(authUser.id()).orElseThrow(() -> new IllegalArgumentException("Not found"))), authUser.id());
//        }
//        for (int i = 0; i < 3; i++) {
//            requestService.save(new Request(null, "A" + i, userRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("Not found"))), 2L);
//        }
//        requestService.save(new Request(null, "A" + 23, userRepository.findById(authUser.id()).orElseThrow(() -> new IllegalArgumentException("Not found"))), authUser.id());
//
//    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Request> create(@AuthenticationPrincipal AuthUser authUser,
                                          @RequestBody Request request) {
        Request created = requestService.save(request, authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/requests" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody Request request,
                       @PathVariable int id) {
        requestService.save(request, authUser.id());
    }

    @PutMapping(value = "/{id}/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void send(@AuthenticationPrincipal AuthUser authUser,
                     @PathVariable Long id) {
        requestService.send(id, authUser.id());
    }
}
