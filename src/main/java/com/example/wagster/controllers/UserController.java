package com.example.wagster.controllers;

import com.example.wagster.models.User;
import com.example.wagster.repos.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(name = "url") String url) {
        // Perform registration logic here using userRepo
        // e.g., save the user to the database using userRepo.save(user)

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setImageURL(url);

        // Assign the "ROLE_ADMIN" authority to the user
        user.setAdmin(false);

        userDao.save(user);

        return "redirect:/";
    }

    @GetMapping("/user/profile")
    public String showProfile(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(currentUser.getId()).orElse(null);
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model){
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User userToEdit = userDao.findById(currentUser.getId()).orElse(null);

        model.addAttribute("user", userToEdit);
        return "users/editProfile";
}

@PostMapping("/profile/edit")
    public String editProfileForm(@ModelAttribute("user") User updatedUser, @RequestParam(name = "url") String url) {

    updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    updatedUser.setImageURL(url);

    userDao.save(updatedUser);

    return "redirect:/profile";
}

@GetMapping("/profile/delete")
    public String showDeleteProfileForm(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userToDelete = userDao.findById(currentUser.getId()).orElse(null);
        model.addAttribute("user", userToDelete);
        return "users/deleteProfile";
    }

//    POST MAPPING to finish delete function
@PostMapping("/profile/delete")
public String deleteProfile(@ModelAttribute("user") User userToDelete, HttpServletRequest request) {
    userDao.deleteById(userToDelete.getId());

    // Invalidate the current session
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    return "redirect:/";
    }



}
