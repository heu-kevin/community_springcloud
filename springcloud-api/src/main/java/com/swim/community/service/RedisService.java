package com.swim.community.service;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@FeignClient(value = "springcloud-provider-redis")
public interface RedisService {
    // like service======================
    @RequestMapping(value = "/community/likeservice/like/{userId}/{entityType}/{entityId}/{entityUserId}", method = RequestMethod.POST)
    void like(@PathVariable("userId") int userId,
              @PathVariable("entityType") int entityType,
              @PathVariable("entityId") int entityId,
              @PathVariable("entityUserId") int entityUserId);

    // 查询某实体点赞的数量
    @RequestMapping(value = "/community/likeservice/findEntityLikeCount/{entityType}/{entityId}", method = RequestMethod.GET)
    ResponseEntity<String> findEntityLikeCount(@PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    // 查询某人对某实体的点赞状态
    @RequestMapping("/community/likeservice/findEntityLikeStatus/{userId}/{entityType}/{entityId}")
    ResponseEntity<String> findEntityLikeStatus(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    // 查询某个用户获得的赞数量
    @RequestMapping("/community/likeservice/findUserLikeCount/{userId}")
    ResponseEntity<String> findUserLikeCount(@PathVariable("userId") int userId);

    // follow service=======================

    @RequestMapping("/community/followservice/follow/{userId}/{entityType}/{entityId}")
    void follow(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    @RequestMapping("/community/followservice/unfollow/{userId}/{entityType}/{entityId}")
    void unfollow(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    @RequestMapping("/community/followservice/findFolloweeCount/{userId}/{entityType}")
    ResponseEntity<String> findFolloweeCount(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType);

    @RequestMapping("/community/followservice/findFollowerCount/{entityType}/{entityId}")
    ResponseEntity<String> findFollowerCount(@PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    @RequestMapping("/community/followservice/hasFollowed/{userId}/{entityType}/{entityId}")
    ResponseEntity<Integer> hasFollowed(@PathVariable("userId") int userId, @PathVariable("entityType") int entityType, @PathVariable("entityId") int entityId);

    @RequestMapping("/community/followservice/findFollowees/{userId}/{offset}/{limit}")
    ResponseEntity<List<Map<String, Object>>> findFollowees(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit);

    @RequestMapping("/community/followservice/findFollowers/{userId}/{offset}/{limit}")
    ResponseEntity<List<Map<String, Object>>> findFollowers(@PathVariable("userId") int userId, @PathVariable("offset") int offset, @PathVariable("limit") int limit);

    // data service=================
    @RequestMapping("/community/dataservice/recordUV/{ip}")
    void recordUV(@PathVariable("ip") String ip);

    @RequestMapping("/community/dataservice/calculateUV")
    ResponseEntity<String> calculateUV(@RequestParam("start") Date start, @RequestParam("end") Date end);

    @RequestMapping("/community/dataservice/recordDAU/{userId}")
    void recordDAU(@PathVariable("userId") int userId);

    @RequestMapping("/community/dataservice/calculateDAU")
    ResponseEntity<String> calculateDAU(@RequestParam("start") Date start, @RequestParam("end") Date end);
}
