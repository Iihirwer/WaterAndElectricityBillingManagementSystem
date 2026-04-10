package electricity.com.waterandelectricitybillingmanagementsystem.service;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.*;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    private final BillRepository billRepository;
    private final SystemService systemService;

    public BillingService(BillRepository billRepository, SystemService systemService) {
        this.billRepository = billRepository;
        this.systemService = systemService;
    }

    @Transactional
    public Bill generateBill(Meter meter, Double unitsConsumed, LocalDate issueDate) {
        Bill bill = new Bill();
        bill.setMeter(meter);
        bill.setUser(meter.getUser());
        bill.setIssueDate(issueDate);
        bill.setDueDate(issueDate.plusDays(15));
        bill.setBillingPeriod(issueDate.format(DateTimeFormatter.ofPattern("MMM yyyy")));
        bill.setStatus(BillStatus.PENDING);

        // Fetch dynamic rates
        BigDecimal rate = meter.getType() == MeterType.ELECTRICITY ? systemService.getElectricityRate()
                : systemService.getWaterRate();
        BigDecimal amount = rate.multiply(BigDecimal.valueOf(unitsConsumed));
        bill.setAmount(amount);
        bill.setUnits(unitsConsumed);

        return billRepository.save(bill);
    }

    public List<Bill> findByUser(User user) {
        return billRepository.findByUser(user);
    }

    public List<Bill> findByMeter(Meter meter) {
        return billRepository.findByMeter(meter);
    }

    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    public Bill updateBillStatus(Bill bill, BillStatus status) {
        bill.setStatus(status);
        return billRepository.save(bill);
    }
}
