package com.swim.community.controller;


import com.swim.community.annotation.LoginRequired;
import com.swim.community.entity.Comment;
import com.swim.community.entity.DiscussPost;
import com.swim.community.entity.Page;
import com.swim.community.entity.User;
import com.swim.community.service.PostCommentService;
import com.swim.community.service.RedisService;
import com.swim.community.service.UserService;
import com.swim.community.util.CommunityConstant;
import com.swim.community.util.CommunityUtil;
import com.swim.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;


    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private HostHolder hostHolder;



    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage(Model model) {
//        // 上传文件名称
//        String fileName = CommunityUtil.generateUUID();
//        // 设置响应信息
//        StringMap policy = new StringMap();
//        policy.put("returnBody", CommunityUtil.getJSONString(0));
//        // 生成上传凭证
//        Auth auth = Auth.create(accessKey, secretKey);
//        String uploadToken = auth.uploadToken(headerBucketName, fileName, 3600, policy);
//        model.addAttribute("uploadToken", uploadToken);
//        model.addAttribute("fileName", fileName);
        return "/site/setting";
    }


    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片！");
            return "/site/setting";
        }
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".")+1);
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确！");
            return "/site/setting";
        }

        // 生成随机文件名
        String fileName = CommunityUtil.generateUUID() + "." + suffix;
        // 确定文件存放路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！", e);
        }
        //更新用户头像路径
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }

    // 废弃
    @RequestMapping(path = "/header/{fileName:.+}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //System.out.println(fileName);
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        //System.out.println(fileName);
        //System.out.println(suffix);
        response.setContentType("image/" + suffix);
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
                ){
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败："+e.getMessage());
        }
    }

    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword).getBody();
        if (map==null || map.isEmpty()) {
            return "redirect:/logout";
        }else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            return "/site/setting";
        }

    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId).getBody();
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        model.addAttribute("user", user);
        int likeCount = Integer.parseInt(redisService.findUserLikeCount(userId).getBody());
        model.addAttribute("likeCount", likeCount);

        // 关注数量
        long followeeCount = Long.parseLong(redisService.findFolloweeCount(userId, ENTITY_TYPE_USER).getBody());
        model.addAttribute("followeeCount", followeeCount);
        // 粉丝数量
        long followerCount = Long.parseLong(redisService.findFollowerCount(ENTITY_TYPE_USER, userId).getBody());
        model.addAttribute("followerCount", followerCount);
        // 是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            int re = redisService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId).getBody();
            if (re == 1) {
                hasFollowed = true;
            }

        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }

    // 我的帖子
    @RequestMapping(path = "/mypost/{userId}", method = RequestMethod.GET)
    public String getMyPost(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.findUserById(userId).getBody();
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        model.addAttribute("user", user);

        page.setPath("/user/mypost/" + userId);
        page.setRows(Integer.parseInt(postCommentService.findDiscussPostRows(userId).getBody()));

        List<DiscussPost> discussList = postCommentService.
                findDiscussPosts(userId, page.getOffset(), page.getLimit(), 0).getBody();
        List<Map<String, Object>> discussVOList = new ArrayList<>();
        if (discussList != null) {
            for (DiscussPost post : discussList) {
                Map<String, Object> map = new HashMap<>();
                map.put("discussPost", post);
                map.put("likeCount", Long.parseLong(redisService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()).getBody()));
                discussVOList.add(map);
            }
        }
        model.addAttribute("discussPosts", discussVOList);

        return "/site/my-post";
    }

    // 我的回复
    @RequestMapping(path = "/myreply/{userId}", method = RequestMethod.GET)
    public String getMyReply(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.findUserById(userId).getBody();
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        model.addAttribute("user", user);

        page.setPath("/user/myreply/" + userId);
        page.setRows(postCommentService.findUserCount(userId).getBody());

        List<Comment> commentList = postCommentService.
                findUserComments(userId, page.getOffset(), page.getLimit()).getBody();
        List<Map<String, Object>> commentVOList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                Map<String, Object> map = new HashMap<>();
                map.put("comment", comment);
                DiscussPost post = postCommentService.findDiscussPostById(comment.getEntityId()).getBody();
                map.put("discussPost", post);
                commentVOList.add(map);
            }
        }
        model.addAttribute("comments", commentVOList);

        return "/site/my-reply";
    }
}
