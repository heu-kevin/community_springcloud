package com.swim.community.service;

import com.swim.community.entity.LoginTicket;
import com.swim.community.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@FeignClient(value = "springcloud-provider-user")
public interface UserService {

    @RequestMapping("/community/userservice/findUserById/{id}")
    ResponseEntity<User> findUserById(@PathVariable("id") Integer id);

    @RequestMapping(value = "/community/userservice/register", method = RequestMethod.POST)
    ResponseEntity<Map<String, Object>> register(@RequestBody User user);

    @RequestMapping("/community/userservice/activation/{userId}/{code}")
    ResponseEntity<Integer> activation(@PathVariable("userId") int userId, @PathVariable("code") String code);

    @RequestMapping("/community/userservice/login/{username}/{password}/{expiredSeconds}")
    ResponseEntity<Map<String, Object>> login(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("expiredSeconds") long expiredSeconds);

    @RequestMapping("/community/userservice/logout/{ticket}")
    void logout(@PathVariable("ticket") String ticket);

    @RequestMapping("/community/userservice/findLoginTicket/{ticket}")
    ResponseEntity<LoginTicket> findLoginTicket(@PathVariable("ticket") String ticket);

    @RequestMapping("/community/userservice/updateHeader")
    ResponseEntity<Integer> updateHeader(@RequestParam("userId") int userId, @RequestParam("headerUrl") String headerUrl);

    @RequestMapping("/community/userservice/checkEmail/{email}")
    ResponseEntity<Map<String, Object>> checkEmail(@PathVariable("email") String email);

    @RequestMapping("/community/userservice/resetPassword/{email}/{password}")
    ResponseEntity<Map<String, Object>> resetPassword(@PathVariable("email") String email, @PathVariable("password") String password);

    @RequestMapping("/community/userservice/updatePassword/{userId}/{oldPassword}/{newPassword}")
    ResponseEntity<Map<String, Object>> updatePassword(@PathVariable("userId") int userId, @PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword);

    @RequestMapping("/community/userservice/findUserByName/{username}")
    ResponseEntity<User> findUserByName(@PathVariable("username") String username);

//    @RequestMapping("/community/userservice/getAuthorities/{userId}")
//    ResponseEntity<Collection<? extends GrantedAuthority>> getAuthorities(@PathVariable("userId") int userId);

}
