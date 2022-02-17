package com.swim.community.service;

import com.swim.community.entity.Comment;
import com.swim.community.entity.DiscussPost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "SPRINGCLOUD-PROVIDER-POST-COMMENT")
public interface PostCommentService {

    //PostService

    @RequestMapping("/community/postservice/findDiscussPosts/{userId}/{offset}/{limit}/{orderMode}")
    ResponseEntity<List<DiscussPost>> findDiscussPosts(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit, @PathVariable("orderMode") int orderMode);

    @GetMapping("/community/postservice/findDiscussPostRows/{userId}")
    ResponseEntity<String> findDiscussPostRows(@PathVariable("userId") Integer userId);

    @RequestMapping(value = "/community/postservice/addDiscussPost", method = RequestMethod.POST)
    ResponseEntity<Integer> addDiscussPost(@RequestBody DiscussPost post);

    @RequestMapping("/community/postservice/findDiscussPostById/{id}")
    ResponseEntity<DiscussPost> findDiscussPostById(@PathVariable("id") int id);

    @RequestMapping("/community/postservice/updateCommentCount/{id}/{commentCount}")
    ResponseEntity<Integer> updateCommentCount(@PathVariable("id") int id, @PathVariable("commentCount") int commentCount);

    @RequestMapping("/community/postservice/updateType/{id}/{type}")
    ResponseEntity<Integer> updateType(@PathVariable("id") int id, @PathVariable("type") int type);

    @RequestMapping("/community/postservice/updateStatus/{id}/{status}")
    ResponseEntity<Integer> updateStatus(@PathVariable("id") int id, @PathVariable("status") int status);

    @RequestMapping("/community/postservice/updateScore/{id}/{score}")
    ResponseEntity<Integer> updateScore(@PathVariable("id") int id, @PathVariable("score") double score);

    // CommentService
    @RequestMapping("/community/commentservice/findCommentsByEntity/{entityType}/{entityId}/{offset}/{limit}")
    ResponseEntity<List<Comment>> findCommentsByEntity(@PathVariable("entityType") int entityType,
                                                       @PathVariable("entityId") int entityId,
                                                       @PathVariable("offset") int offset,
                                                       @PathVariable("limit") int limit);

    @RequestMapping("/community/commentservice/findCommentCount/{entityType}/{entityId}")
    ResponseEntity<Integer> findCommentCount(@PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    @RequestMapping(value = "/community/commentservice/addComment", method = RequestMethod.POST)
    ResponseEntity<Integer> addComment(@RequestBody Comment comment);

    @RequestMapping("/community/commentservice/findCommentById/{id}")
    ResponseEntity<Comment> findCommentById(@PathVariable("id") int id);

    @RequestMapping("/community/commentservice/findUserComments/{userId}/{offset}/{limit}")
    ResponseEntity<List<Comment>> findUserComments(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit);

    @RequestMapping("/community/commentservice/findUserCount/{userId}")
    ResponseEntity<Integer> findUserCount(@PathVariable("userId") int userId);
}
