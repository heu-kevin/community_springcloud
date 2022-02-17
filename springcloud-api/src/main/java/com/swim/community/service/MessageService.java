package com.swim.community.service;

import com.swim.community.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "springcloud-provider-es")
public interface MessageService {

    @RequestMapping("/community/messageservice/findConversations/{userId}/{offset}/{limit}")
    ResponseEntity<List<Message>> findConversations(@PathVariable("userId") int userId,
                                                           @PathVariable("offset") int offset,
                                                           @PathVariable("limit") int limit);

    @RequestMapping("/community/messageservice/findConversationCount/{userId}")
    ResponseEntity<Integer> findConversationCount(@PathVariable("userId") int userId);

    @RequestMapping("/community/messageservice/findLetters")
    ResponseEntity<List<Message>> findLetters(@RequestParam("conversationId") String conversationId,
                                                     @RequestParam("offset") int offset,
                                                     @RequestParam("limit") int limit);

    @RequestMapping("/community/messageservice/findLetterCount/{conversationId}")
    ResponseEntity<Integer> findLetterCount(@PathVariable("conversationId") String conversationId);

    @RequestMapping(value = "/community/messageservice/findLetterUnreadCount")
    ResponseEntity<Integer> findLetterUnreadCount(@RequestParam("userId") int userId, @RequestParam("conversationId") String conversationId);

    @RequestMapping("/community/messageservice/addMessage")
    ResponseEntity<Integer> addMessage(@RequestBody Message message);

    @RequestMapping("/community/messageservice/readMessage")
    ResponseEntity<Integer> readMessage(@RequestParam("ids") List<Integer> ids);

    @RequestMapping("/community/messageservice/deleteMessage/{id}")
    ResponseEntity<Integer> deleteMessage(@PathVariable("id") int id);

    @RequestMapping("/community/messageservice/findLatestNotice")
    ResponseEntity<Message> findLatestNotice(@RequestParam("userId") int userId, @RequestParam("topic") String topic);

    @RequestMapping("/community/messageservice/findNoticeCount")
    ResponseEntity<Integer> findNoticeCount(@RequestParam("userId") int userId, @RequestParam("topic") String topic);

    @RequestMapping("/community/messageservice/findNoticeUnreadCount")
    ResponseEntity<Integer> findNoticeUnreadCount(@RequestParam("userId") int userId, @RequestParam("topic") String topic);

    @RequestMapping("/community/messageservice/findNotices")
    ResponseEntity<List<Message>> findNotices(@RequestParam("userId") int userId,
                                                     @RequestParam("topic") String topic,
                                                     @RequestParam("offset") int offset,
                                                     @RequestParam("limit") int limit);

}
