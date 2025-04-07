package ru.mipt.messenger.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import ru.mipt.messenger.constants.ChatConstants;
import ru.mipt.messenger.types.chattypes.Type;

@Getter
@Entity
@Table(name = "chats")
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Chat ID. Must be null when creating a new chat.")
    private Integer chatId;

    @Enumerated(EnumType.STRING)
    @NotNull
    @ColumnDefault("Active")
    private Type type;

    @NotBlank(message = "Chat name cannot be null or blank")
    @Length(max = ChatConstants.MAX_NAME_LENGTH, message = "Chat name too long")
    @Column(nullable = false)
    @Setter
    private String name;

    @Length(max = ChatConstants.MAX_NAME_LENGTH, message = "Chat name too long")
    @Column(nullable = false)
    @Setter
    private String picture_link;

    @Length(max = ChatConstants.MAX_NAME_LENGTH, message = "Chat name too long")
    @Column(nullable = false)
    @Setter
    private String description;

    @Column(nullable = false)
    @CreationTimestamp
    @Schema(description = "Chat creation date. Automatically generated when creating a new chat. If not null, must match pattern \"yyyy-MM-dd'T'HH:mm:ss.SSSZ\"")
    private LocalDateTime creationDttm;


    public Chat() {
        this.type = Type.Private;
        this.creationDttm = LocalDateTime.now(ZoneOffset.UTC);
    }
    
    public Chat(String name, LocalDateTime creationDttm, Type type) {
        this.name = name;
        this.type = type;

        if (creationDttm == null) {
            this.creationDttm = LocalDateTime.now(ZoneOffset.UTC);
        } else {
            this.creationDttm = creationDttm;
        }
    }

    public void setType(Type type) {
        if (type == null) {
            this.type = Type.Private;
        } else {
            this.type = type;
        }
    }
}
