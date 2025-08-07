package com.example.demo.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getuser() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> optionalUser = userRepository.findByPhone(user.getPhone());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        userRepository.save(user);
    }

    public void deleteUsrer(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void updateuser(Long id, User dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getNationalld() != null) {
            user.setNationalld(dto.getNationalld());
        }
        userRepository.save(user);
    }
}
