package com.swim.community.controller;

import com.swim.community.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/followservice")
public class FollowController {

    @Autowired
    private FollowService followService;

    @RequestMapping("/follow/{userId}/{entityType}/{entityId}")
    public void follow(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        followService.follow(userId, entityType, entityId);
    }

    @RequestMapping("/unfollow/{userId}/{entityType}/{entityId}")
    public void unfollow(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        followService.unfollow(userId, entityType, entityId);
    }

    @RequestMapping("/findFolloweeCount/{userId}/{entityType}")
    public ResponseEntity<String> findFolloweeCount(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType) {
        return ResponseEntity.ok(followService.findFolloweeCount(userId, entityType) + "");
    }

    @RequestMapping("/findFollowerCount/{entityType}/{entityId}")
    public ResponseEntity<String> findFollowerCount(@PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        return ResponseEntity.ok(followService.findFollowerCount(entityType, entityId) + "");
    }

    @RequestMapping("/hasFollowed/{userId}/{entityType}/{entityId}")
    public ResponseEntity<Integer> hasFollowed(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        Integer ans = 0;
        if (followService.hasFollowed(userId, entityType, entityId)) {
            ans = 1;
            return ResponseEntity.ok(ans);
        }else {
            return ResponseEntity.ok(ans);
        }
    }

    @RequestMapping("/findFollowees/{userId}/{offset}/{limit}")
    public ResponseEntity<List<Map<String, Object>>> findFollowees(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(followService.findFollowees(userId, offset, limit));
    }

    @RequestMapping("/findFollowers/{userId}/{offset}/{limit}")
    public ResponseEntity<List<Map<String, Object>>> findFollowers(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(followService.findFollowers(userId, offset, limit));
    }

}
