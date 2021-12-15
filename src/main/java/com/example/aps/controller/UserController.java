package com.example.aps.controller;

import com.example.aps.AuthUser;
import com.example.aps.entity.Request;
import com.example.aps.service.RequestService;
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
@RequestMapping("/api/user/requests")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final RequestService requestService;

    public UserController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/")
    public List<Request> getAll(@AuthenticationPrincipal AuthUser user) {
        return requestService.getAll(user.id());
    }

    @GetMapping("/{id}")
    public Request get(@AuthenticationPrincipal AuthUser user, @PathVariable Long id) {
        return requestService.get(id, user.id());
    }

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
                       @RequestBody Request request) {
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
