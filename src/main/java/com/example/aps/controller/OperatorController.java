package com.example.aps.controller;

import com.example.aps.entity.Condition;
import com.example.aps.entity.Request;
import com.example.aps.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/operator/requests")
@ResponseBody
@PreAuthorize("hasAuthority('OPERATOR')")
public class OperatorController {
    //    Оператор может

//•	Просматривать отправленные на рассмотрение заявки+
//•	Принимать заявки+
//•	Отклонять заявки+

//    Оператор НЕ может

    //•	создавать заявки+
//•	просматривать заявки в статусе отличном от «отправлено»+
//•	редактировать заявки+
//•	назначать права+

    @Autowired
    RequestService requestService;

    @GetMapping("/")
    public List<Request> getAllSend() {
        return requestService.getAllSend(Condition.SENT);
    }

    @GetMapping("/{id}")
    public Request get(@PathVariable Long id) {
        return requestService.get(id);
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
