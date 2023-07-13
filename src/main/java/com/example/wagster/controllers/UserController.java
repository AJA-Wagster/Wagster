package com.example.wagster.controllers;

import com.example.wagster.config.PasswordPolicy;
import com.example.wagster.models.User;
import com.example.wagster.repos.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("error", (String) model.asMap().get("errorMessage"));
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(name = "url") String url, Model model, RedirectAttributes redirectAttributes) {
        // Perform registration logic here using userRepo
        // e.g., save the user to the database using userRepo.save(user)

        if (PasswordPolicy.isValid(user.getPassword())){
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            if (url.length() < 3) {
                user.setImageURL("https://img.freepik.com/premium-photo/golden-retriever-lying-panting-isolated-white_191971-16974.jpg");
            }

            // Assign the "ROLE_ADMIN" authority to the user
            user.setAdmin((byte) 0);

            userDao.save(user);
        }else {
            String errorMessage = "Password does not meet the requirements. Passwords must meet all of the following, one lowercase letter, one uppercase letter, one special character ( ?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) , and must be between 8-16 characters.";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/register";
        }


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

@GetMapping("/profile/delete/{userId}")
public String showUserForm(@PathVariable(name = "userId") Long id, Model model){
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        return "users/deleteProfile";
}

@PostMapping("profile/delete/{userId}")
public String deleteUser(@PathVariable(name = "userId") Long id){
    userDao.deleteById(id);
    return "redirect:/feed";
}

}
