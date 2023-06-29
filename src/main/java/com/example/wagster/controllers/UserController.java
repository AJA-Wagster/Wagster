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
//        set the hashed password BEFORE saving to the database
        user.setPassword(hash);
        user.setImageURL(url);
        userDao.save(user);

        return "redirect:/";
    }

@GetMapping("/profile/edit")
    public String showEditProfileForm(Model model){
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User userToEdit = userDao.findById(currentUser.getId()).orElse(null);
        model.addAttribute("user", userToEdit);
        return "users/editProfile";
}

@PostMapping("/profile/edit")
    public String editProfileForm(@ModelAttribute("user") User updatedUser) {

    updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

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

    @GetMapping("/admin/users/{id}/delete")
    public String showDeleteUserForm(@PathVariable Long id, Model model) {
        // Retrieve the user to be deleted from the userRepo
        User userToDelete = userDao.findById(id).orElse(null);

        // Pass the user object to the view
        model.addAttribute("user", userToDelete);

        return "users/deleteProfile";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // Retrieve the user to be deleted from the userRepo
        User userToDelete = userDao.findById(id).orElse(null);

        // Perform authorization check here to ensure admin rights
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            // Redirect or show an error message indicating insufficient privileges
            return "redirect:/admin/users"; // Redirect to admin user list
        }

        // Delete the user from the userRepo
        userDao.deleteById(id);

        // Invalidate the current session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/admin/users";
    }


}
