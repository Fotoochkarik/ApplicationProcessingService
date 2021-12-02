package com.example.aps.service;

import com.example.aps.entity.User;
import com.example.aps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Entity not found "+ id));
    }

    public User getByName(String name) {
        return userRepository.findByName(name);
    }
}
