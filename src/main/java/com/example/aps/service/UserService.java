package com.example.aps.service;

import com.example.aps.entity.Role;
import com.example.aps.entity.User;
import com.example.aps.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    public void addOperatorRole(Long id) {
        User user = get(id);
        Set<Role> roles = user.getRoles();
        if (!roles.contains(Role.OPERATOR)) {
            roles.add(Role.OPERATOR);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
