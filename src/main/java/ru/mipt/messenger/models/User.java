package ru.mipt.messenger.models;

import jakarta.persistence.*;
import ru.mipt.messenger.types.usertypes.Role;
import ru.mipt.messenger.types.usertypes.Status;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String nickname;
    private String firstname;
    private String secondname;
    private LocalDateTime registrationDttm;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String profilePictureLink;

    @Column(name = "is_active") // didn`t work without this annotation
    private boolean isActive;
    
    private Timestamp lastSeen;
    private String phone;
    private String email;
    private String password;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public void setRegistrationDttm(LocalDateTime registrationDttm) {
        this.registrationDttm = registrationDttm;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setProfilePictureLink(String profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void setLastSeen(Timestamp lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public LocalDateTime getRegistrationDttm() {
        return registrationDttm;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public String getProfilePictureLink() {
        return profilePictureLink;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public Timestamp getLastSeen() {
        return lastSeen;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
