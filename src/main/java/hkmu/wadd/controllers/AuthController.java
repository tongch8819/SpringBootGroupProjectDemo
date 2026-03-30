package hkmu.wadd.controllers;

import hkmu.wadd.models.AppUser;
import hkmu.wadd.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Returns login.jsp
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register"; // Returns register.jsp
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") AppUser user) {
        // 1. Hash the password before saving!
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 2. Assign the default role (Student)
        user.setRole("ROLE_STUDENT");
        
        // 3. Save to database
        userRepository.save(user);
        
        // 4. Redirect to login page with a success message flag
        return "redirect:/login?registered";
    }
}