package electricity.com.waterandelectricitybillingmanagementsystem.controller;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Bill;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.service.BillingService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.MeterService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.PaymentService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final UserService userService;
    private final MeterService meterService;
    private final BillingService billingService;
    private final PaymentService paymentService;

    public CustomerController(UserService userService, MeterService meterService, BillingService billingService, PaymentService paymentService) {
        this.userService = userService;
        this.meterService = meterService;
        this.billingService = billingService;
        this.paymentService = paymentService;
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName()).orElseThrow();
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("meters", meterService.findByUser(user));
        model.addAttribute("bills", billingService.findByUser(user));
        return "customer/dashboard";
    }

    @GetMapping("/meters")
    public String myMeters(Model model) {
        User user = getAuthenticatedUser();
        model.addAttribute("meters", meterService.findByUser(user));
        return "customer/meters";
    }

    @GetMapping("/bills")
    public String myBills(Model model) {
        User user = getAuthenticatedUser();
        model.addAttribute("bills", billingService.findByUser(user));
        return "customer/bills";
    }

    @PostMapping("/pay-bill")
    public String payBill(@RequestParam("billId") Long billId,
            @RequestParam("paymentMethod") String paymentMethod) {
        Bill bill = billingService.findById(billId).orElseThrow();
        paymentService.processPayment(bill, paymentMethod);
        return "redirect:/customer/bills";
    }
}
