package com.example.demo.Service;

import com.example.demo.Repository.StaffRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.dto.CreateRequest.StaffCreateRequest;
import com.example.demo.model.dto.ListDto.staffListDto;
import com.example.demo.model.dto.ProfileDto.StaffProfileDto;
import com.example.demo.model.dto.Update.StaffUpdateRequest;
import com.example.demo.model.entity.Staff;
import com.example.demo.model.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffService(StaffRepository staffRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public StaffProfileDto createStaff(StaffCreateRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + request.getUsername()));

        Staff staff = Staff.builder()
                .userId(user.getId())
                .personnelld(request.getPersonnelId())
                .build();

        Staff savedStaff = staffRepository.save(staff);

        user.getRoles().add("STAFF");
        user.setActive(true);
        userRepository.save(user);

        return mapToProfileDto(savedStaff, user);
    }

    @Transactional
    public StaffProfileDto updateStaff(Long id, StaffUpdateRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with id: " + id));

        User user = userRepository.findById(staff.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found for staff id: " + staff.getId()));

        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return mapToProfileDto(staff, user);
    }

    @Transactional(readOnly = true)
    public List<StaffProfileDto> getAllStaff() {
        return staffRepository.findAll().stream()
                .map(staff -> {
                    User user = userRepository.findById(staff.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("Associated user not found for staff id: " + staff.getId()));
                    return mapToProfileDto(staff, user);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<staffListDto> getStaffList() {
        return staffRepository.findAll().stream()
                .map(staff -> {
                    User user = userRepository.findById(staff.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("User not found for staff id: " + staff.getId()));
                    return new staffListDto(staff.getId(), user.getName(), staff.getPersonnelld());
                })
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public StaffProfileDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("staff not found with id: " + id));
        User user = userRepository.findById(staff.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found for staff id: " + staff.getId()));
        return mapToProfileDto(staff, user);

    }

    @Transactional
    public void deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with id: " + id));

        User user = userRepository.findById(staff.getUserId()).orElse(null);
        if (user != null) {
            user.getRoles().remove("STAFF");
            if (user.getRoles().isEmpty()) {
                user.setActive(false);
            }
            userRepository.save(user);
        }

        staffRepository.deleteById(id);
    }

    private StaffProfileDto mapToProfileDto(Staff staff, User user) {
        return StaffProfileDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .personnelId(staff.getPersonnelld())
                .build();
    }
}