package electricity.com.waterandelectricitybillingmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "meters")
@Getter
@Setter
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
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
}
