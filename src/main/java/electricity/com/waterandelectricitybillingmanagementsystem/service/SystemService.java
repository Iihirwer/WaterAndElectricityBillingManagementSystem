package electricity.com.waterandelectricitybillingmanagementsystem.service;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.SystemSetting;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SystemService {

    private final SystemSettingRepository systemSettingRepository;

    public SystemService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
        // Initialize default rates if they don't exist
        initSetting("WATER_RATE", "0.05");
        initSetting("ELECTRICITY_RATE", "0.15");
    }

    private void initSetting(String key, String value) {
        if (!systemSettingRepository.existsById(key)) {
            systemSettingRepository.save(new SystemSetting(key, value));
        }
    }

    public BigDecimal getWaterRate() {
        return systemSettingRepository.findById("WATER_RATE")
                .map(SystemSetting::getValueAsBigDecimal)
                .orElse(new BigDecimal("0.05"));
    }

    public BigDecimal getElectricityRate() {
        return systemSettingRepository.findById("ELECTRICITY_RATE")
                .map(SystemSetting::getValueAsBigDecimal)
                .orElse(new BigDecimal("0.15"));
    }

    public void setWaterRate(BigDecimal rate) {
        systemSettingRepository.save(new SystemSetting("WATER_RATE", rate.toString()));
    }

    public void setElectricityRate(BigDecimal rate) {
        systemSettingRepository.save(new SystemSetting("ELECTRICITY_RATE", rate.toString()));
    }
}
