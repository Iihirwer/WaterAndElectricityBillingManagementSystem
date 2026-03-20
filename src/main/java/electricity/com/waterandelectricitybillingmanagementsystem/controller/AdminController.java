package electricity.com.waterandelectricitybillingmanagementsystem.controller;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.MeterType;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.service.BillingService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.MeterService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final MeterService meterService;
    private final BillingService billingService;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("meters", meterService.findAll());
        model.addAttribute("bills", billingService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/meters")
    public String meters(Model model) {
        model.addAttribute("meters", meterService.findAll());
        return "admin/meters";
    }

    @GetMapping("/add-meter")
    public String addMeterPage(Model model) {
        model.addAttribute("meter", new Meter());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("meterTypes", MeterType.values());
        return "admin/add-meter";
    }

    @PostMapping("/add-meter")
    public String addMeter(@ModelAttribute("meter") Meter meter, @RequestParam("userId") Long userId) {
        User user = userService.findById(userId).orElseThrow();
        meter.setUser(user);
        meterService.saveMeter(meter);
        return "redirect:/admin/meters";
    }

    @GetMapping("/add-reading/{meterId}")
    public String addReadingPage(@PathVariable Long meterId, Model model) {
        Meter meter = meterService.findById(meterId).orElseThrow();
        model.addAttribute("meter", meter);
        return "admin/add-reading";
    }

    @PostMapping("/add-reading")
    public String addReading(@RequestParam("meterId") Long meterId,
            @RequestParam("value") Double value,
            @RequestParam("readingDate") LocalDate readingDate) {
        meterService.addReading(meterId, value, readingDate);
        return "redirect:/admin/meters";
    }

    @GetMapping("/bills")
    public String bills(Model model) {
        model.addAttribute("bills", billingService.findAll());
        return "admin/bills";
    }

    @GetMapping("/add-customer")
    public String addCustomerPage(Model model) {
        model.addAttribute("user", new User());
        return "admin/add-customer";
    }

    @PostMapping("/add-customer")
    public String addCustomer(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "admin/add-customer";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(electricity.com.waterandelectricitybillingmanagementsystem.entity.Role.CUSTOMER);
        userService.save(user);
        return "redirect:/admin/users?success";
    }
}
