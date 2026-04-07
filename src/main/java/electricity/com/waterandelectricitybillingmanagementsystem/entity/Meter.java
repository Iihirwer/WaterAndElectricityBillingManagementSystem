package electricity.com.waterandelectricitybillingmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "meters")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    @Size(min = 12, max = 12, message = "Meter number must be exactly 12 characters")
    private String meterNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeterType type;

    @Column(nullable = false)
    private String assignedAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<MeterReading> readings;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<Bill> bills;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMeterNumber() { return meterNumber; }
    public void setMeterNumber(String meterNumber) { this.meterNumber = meterNumber; }

    public MeterType getType() { return type; }
    public void setType(MeterType type) { this.type = type; }

    public String getAssignedAddress() { return assignedAddress; }
    public void setAssignedAddress(String assignedAddress) { this.assignedAddress = assignedAddress; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<MeterReading> getReadings() { return readings; }
    public void setReadings(List<MeterReading> readings) { this.readings = readings; }

    public List<Bill> getBills() { return bills; }
    public void setBills(List<Bill> bills) { this.bills = bills; }
}
