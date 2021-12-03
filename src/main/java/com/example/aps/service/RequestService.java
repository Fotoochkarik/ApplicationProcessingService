package com.example.aps.service;

import com.example.aps.entity.Condition;
import com.example.aps.entity.Request;
import com.example.aps.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    public Request get(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + id));
    }

    public Request get(Long id, Long userId) {
        Request request = get(id);
        if (request.getAuthor().getId().equals(userId)) {
            return request;
        }
        throw new IllegalArgumentException("Does not meet the requirements");
    }

    public List<Request> getAll(Long userId) {
        return requestRepository.findAllByAuthorId(userId);
    }

    public Request save(Request request, Long userId) {
        if (request.isNew()) {
            return requestRepository.save(new Request(null, request.getDescription(), userService.get(userId)));
        } else {
            if (request.getAuthor().getId().equals(userId) && request.getStatus().equals(Condition.DRAFT)) {
                return requestRepository.save(request);
            }
            throw new IllegalArgumentException("Does not meet the requirements");
        }
    }

    public Request send(Long id, Long userId) {
        Request request = get(id);
        if (!request.isNew()) {
            if (request.getAuthor().getId().equals(userId) && request.getStatus().equals(Condition.DRAFT)) {
                request.setStatus(Condition.SENT);
                return requestRepository.save(request);
            }
            throw new IllegalArgumentException("Does not meet the requirements");
        }
        throw new IllegalArgumentException("Must not be new " + id);
    }

    public List<Request> getAllWithStatusSend(Condition sent) {
        return requestRepository.findAllByStatus(sent).stream()
                .map(this::convertMessage)
                .collect(Collectors.toList());
    }

    public Request accept(Long id) {
        Request request = get(id);
        if (request.getStatus().equals(Condition.SENT)) {
            request.setStatus(Condition.ACCEPTED);
            return requestRepository.save(request);
        }
        throw new IllegalArgumentException("Does not meet the status " + Condition.SENT);
    }

    public Request reject(Long id) {
        Request request = get(id);
        if (request.getStatus().equals(Condition.SENT)) {
            request.setStatus(Condition.REJECTED);
            return requestRepository.save(request);
        }
        throw new IllegalArgumentException("Does not meet the status " + Condition.SENT);
    }

    public Request getBL(Long id) {
        return convertMessage(get(id));
    }

    public Request convertMessage(Request request) {
        String newDescription = request.getDescription()
                .replace("", "-")
                .substring(1);
        request.setDescription(newDescription);
        return request;
    }
}
