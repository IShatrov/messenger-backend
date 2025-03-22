package ru.mipt.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.services.UserService;

import java.util.List;


@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("{user_base_url}/create")
    public void create(@RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) User user) {
        userService.createUser(user);
    }

    @GetMapping("{user_base_url}/read")
    public List<User> read(@RequestParam(required = false) Integer id, @RequestParam(required = false) String nickname) {
        if (nickname != null) {
            return userService.readUsersByNickname(nickname);
        }

        return List.of(userService.readUserById(id));
    }

    @PostMapping("{user_base_url}/update")
    public void update(@RequestParam Integer id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("{user_base_url}/delete")
    public void delete(@RequestParam Integer id) {
        userService.deleteUser(id);
    }
}
