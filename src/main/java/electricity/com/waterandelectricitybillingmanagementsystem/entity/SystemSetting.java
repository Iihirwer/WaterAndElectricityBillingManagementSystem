package electricity.com.waterandelectricitybillingmanagementsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "system_settings")
public class SystemSetting {

    @Id
    private String settingKey;

    @Column(nullable = false)
    private String settingValue;

    public SystemSetting() {}

    public SystemSetting(String settingKey, String settingValue) {
        this.settingKey = settingKey;
        this.settingValue = settingValue;
    }

    public String getSettingKey() { return settingKey; }
    public void setSettingKey(String settingKey) { this.settingKey = settingKey; }

    public String getSettingValue() { return settingValue; }
    public void setSettingValue(String settingValue) { this.settingValue = settingValue; }

    public BigDecimal getValueAsBigDecimal() {
        return new BigDecimal(settingValue);
    }
}
