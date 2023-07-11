package com.example.wagster.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

    @GetMapping("/aboutus")
    public String aboutus() {
        return "/aboutus";
    }
}
