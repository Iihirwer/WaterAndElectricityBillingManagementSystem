package electricity.com.waterandelectricitybillingmanagementsystem.controller;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Role;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.service.UserService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already in use.");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setEnabled(false); // Await admin verification
        user.setEmailVerified(false); // Await email verification
        
        // Generate 6-digit OTP
        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        user.setVerificationToken(otp);
        
        userService.save(user);
        emailService.sendOtpEmail(user.getEmail(), otp);
        
        return "redirect:/verify-otp?email=" + user.getEmail();
    }

    @GetMapping("/verify-otp")
    public String verifyOtpPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
        if (userService.verifyOtp(email, otp)) {
            return "redirect:/login?verified";
        } else {
            model.addAttribute("email", email);
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "verify-otp";
        }
    }
}
