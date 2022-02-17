package com.swim.community.controller;

import com.netflix.discovery.converters.Auto;
import com.netflix.ribbon.proxy.annotation.Http;
import com.swim.community.entity.LoginTicket;
import com.swim.community.entity.User;
import com.swim.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/userservice")
public class UseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUserById/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Integer id) {
        // return new ResponseEntity(userService.findUserById(id), HttpStatus.OK);
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @RequestMapping("/activation/{userId}/{code}")
    public ResponseEntity<Integer> activation(@PathVariable("userId") int userId, @PathVariable("code") String code) {
        return ResponseEntity.ok(userService.activation(userId, code));
    }

    @RequestMapping("/login/{username}/{password}/{expiredSeconds}")
    public ResponseEntity<Map<String, Object>> login(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("expiredSeconds") long expiredSeconds) {
        return ResponseEntity.ok(userService.login(username, password, expiredSeconds));
    }

    @RequestMapping("/logout/{ticket}")
    public void logout(@PathVariable("ticket") String ticket) {
        userService.logout(ticket);
    }

    @RequestMapping("/findLoginTicket/{ticket}")
    public ResponseEntity<LoginTicket> findLoginTicket(@PathVariable("ticket") String ticket) {
        return ResponseEntity.ok(userService.findLoginTicket(ticket));
    }

    @RequestMapping("/updateHeader")
    public ResponseEntity<Integer> updateHeader(@RequestParam("userId") int userId, @RequestParam("headerUrl") String headerUrl) {

        System.out.println(userId);
        System.out.println(headerUrl);
        return ResponseEntity.ok(userService.updateHeader(userId, headerUrl));
    }

    @RequestMapping("/checkEmail/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.checkEmail(email));
    }

    @RequestMapping("/resetPassword/{email}/{password}")
    public ResponseEntity<Map<String, Object>> resetPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
        return ResponseEntity.ok(userService.resetPassword(email, password));
    }

    @RequestMapping("/updatePassword/{userId}/{oldPassword}/{newPassword}")
    public ResponseEntity<Map<String, Object>> updatePassword(@PathVariable("userId") int userId, @PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword) {
        return ResponseEntity.ok(userService.updatePassword(userId, oldPassword, newPassword));
    }

    @RequestMapping("/findUserByName/{username}")
    public ResponseEntity<User> findUserByName(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findUserByName(username));
    }

//    @RequestMapping("/getAuthorities/{userId}")
//    public ResponseEntity<Collection<? extends GrantedAuthority>> getAuthorities(@PathVariable("userId") int userId) {
//        return ResponseEntity.ok(userService.getAuthorities(userId));
//    }

}
