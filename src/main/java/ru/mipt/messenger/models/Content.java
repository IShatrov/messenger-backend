package ru.mipt.messenger.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contents")
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer contentId;

    @Getter
    private String text;

    @Getter
    private Integer fileId;

    public Content(String text) {
        this.text = text;
    }
}
