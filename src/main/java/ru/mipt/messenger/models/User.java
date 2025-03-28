package ru.mipt.messenger.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import ru.mipt.messenger.constants.UserConstants;
import ru.mipt.messenger.types.usertypes.Role;
import ru.mipt.messenger.types.usertypes.Status;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message = "Nickname cannot be null or blank")
    @Length(max = UserConstants.MAX_NAME_LENGTH, message = "Nickname too long")
    @Column(unique = true, nullable = false)
    private String nickname;

    @NotBlank(message = "Firstname cannot be null or blank")
    @Length(max = UserConstants.MAX_NAME_LENGTH, message = "Firstname too long")
    @Column(nullable = false)
    private String firstname;

    @Length(max = UserConstants.MAX_NAME_LENGTH, message = "Secondname too long")
    private String secondname;

    // Did not use @NotNull because for some reason in caused Spring to create objects with this field set to null
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registrationDttm;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull
    @ColumnDefault("Active")
    private Status status;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    @ColumnDefault("User")
    private Role role;

    @Length(max = UserConstants.MAX_LINK_LENGTH, message = "Link too long")
    private String profilePictureLink;

    @NotNull
    @Column(name = "is_active", nullable = false) // didn`t work without this annotation
    @ColumnDefault("true")
    private boolean isActive;
    
    private Timestamp lastSeen;

    @NotBlank(message = "Phone cannot be null or blank")
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?\\d{5,15}$", message = "Phone must match the following pattern: ^\\+?\\d{5,15}$")
    private String phone;

    @Length(max = UserConstants.MAX_EMAIL_LENGTH, message = "Email too long")
    @Email(message = "Email must match email pattern")
    private String email;

    @NotBlank(message = "Password cannot be null or blank")
    @Column(nullable = false)
    @Length(min = UserConstants.MIN_PASSWORD_LENGTH, max =UserConstants.MAX_PASSWORD_LENGTH,
            message = "Invalid password length")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must have at least one uppercase letter"),
            @Pattern(regexp = ".*[a-z].*", message = "Password must have at least one lowercase letter")
    })
    private String password;


    public User() {
        System.out.println("In empty");
        this.isActive = true;
        this.role = Role.User;
        this.status = Status.Active;
        this.registrationDttm = LocalDateTime.now(ZoneOffset.UTC);
    }
    
    public User(String nickname, String firstname, String secondname, LocalDateTime registrationDttm,
                LocalDate dateOfBirth, Status status, Role role, String profilePictureLink, boolean isActive,
                Timestamp lastSeen, String phone, String email, String password) {
        this.nickname = nickname;
        this.firstname = firstname;
        this.secondname = secondname;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.role = role;
        this.profilePictureLink = profilePictureLink;
        this.isActive = isActive;
        this.lastSeen = lastSeen;
        this.phone = phone;
        this.email = email;
        this.password = password;

        if (registrationDttm == null) {
            this.registrationDttm = LocalDateTime.now(ZoneOffset.UTC);
        } else {
            this.registrationDttm = registrationDttm;
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setStatus(Status status) {
        if (status == null) {
            this.status = Status.Active;
        } else {
            this.status = status;
        }
    }

    public void setRole(Role role) {
        if (role == null) {
            this.role = Role.User;
        } else {
            this.role = role;
        }
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
