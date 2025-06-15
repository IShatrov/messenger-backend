package ru.mipt.messenger.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mipt.messenger.dto.MessageWithTextDto;
import ru.mipt.messenger.models.Message;
import java.util.List;

public interface MessageWithTextRepository extends JpaRepository<Message, Integer> {
    @Query("""
        SELECT new ru.mipt.messenger.dto.MessageWithTextDto(
            m.messageId, m.senderId, m.chatId, c.text, m.sendDttm, m.isRead, m.replyToMessageId, m.updatedDttm)
        FROM Message m
        JOIN Content c ON m.contentId = c.contentId
        WHERE m.chatId = :chatId
        ORDER BY m.sendDttm DESC
        """)
    List<MessageWithTextDto> findLastNMessagesWithText(@Param("chatId") Integer chatId, Pageable pageable);

    @Query("""
        SELECT new ru.mipt.messenger.dto.MessageWithTextDto(
            m.messageId, m.senderId, m.chatId, c.text, m.sendDttm, m.isRead, m.replyToMessageId, m.updatedDttm)
        FROM Message m
        JOIN Content c ON m.contentId = c.contentId
        WHERE m.chatId = :chatId
          AND m.sendDttm < (SELECT m2.sendDttm FROM Message m2 WHERE m2.messageId = :messageId)
        ORDER BY m.sendDttm DESC
        """)
    List<MessageWithTextDto> findNMessagesBeforeMessageWithText(@Param("chatId") Integer chatId, @Param("messageId") Integer messageId, Pageable pageable);
} 