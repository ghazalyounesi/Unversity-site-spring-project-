package com.example.demo.Repasitory;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.phone = ?1 ")
    Optional<User> findByPhone(String phone);
    Optional<User> findByUsername(String username);
    List<User> findByNameContainingIgnoreCase(String name);

}
