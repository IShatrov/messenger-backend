package ru.mipt.messenger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;

import ru.mipt.messenger.exceptions.NotEnoughAuthorityException;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.services.UserService;
import ru.mipt.messenger.services.SessionInfoService;
import ru.mipt.messenger.dto.UserDto;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Creates a new user", description = """
                  Creates a new user in the database. Please make sure that request body matches the following criteria:
                  1) userId is null. This is required to correctly generate userId in the database. It is not required to explicitly assign userId to null in the JSON since it defaults to null.
                  2) nickname, firstname, phone, email and password are not null, not blank, have valid length and match patterns if required.
                  3) nickname, phone and email are unique in the database.
                  4) If registrationDttm is not null, it matches pattern "yyyy-MM-dd'T'HH:mm:ss.SSSZ". RegistrationDttm defaults to current UTC time even if it is not null in the request.
                  @see <a href="https://docs.spring.io/spring-framework/docs/3.2.4.RELEASE_to_4.0.0.M3/Spring%20Framework%203.2.4.RELEASE/org/springframework/format/annotation/DateTimeFormat.ISO.html">Spring docs</a>
                  5) dateOfBirth is not null and matches pattern "yyyy-MM-dd".
                  6) If secondName is not null, it has valid length.
                  7) If status is not null, it is either "Banned" or "Active". If status is null, it defaults to "Active".
                  8) If role is not null, it is either "Admin" or "User". If role is null, it defaults to "User".
                  9) If profilePictureLink is not null, it has valid length.
            """)
    @PostMapping("${user_base_url}")
    public void create(@Valid @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) User user)
            throws DataIntegrityViolationException, MethodArgumentNotValidException, HttpMessageNotReadableException {
        userService.createUser(user);
    }


    @Transactional
    @Operation(summary = "Reads user by userId or firstname", description = """
                    Reads by userId if userId is not null or by firstname if userId is null. If both are null, returns authenticated user. To read by id, the authenticated user must be admin or id must be equal to id of the authenticated user. Returns a list of users.
                    """)
    @GetMapping("${user_base_url}")
    public List<UserDto> read( Authentication auth,
                            @RequestParam(required = false) Integer id,
                            @RequestParam(required = false) String firstname)
                            throws IllegalArgumentException, NotEnoughAuthorityException {
        if (id != null) {
            var result = userService.readUserById(id);
            return (result == null) ? List.of() : List.of(new UserDto(result));
        }

        if (firstname != null) {
            return userService.readUsersByFirstname(firstname).stream()
                .map(UserDto::new)
                .toList();
        }

        var user = userService.readUserById(userService.readUserByNickname(auth.getName()).getUserId());
        return List.of(new UserDto(user));
    }

    @GetMapping("${user_base_url}/current")
    public UserDto getCurrentUser(@AuthenticationPrincipal SecureUser secureUser) {
        return new UserDto(secureUser.getUser());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public List<UserDto> getAll() {
        return userService.getAllUsers().stream()
            .map(UserDto::new)
            .toList();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Get users whose username contains substring after removing whitespaces and bringing everything to lower case")
    @GetMapping("${user_base_url}/contains")
    public List<UserDto> getBySubstring(@RequestParam String substr) {
        return userService.getUsersBySubstring(substr).stream()
            .map(UserDto::new)
            .toList();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Updates a user")
    @PutMapping("${user_base_url}")
    public void update(@RequestBody User user) throws ResourceNotFoundException {
        userService.updateUser(user);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Deletes a user")
    @DeleteMapping("${user_base_url}")
    public void delete(@RequestParam Integer id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }
}
