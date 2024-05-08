package org.mrchv.springrestapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String showAdminPage() {
        return "admin-page";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "user-page";
    }
}
