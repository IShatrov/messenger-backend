package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.models.Content;
import ru.mipt.messenger.repositories.ContentRepository;

@Service
@AllArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public Content getById(Integer id) {
        return contentRepository.findById(id).orElse(null);
    }

    public Content create(String text) {
        return contentRepository.save(new Content(text));
    }
}
