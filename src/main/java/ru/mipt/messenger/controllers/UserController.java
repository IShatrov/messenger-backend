package ru.mipt.messenger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;

import ru.mipt.messenger.models.User;
import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.services.UserService;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Creates a new user in the database. Please make sure that request body matches the following criteria:
     * 1) userId is null. This is required to correctly generate userId in the database.
     * It is not required to explicitly assign userId to null in the JSON since it defaults to null.
     * 2) nickname, firstname, phone, email and password are not null, not blank, have valid length and match patterns
     * if required.
     * 3) nickname, phone and email are unique in the database.
     * 4) If registrationDttm is not null, it matches pattern "yyyy-MM-dd'T'HH:mm:ss.SSSZ". RegistrationDttm
     * defaults to current UTC time even if it is not null in the request.
     * @see <a href="https://docs.spring.io/spring-framework/docs/3.2.4.RELEASE_to_4.0.0.M3/Spring%20Framework%203.2.4.RELEASE/org/springframework/format/annotation/DateTimeFormat.ISO.html">Spring docs</a>
     * 5) dateOfBirth is not null and matches pattern "yyyy-MM-dd".
     * 6) If secondName is not null, it has valid length.
     * 7) If status is not null, it is either "Banned" or "Active". If status is null, it defaults to "Active".
     * 8) If role is not null, it is either "Admin" or "User". If role is null, it defaults to "User".
     * 9) If profilePictureLink is not null, it has valid length.
     * @param user User to create
     * @throws DataIntegrityViolationException if userId is not null or other database error occurs.
     * @throws MethodArgumentNotValidException if any field of user is invalid.
     * @throws HttpMessageNotReadableException if JSON is invalid.
     */
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
    @PostMapping("{user_base_url}")
    public void create(@Valid @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) User user) throws
            DataIntegrityViolationException, MethodArgumentNotValidException, HttpMessageNotReadableException {
        userService.createUser(user);
    }

    /**
     * Reads by userId if userId is not null or by firstname if userId is null.
     * @param id userId to read.
     * @param firstname firstname to read.
     * @return List of matching users. If no users have matching userId or firstname, returns an empty list.
     * @throws IllegalArgumentException if both userId and firstname are null.
     */
    @Operation(summary = "Reads user by userId or firstname", description = """
                    Reads by userId if userId is not null or by firstname if userId is null. Returns a list of found users.
                    """)
    @GetMapping("{user_base_url}")
    public List<User> read(@RequestParam(required = false) Integer id, @RequestParam(required = false) String firstname)
            throws IllegalArgumentException {
        if (id != null) {
            var result = userService.readUserById(id);
            return (result == null) ? List.of() : List.of(result);
        }

        if (firstname != null) {
            return userService.readUsersByFirstname(firstname);
        }

        throw new IllegalArgumentException("At least one of the arguments must not be null");
    }

    /**
     * Get all users from db using service method getAllUsers
     * @return list of users
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    /**
     * Gets all users whose nickname contains substr after removing whitespaces and bringing everything to lower case
     * @param substr substring to search
     * @return list of users
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "Get users whose username contains substring after removing whitespaces and bringing everything to lower case")
    @GetMapping("/{user_base_url}/contains")
    public List<User> getBySubstring(@RequestParam String substr) {
        return userService.getUsersBySubstring(substr);
    }

    /**
     * Gets all users whose nickname contains substr after removing whitespaces and bringing everything to lower case
     * @param substr substring to search
     * @return list of users
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "Get users whose username contains substring after removing whitespaces and bringing everything to lower case")
    @GetMapping("/{user_base_url}/contains")
    public List<User> getUsersBySubstring(@RequestParam String substr) {
        return userService.getUsersBySubstring(substr);
    }

    /**
     * Updates an existing user. Please note that you have to send the entire JSON in request body, even if you want
     * to update a single field. The JSON must follow the same criteria as for create() method except userId must
     * be of the user you want to update.
     * @param user user to update.
     * @throws ResourceNotFoundException if user does not exist
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Updates a user")
    @PutMapping("{user_base_url}")
    public void update(@RequestBody User user) throws ResourceNotFoundException {
        userService.updateUser(user);
    }

    /**
     * Deletes an existing user.
     * @param id user to delete.
     * @throws ResourceNotFoundException if user with id does not exist.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Deletes a user")
    @DeleteMapping("{user_base_url}")
    public void delete(@RequestParam Integer id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }
}
