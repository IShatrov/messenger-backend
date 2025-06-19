package ru.mipt.messenger.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "settings", schema = "messenger")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "dark_theme", nullable = false)
    private boolean darkTheme = false;

    @Column(name = "show_date_of_birth", nullable = false)
    private boolean showDateOfBirth = false;

    @Column(name = "chat_yourself_default", nullable = false)
    private boolean chatYourselfDefault = false;

    @Column(name = "contact_auto_accept", nullable = false)
    private boolean contactAutoAccept = false;
}