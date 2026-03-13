package electricity.com.waterandelectricitybillingmanagementsystem.repository;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterRepository extends JpaRepository<Meter, Long> {
    List<Meter> findByUser(User user);

    boolean existsByMeterNumber(String meterNumber);
}
