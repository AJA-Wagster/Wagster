package com.example.wagster.controllers;

import com.example.wagster.models.User;
import com.example.wagster.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


//        import com.example.wagster.models.User;
//        import com.example.wagster.repos.UserRepo;
//        import org.springframework.security.crypto.password.PasswordEncoder;
//        import org.springframework.stereotype.Controller;
//        import org.springframework.ui.Model;
//        import org.springframework.web.bind.annotation.GetMapping;
//        import org.springframework.web.bind.annotation.ModelAttribute;
//        import org.springframework.web.bind.annotation.PostMapping;

// RegistrationController.java
@Controller
public class UserController  {

    private UserRepo userDao;
    private PasswordEncoder passwordEncoder;


    public UserController(UserRepo userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        // Perform registration logic here using userRepo
        // e.g., save the user to the database using userRepo.save(user)


        String hash = passwordEncoder.encode(user.getPassword());
//        set the hashed password BEFORE saving to the database
        user.setPassword(hash);
        userDao.save(user);


        return "redirect:/";
    }



}














