package com.swim.community.controller;

import com.swim.community.entity.Message;
import com.swim.community.service.MessageService;
import com.swim.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messageservice")
public class MessageController implements CommunityConstant {
    @Autowired
    private MessageService messageService;

    @RequestMapping("/findConversations/{userId}/{offset}/{limit}")
    public ResponseEntity<List<Message>> findConversations(@PathVariable("userId") int userId,
                                                          @PathVariable("offset") int offset,
                                                          @PathVariable("limit") int limit) {
        return ResponseEntity.ok(messageService.findConversations(userId, offset, limit));
    }

    @RequestMapping("/findConversationCount/{userId}")
    public ResponseEntity<Integer> findConversationCount(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(messageService.findConversationCount(userId));
    }

    @RequestMapping("/findLetters")
    public ResponseEntity<List<Message>> findLetters(@RequestParam("conversationId") String conversationId,
                                     @RequestParam("offset") int offset,
                                     @RequestParam("limit") int limit) {
        return ResponseEntity.ok(messageService.findLetters(conversationId, offset, limit));
    }

    @RequestMapping("/findLetterCount/{conversationId}")
    public ResponseEntity<Integer> findLetterCount(@PathVariable("conversationId") String conversationId) {
        return ResponseEntity.ok(messageService.findLetterCount(conversationId));
    }

    @RequestMapping(value = "/findLetterUnreadCount")
    public ResponseEntity<Integer> findLetterUnreadCount(@RequestParam("userId") int userId, @RequestParam("conversationId") String conversationId) {
        if (conversationId.equals(NULL_PARAM))
            return ResponseEntity.ok(messageService.findLetterUnreadCount(userId, null));
        else
            return ResponseEntity.ok(messageService.findLetterUnreadCount(userId, conversationId));
    }

    @RequestMapping("/addMessage")
    public ResponseEntity<Integer> addMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.addMessage(message));
    }

    @RequestMapping("/readMessage")
    public ResponseEntity<Integer> readMessage(@RequestParam("ids") List<Integer> ids) {
        return ResponseEntity.ok(messageService.readMessage(ids));
    }

    @RequestMapping("/deleteMessage/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("id") int id) {
        return ResponseEntity.ok(messageService.deleteMessage(id));
    }

    @RequestMapping("/findLatestNotice")
    public ResponseEntity<Message> findLatestNotice(@RequestParam("userId") int userId, @RequestParam("topic") String topic) {
        return ResponseEntity.ok(messageService.findLatestNotice(userId, topic));
    }

    @RequestMapping("/findNoticeCount")
    public ResponseEntity<Integer> findNoticeCount(@RequestParam("userId") int userId, @RequestParam("topic") String topic) {
        return ResponseEntity.ok(messageService.findNoticeCount(userId, topic));
    }

    @RequestMapping("/findNoticeUnreadCount")
    public ResponseEntity<Integer> findNoticeUnreadCount(@RequestParam("userId") int userId, @RequestParam("topic") String topic) {
        if (topic.equals(NULL_PARAM))
            return ResponseEntity.ok(messageService.findNoticeUnreadCount(userId, null));
        else
            return ResponseEntity.ok(messageService.findNoticeUnreadCount(userId, topic));
    }

    @RequestMapping("/findNotices")
    public ResponseEntity<List<Message>> findNotices(@RequestParam("userId") int userId,
                                                     @RequestParam("topic") String topic,
                                                     @RequestParam("offset") int offset,
                                                     @RequestParam("limit") int limit) {
        return ResponseEntity.ok(messageService.findNotices(userId, topic, offset, limit));
    }

}
