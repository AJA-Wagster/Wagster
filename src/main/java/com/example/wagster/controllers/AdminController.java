package com.example.wagster.controllers;

import com.example.wagster.models.Post;
import com.example.wagster.models.User;
import com.example.wagster.repos.PostRepo;
import com.example.wagster.repos.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private UserRepo userDao;
    private PostRepo postDao;

    public AdminController(UserRepo userDao, PostRepo postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
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

    @PostMapping("/admin/posts/{id}/delete")
    public String deletePost(@PathVariable long id, HttpServletRequest request) {
        // Retrieve the post to be deleted from the postDao
        Post postToDelete = postDao.findById(id).orElse(null);

        // Perform authorization check here to ensure admin rights
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            // Redirect or show an error message indicating insufficient privileges
            return "redirect:/posts/" + id; // Redirect to post details page
        }

        // Delete the post from the postDao
        postDao.deleteById(id);

        return "redirect:/posts";
    }
}
