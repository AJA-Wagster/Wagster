package com.example.wagster.controllers;

import com.example.wagster.config.PasswordPolicy;
import com.example.wagster.models.Post;
import com.example.wagster.models.User;
import com.example.wagster.repos.PostRepo;
import com.example.wagster.repos.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private UserRepo userDao;
    private PostRepo postDao;

    private PasswordEncoder passwordEncoder;

    public AdminController(UserRepo userDao, PostRepo postDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register/admin")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("AdminUser", new User());
        return "admin-registration";
    }

//    @PostMapping("/register/admin")
//    public String registerAdmin(@ModelAttribute User user) {
//
//        System.out.println(user.getPassword());
//
//        String hash = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hash);
//
//        // Assign the "ROLE_ADMIN" authority to the user
//        user.setAdmin(true);
//
//        userDao.save(user);
//
//        return "redirect:/";
//    }

    @PostMapping("/register/admin")
    public String registerUser(@ModelAttribute User user, @RequestParam(name = "url") String url, RedirectAttributes redirectAttributes) {
        // Perform registration logic here using userRepo
        // e.g., save the user to the database using userRepo.save(user)

        if (PasswordPolicy.isValid(user.getPassword())){
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            user.setImageURL(url);

            // Assign the "ROLE_ADMIN" authority to the user
            user.setAdmin((byte) 1);

            userDao.save(user);

        }else {
            String errorMessage = "Password does not meet the requirements. Passwords must meet all of the following, one lowercase letter, one uppercase letter, one special character(!@#&()–[{}]:;',?/*~$^+=<>), and must be between 8-16 characters.";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/register/admin";
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
                .anyMatch(auth -> auth.getAuthority().equals(false));
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

        return "redirect:/";
    }

    @PostMapping("/admin/posts/{id}/delete")
    public String deletePost(@PathVariable long id, HttpServletRequest request) {
        // Retrieve the post to be deleted from the postDao
        Post postToDelete = postDao.findById(id).orElse(null);

        // Perform authorization check here to ensure admin rights
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(true));
        if (!isAdmin) {
            // Redirect or show an error message indicating insufficient privileges
            return "redirect:/posts/" + id; // Redirect to post details page
        }

        // Delete the post from the postDao
        postDao.deleteById(id);

        return "redirect:/";
    }

}
