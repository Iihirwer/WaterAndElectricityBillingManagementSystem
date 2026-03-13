package electricity.com.waterandelectricitybillingmanagementsystem.repository;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Bill;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUser(User user);

    List<Bill> findByMeter(Meter meter);
}
