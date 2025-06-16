package ru.mipt.messenger.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class UserOwnerId implements Serializable {
    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "user_id")
    private Integer userId;
}

@Data
@Entity
@Table(name = "contacts")
@IdClass(UserOwnerId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @Column(name = "owner_id")
    private Integer ownerId;

    @Id
    @Column(name = "user_id")
    private Integer userId;
}