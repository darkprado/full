package com.example.back.entity;

import java.time.LocalDateTime;
import java.util.*;

import com.example.back.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author s.melekhin
 * @since 27 март 2023 г.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
