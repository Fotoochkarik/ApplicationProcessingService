package com.example.aps.controller;

import com.example.aps.entity.Condition;
import com.example.aps.entity.Request;
import com.example.aps.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operator/requests")
@PreAuthorize("hasAuthority('OPERATOR')")
public class OperatorController {
    private final RequestService requestService;

    public OperatorController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/")
    public List<Request> getAllWithStatusSend() {
        return requestService.getAllWithStatusSend(Condition.SENT);
    }

    @GetMapping("/{id}")
    public Request get(@PathVariable Long id) {
        return requestService.getBL(id);
    }

    @PutMapping(value = "/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void accept(@PathVariable Long id) {
        requestService.accept(id);
    }

    @PutMapping(value = "/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void reject(@PathVariable Long id) {
        requestService.reject(id);
    }
}
