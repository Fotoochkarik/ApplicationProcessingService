package com.example.aps.service;

import com.example.aps.entity.Condition;
import com.example.aps.entity.Request;
import com.example.aps.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    public List<Request> getAll(Long id) {
        return requestRepository.findAllByAuthorId(id);
    }

    public Request save(Request request, Long userId) {
        if (request.isNew()) {
            return requestRepository.save(new Request(null, request.getDescription(), userService.get(userId)));
        } else {
            if (request.getAuthor().getId().equals(userId) && request.getStatus().equals(Condition.DRAFT)) {
                return requestRepository.save(request);
            }
            throw new IllegalArgumentException(" Не соответствует " + userId);
        }
    }

    public Request send(Long id, Long userId) {
        Request request = requestRepository.getById(id);
        if (!request.isNew()) {
            if (request.getAuthor().getId().equals(userId) && request.getStatus().equals(Condition.DRAFT)) {
                request.setStatus(Condition.SENT);
                return requestRepository.save(request);
            }
            throw new IllegalArgumentException(" Не соответствует " + id);
        }
        throw new IllegalArgumentException("Not new " + id);
    }

    public List<Request> getAllSend(Condition sent) {
        return requestRepository.findAllByStatus(sent);
    }

    public Request accept(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        Request request = optionalRequest.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        if (request.getStatus().equals(Condition.SENT)) {
            request.setStatus(Condition.ACCEPTED);
            return requestRepository.save(request);
        }
        throw new IllegalArgumentException(" Не соответствует  status" + id);
    }

    public Request reject(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        Request request = optionalRequest.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        if (request.getStatus().equals(Condition.SENT)) {
            request.setStatus(Condition.REJECTED);
            return requestRepository.save(request);
        }
        throw new IllegalArgumentException(" Не соответствует  status" + id);
    }

    public Request get(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found" + id));
        String newDescription = request.getDescription().replace("", "-").substring(1);
//request.setDescription(newDescription.substring(1,newDescription.length()-1));
        request.setDescription(newDescription);
        return request;
    }
}
