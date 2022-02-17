package com.swim.community.controller;

import com.swim.community.service.LikeInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likeservice")
public class LikeController {

    @Autowired
    private LikeInnerService likeInnerService;

    @RequestMapping(value = "/like/{userId}/{entityType}/{entityId}/{entityUserId}", method = RequestMethod.POST)
    public void like(@PathVariable("userId") int userId,
                     @PathVariable("entityType") int entityType,
                     @PathVariable("entityId") int entityId,
                     @PathVariable("entityUserId") int entityUserId) {
        likeInnerService.like(userId, entityType, entityId, entityUserId);
        // return ;
    }

    // 查询某实体点赞的数量
    @RequestMapping(value = "/findEntityLikeCount/{entityType}/{entityId}", method = RequestMethod.GET)
    public ResponseEntity<String> findEntityLikeCount(@PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        return ResponseEntity.ok(likeInnerService.findEntityLikeCount(entityType, entityId) + "");
    }

    // 查询某人对某实体的点赞状态
    @RequestMapping("/findEntityLikeStatus/{userId}/{entityType}/{entityId}")
    public ResponseEntity<String> findEntityLikeStatus(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId) {
        return ResponseEntity.ok(likeInnerService.findEntityLikeStatus(userId, entityType, entityId) + "");
    }

    // 查询某个用户获得的赞数量
    @RequestMapping("/findUserLikeCount/{userId}")
    public ResponseEntity<String> findUserLikeCount(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(likeInnerService.findUserLikeCount(userId) + "");
    }

}
