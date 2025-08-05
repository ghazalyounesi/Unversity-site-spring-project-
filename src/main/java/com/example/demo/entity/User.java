package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails{
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
         strategy = GenerationType.SEQUENCE,
         generator =  "user_sequence"

    )
    private long id;
    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String username;
    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "phone_number",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String phone;
    private String nationalld;
    private boolean admin;
    private boolean active;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();


    public User() {
    }

    public User(String username, long id, String password, String name, String phone, String nationalld, boolean admin, boolean active) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.nationalld = nationalld;
        this.admin = admin;
        this.active = active;
    }

    public User(String username, String password, String name, String phone, String nationalld, boolean admin, boolean active) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.nationalld = nationalld;
        this.admin = admin;
        this.active = active;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    public Set<String> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
