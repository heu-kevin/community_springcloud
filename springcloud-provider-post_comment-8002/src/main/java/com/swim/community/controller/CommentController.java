package com.swim.community.controller;

import com.swim.community.entity.Comment;
import com.swim.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentservice")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/findCommentsByEntity/{entityType}/{entityId}/{offset}/{limit}")
    public ResponseEntity<List<Comment>> findCommentsByEntity(@PathVariable("entityType") int entityType,
                                                              @PathVariable("entityId") int entityId,
                                                              @PathVariable("offset") int offset,
                                                              @PathVariable("limit") int limit) {
        return ResponseEntity.ok(commentService.findCommentsByEntity(entityType, entityId, offset, limit));
    }

    @RequestMapping("/findCommentCount/{entityType}/{entityId}")
    public ResponseEntity<Integer> findCommentCount(@PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        return ResponseEntity.ok(commentService.findCommentCount(entityType, entityId));
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseEntity<Integer> addComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.addComment(comment));
    }

    @RequestMapping("/findCommentById/{id}")
    public ResponseEntity<Comment> findCommentById(@PathVariable("id") int id) {
        return ResponseEntity.ok(commentService.findCommentById(id));
    }

    @RequestMapping("/findUserComments/{userId}/{offset}/{limit}")
    public ResponseEntity<List<Comment>> findUserComments(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(commentService.findUserComments(userId, offset, limit));
    }

    @RequestMapping("/findUserCount/{userId}")
    public ResponseEntity<Integer> findUserCount(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(commentService.findUserCount(userId));
    }

}
