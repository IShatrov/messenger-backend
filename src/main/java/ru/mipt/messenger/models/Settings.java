package ru.mipt.messenger.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "settings")
@NoArgsConstructor
@AllArgsConstructor
public class Settings {
    @Id
    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    @Column(name = "dark_theme", nullable = false)
    private boolean darkTheme = false;

    @Getter
    @Column(name = "show_date_of_birth", nullable = false)
    private boolean showDateOfBirth = false;

    @Getter
    @Column(name = "chat_yourself_default", nullable = false)
    private boolean chatYourselfDefault = false;

    @Getter
    @Column(name = "contact_auto_accept", nullable = false)
    private boolean contactAutoAccept = false;
}