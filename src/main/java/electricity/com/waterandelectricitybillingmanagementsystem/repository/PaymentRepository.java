package electricity.com.waterandelectricitybillingmanagementsystem.repository;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
