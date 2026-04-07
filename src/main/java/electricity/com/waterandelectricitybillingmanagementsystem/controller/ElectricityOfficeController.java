package electricity.com.waterandelectricitybillingmanagementsystem.controller;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.MeterType;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.service.BillingService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.MeterService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.UserService;
import electricity.com.waterandelectricitybillingmanagementsystem.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/electricity-office")
public class ElectricityOfficeController {

    private final UserService userService;
    private final MeterService meterService;
    private final BillingService billingService;
    private final SystemService systemService;

    public ElectricityOfficeController(UserService userService, MeterService meterService, 
                                      BillingService billingService, SystemService systemService) {
        this.userService = userService;
        this.meterService = meterService;
        this.billingService = billingService;
        this.systemService = systemService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("meters", meterService.findAll().stream()
                .filter(m -> m.getType() == MeterType.ELECTRICITY).collect(Collectors.toList()));
        model.addAttribute("bills", billingService.findAll().stream()
                .filter(b -> b.getMeter().getType() == MeterType.ELECTRICITY).collect(Collectors.toList()));
        model.addAttribute("currentRate", systemService.getElectricityRate());
        return "electricity-office/dashboard";
    }

    @GetMapping("/meters")
    public String meters(Model model) {
        model.addAttribute("meters", meterService.findAll().stream()
                .filter(m -> m.getType() == MeterType.ELECTRICITY).collect(Collectors.toList()));
        return "electricity-office/meters";
    }

    @GetMapping("/add-meter")
    public String addMeterPage(Model model) {
        model.addAttribute("meter", new Meter());
        model.addAttribute("users", userService.findAll());
        return "electricity-office/add-meter";
    }

    @PostMapping("/add-meter")
    public String addMeter(@ModelAttribute("meter") Meter meter, @RequestParam("userId") Long userId) {
        User user = userService.findById(userId).orElseThrow();
        meter.setUser(user);
        meter.setType(MeterType.ELECTRICITY);
        meterService.saveMeter(meter);
        return "redirect:/electricity-office/meters";
    }

    @GetMapping("/add-reading/{meterId}")
    public String addReadingPage(@PathVariable Long meterId, Model model) {
        Meter meter = meterService.findById(meterId).orElseThrow();
        if (meter.getType() != MeterType.ELECTRICITY) return "redirect:/electricity-office/meters";
        model.addAttribute("meter", meter);
        return "electricity-office/add-reading";
    }

    @PostMapping("/add-reading")
    public String addReading(@RequestParam("meterId") Long meterId,
            @RequestParam("value") Double value,
            @RequestParam("readingDate") LocalDate readingDate) {
        meterService.addReading(meterId, value, readingDate);
        return "redirect:/electricity-office/meters";
    }

    @PostMapping("/generate-bill")
    public String generateBill(@RequestParam("meterId") Long meterId,
                               @RequestParam("units") Double units,
                               @RequestParam("date") LocalDate date) {
        Meter meter = meterService.findById(meterId).orElseThrow();
        billingService.generateBill(meter, units, date);
        return "redirect:/electricity-office/dashboard?success";
    }

    @GetMapping("/rates")
    public String rates(Model model) {
        model.addAttribute("rate", systemService.getElectricityRate());
        return "electricity-office/rates";
    }

    @PostMapping("/update-rate")
    public String updateRate(@RequestParam("rate") BigDecimal rate) {
        systemService.setElectricityRate(rate);
        return "redirect:/electricity-office/rates?updated";
    }
}
