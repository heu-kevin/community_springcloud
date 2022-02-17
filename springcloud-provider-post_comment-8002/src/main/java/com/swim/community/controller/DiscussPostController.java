package com.swim.community.controller;

import com.swim.community.entity.DiscussPost;
import com.swim.community.service.DiscussPostService;
import com.swim.community.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postservice")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping("/findDiscussPosts/{userId}/{offset}/{limit}/{orderMode}")
    public ResponseEntity<List<DiscussPost>> findDiscussPosts(@PathVariable("userId") Integer userId,
                                                              @PathVariable("offset") Integer offset,
                                                              @PathVariable("limit") Integer limit,
                                                              @PathVariable("orderMode") Integer orderMode
    ) {
        return ResponseEntity.ok(discussPostService.findDiscussPosts(userId, offset, limit, orderMode));
        //return ResponseEntity.ok(discussPostService.findDiscussPosts(0, 0, 10, 0));
    }

    @RequestMapping("/findDiscussPostRows/{userId}")
    public ResponseEntity<String> findDiscussPostRows(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(discussPostService.findDiscussPostRows(userId) + "");
    }

    @RequestMapping(value = "/addDiscussPost", method = RequestMethod.POST)
    public ResponseEntity<Integer> addDiscussPost(@RequestBody DiscussPost post) {
        return ResponseEntity.ok(discussPostService.addDiscussPost(post));
    }

    @RequestMapping("/findDiscussPostById/{id}")
    public ResponseEntity<DiscussPost> findDiscussPostById(@PathVariable("id") int id) {
        return ResponseEntity.ok(discussPostService.findDiscussPostById(id));
    }

    @RequestMapping("/updateCommentCount/{id}/{commentCount}")
    public ResponseEntity<Integer> updateCommentCount(@PathVariable("id") int id, @PathVariable("commentCount") int commentCount) {
        return ResponseEntity.ok(discussPostService.updateCommentCount(id, commentCount));
    }

    @RequestMapping("/updateType/{id}/{type}")
    public ResponseEntity<Integer> updateType(@PathVariable("id") int id, @PathVariable("type") int type) {
        return ResponseEntity.ok(discussPostService.updateType(id, type));
    }

    @RequestMapping("/updateStatus/{id}/{status}")
    public ResponseEntity<Integer> updateStatus(@PathVariable("id") int id, @PathVariable("status") int status) {
        return ResponseEntity.ok(discussPostService.updateStatus(id, status));
    }

    @RequestMapping("/updateScore/{id}/{score}")
    public ResponseEntity<Integer> updateScore(@PathVariable("id") int id, @PathVariable("score") double score) {
        return ResponseEntity.ok(discussPostService.updateScore(id, score));
    }

}
