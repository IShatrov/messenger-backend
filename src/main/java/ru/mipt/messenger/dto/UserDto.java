package ru.mipt.messenger.dto;

import ru.mipt.messenger.models.User;

public class UserDto {
    public Integer userId;
    public String nickname;
    public String firstname;
    public String secondname;
    public String profilePictureLink;
    
    public UserDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.firstname = user.getFirstname();
        this.secondname = user.getSecondname();
        this.profilePictureLink = user.getProfilePictureLink();
    }
}