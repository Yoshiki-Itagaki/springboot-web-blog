package com.photo.myblog.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String resetToken;

    @Column(nullable = false)
    private String profilePictureUrl;

    @Column(nullable = false)
    private LocalDateTime birthDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;



    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name="role")
    private Set<String> roles;

    public User() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getResetToken(){
        return resetToken;
    }

    public void setResetToken(String resetToken){
        this.resetToken = resetToken;
    }

    public String getProfilePictureUrl(){
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl){
        this.profilePictureUrl = profilePictureUrl;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate){
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public Set<String> getRoles(){
        return roles;
    }

    public void setRoles(Set<String> roles){
        this.roles = roles;
    }
}
