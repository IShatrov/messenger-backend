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

import ru.mipt.messenger.models.Chat;
import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.services.ChatService;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Creates a new chat in the database. Please make sure that request body matches the following criteria:
     * 1) ChatId is null. This is required to correctly generate chatId in the database.
     * It is not required to explicitly assign chatId to null in the JSON since it defaults to null.
     * 2) Chat name is not null, not blank, have valid length 
     * 3) If creationDttm is not null, it matches pattern "yyyy-MM-dd'T'HH:mm:ss.SSSZ". creationDttm 
     * defaults to current UTC time even if it is not null in the request.
     * @see <a href="https://docs.spring.io/spring-framework/docs/3.2.4.RELEASE_to_4.0.0.M3/Spring%20Framework%203.2.4.RELEASE/org/springframework/format/annotation/DateTimeFormat.ISO.html">Spring docs</a>
     * 4) Type cant be nul
     * @param chat Chat to create
     * @throws DataIntegrityViolationException if chatId is not null or other database error occurs.
     * @throws MethodArgumentNotValidException if any field of chat is invalid.
     * @throws HttpMessageNotReadableException if JSON is invalid.
     */
    @Operation(summary = "Creates a new chat", description = """
      Creates a new chat in the database. Please make sure that request body matches the following criteria:
      1) ChatId is null. This is required to correctly generate chatId in the database.
      It is not required to explicitly assign chatId to null in the JSON since it defaults to null.
      2) Chat name is not null, not blank, have valid length 
      3) If creationDttm is not null, it matches pattern "yyyy-MM-dd'T'HH:mm:ss.SSSZ". creationDttm 
      defaults to current UTC time even if it is not null in the request.
      @see <a href="https://docs.spring.io/spring-framework/docs/3.2.4.RELEASE_to_4.0.0.M3/Spring%20Framework%203.2.4.RELEASE/org/springframework/format/annotation/DateTimeFormat.ISO.html">Spring docs</a>
      4) Type cant be nul
      @param chat Chat to create
      @throws DataIntegrityViolationException if chatId is not null or other database error occurs.
      @throws MethodArgumentNotValidException if any field of chat is invalid.
      @throws HttpMessageNotReadableException if JSON is invalid.
            """)
    @PostMapping("{chat_base_url}")
    public void create(@Valid @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Chat chat) throws
            DataIntegrityViolationException, MethodArgumentNotValidException, HttpMessageNotReadableException {
        chatService.createChat(chat);
    }

    /**
     * Reads by chatId if chatId is not null or by firstname if chatId is null.
     * @param id chatId to read.
     * @param firstname firstname to read.
     * @return List of matching chats. If no chats have matching chatId or firstname, returns an empty list.
     * @throws IllegalArgumentException if both chatId and firstname are null.
     */
    @Operation(summary = "Reads chat by chatId or firstname", description = """
                    Reads by chatId if chatId is not null or by firstname if chatId is null. Returns a list of found chats.
                    """)
    @GetMapping("{chat_base_url}")
    public List<Chat> read(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name)
            throws IllegalArgumentException {
        if (id != null) {
            var result = chatService.readChatById(id);
            return (result == null) ? List.of() : List.of(result);
        }

        if (name != null) {
            return chatService.readChatsByName(name);
        }

        throw new IllegalArgumentException("At least one of the arguments must not be null");
    }

    /**
     * Get all chats from db using service method getAllChats
     * @return list of chats
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Get all chats")
    @GetMapping("/chats")
    public List<Chat> getAll() {
        return chatService.getAllChats();
    }

    /*
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Updates a chat")
    @PutMapping("{chat_base_url}")
    public void update(@RequestBody Chat chat) throws ResourceNotFoundException {
        chatService.updateChat(chat);
    }
    */

    /**
     * Deletes an existing chat.
     * @param id chat to delete.
     * @throws ResourceNotFoundException if chat with id does not exist.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Operation(summary = "Deletes a chat")
    @DeleteMapping("{chat_base_url}")
    public void delete(@RequestParam Integer id) throws ResourceNotFoundException {
        chatService.deleteChat(id);
    }
}
