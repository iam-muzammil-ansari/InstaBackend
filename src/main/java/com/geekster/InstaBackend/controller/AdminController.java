package com.geekster.InstaBackend.controller;

import com.geekster.InstaBackend.model.Admin;
import com.geekster.InstaBackend.model.Post;
import com.geekster.InstaBackend.model.dto.SignInInput;
import com.geekster.InstaBackend.model.dto.SignUpOutput;
import com.geekster.InstaBackend.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;


    @Autowired
    AuthenticationAdminService authenticationAdminService;

    @PostMapping("signup")
    public SignUpOutput signUpAdmin(@RequestBody @Valid Admin admin) {
        return adminService.signUpAdmin(admin);
    }

    @PostMapping("signIn")
    public String signInAdmin(@RequestBody @Valid SignInInput signInInput) {
        return adminService.signInAdmin(signInInput);
    }


    @GetMapping("posts")
    public List<Post> getAllPosts(@RequestParam  String email, @RequestParam String token){
        if(authenticationAdminService.authenticateAdmin(email,token)) {
            return userService.getAllPosts();
        }
        return null;
    }

    @DeleteMapping("removepost")
    public String removePostByAdmin(String email, String token, Integer postId) {
        if(authenticationAdminService.authenticateAdmin(email,token)) {
            userService.removeInstaPostByAdmin(postId);
            return "This post is deleted by admin";
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("signOut")
    public String signOutAdmin(String email, String token)
    {
        if(authenticationAdminService.authenticateAdmin(email,token)) {
            return adminService.signOutAdmin(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }
    }

}
