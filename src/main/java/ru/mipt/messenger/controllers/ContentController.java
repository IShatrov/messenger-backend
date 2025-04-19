package ru.mipt.messenger.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.messenger.models.Content;
import ru.mipt.messenger.services.ContentService;

@RestController
@AllArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("${content_base_url}")
    public Content read(Integer id) {
        return contentService.getById(id);
    }
}
