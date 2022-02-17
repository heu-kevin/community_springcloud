package com.swim.community.controller;

import com.swim.community.entity.DiscussPost;
import com.swim.community.entity.Page;
import com.swim.community.entity.User;
import com.swim.community.service.PostCommentService;
import com.swim.community.service.RedisService;

import com.swim.community.service.UserService;
import com.swim.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {
    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService likeService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page,
                               @RequestParam(name = "orderMode", defaultValue = "0") int orderMode) {
        //方法调用前，SpringMVC会自动实例化Model和Page，并将page注入model
        //所以，在thymeleaf中可以直接访问Page对象中的数据
        int rows = Integer.parseInt(postCommentService.findDiscussPostRows(0).getBody());
        page.setRows(rows);
        page.setPath("/index?orderMode=" + orderMode);
        List<DiscussPost> list = postCommentService
                .findDiscussPosts(0, page.getOffset(), page.getLimit(), orderMode).getBody();
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for(DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId()).getBody();
                map.put("user", user);

                // long likeCount = 0;
                long likeCount = Long.parseLong(
                        likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()).getBody());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("orderMode", orderMode);
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

    @RequestMapping(path = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/error/404";
    }

}
