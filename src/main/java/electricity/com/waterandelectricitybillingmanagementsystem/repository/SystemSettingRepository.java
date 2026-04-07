package electricity.com.waterandelectricitybillingmanagementsystem.repository;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, String> {
}
