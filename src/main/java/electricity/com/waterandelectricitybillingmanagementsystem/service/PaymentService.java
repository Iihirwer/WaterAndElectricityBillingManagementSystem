package electricity.com.waterandelectricitybillingmanagementsystem.service;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Bill;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.BillStatus;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.Payment;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillingService billingService;

    public PaymentService(PaymentRepository paymentRepository, BillingService billingService) {
        this.paymentRepository = paymentRepository;
        this.billingService = billingService;
    }

    @Transactional
    public Payment processPayment(Bill bill, String paymentMethod, String accountNumber) {
        if (bill.getStatus() == BillStatus.PAID) {
            throw new IllegalStateException("Bill is already paid");
        }

        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setAmountPaid(bill.getAmount());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(paymentMethod);
        payment.setAccountNumber(accountNumber);

        Payment savedPayment = paymentRepository.save(payment);
        billingService.updateBillStatus(bill, BillStatus.PAID);

        return savedPayment;
    }
}
