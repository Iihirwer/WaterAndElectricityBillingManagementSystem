package electricity.com.waterandelectricitybillingmanagementsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "meter_readings")
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate readingDate;

    @Column(name = "\"value\"", nullable = false)
    private Double value; // units reading

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getReadingDate() { return readingDate; }
    public void setReadingDate(LocalDate readingDate) { this.readingDate = readingDate; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public Meter getMeter() { return meter; }
    public void setMeter(Meter meter) { this.meter = meter; }
}
