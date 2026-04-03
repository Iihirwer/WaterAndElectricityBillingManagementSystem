package electricity.com.waterandelectricitybillingmanagementsystem.service;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.MeterReading;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.MeterReadingRepository;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.MeterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MeterService {

    private final MeterRepository meterRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final BillingService billingService;

    public MeterService(MeterRepository meterRepository, MeterReadingRepository meterReadingRepository, BillingService billingService) {
        this.meterRepository = meterRepository;
        this.meterReadingRepository = meterReadingRepository;
        this.billingService = billingService;
    }

    public Meter saveMeter(Meter meter) {
        if (meterRepository.existsByMeterNumber(meter.getMeterNumber())) {
            throw new IllegalArgumentException("Meter number already exists");
        }
        return meterRepository.save(meter);
    }

    public List<Meter> findByUser(User user) {
        return meterRepository.findByUser(user);
    }

    public Optional<Meter> findById(Long id) {
        return meterRepository.findById(id);
    }

    public List<Meter> findAll() {
        return meterRepository.findAll();
    }

    @Transactional
    public MeterReading addReading(Long meterId, Double value, LocalDate readingDate) {
        Meter meter = meterRepository.findById(meterId)
                .orElseThrow(() -> new IllegalArgumentException("Meter not found"));

        // Get the most recent reading to calculate consumption
        Optional<MeterReading> lastReadingOpt = meterReadingRepository.findFirstByMeterOrderByReadingDateDesc(meter);
        Double unitsConsumed = value; // Default if first reading
        if (lastReadingOpt.isPresent()) {
            unitsConsumed = value - lastReadingOpt.get().getValue();
            if (unitsConsumed < 0) {
                throw new IllegalArgumentException("New reading value cannot be less than previous reading");
            }
        }

        MeterReading reading = new MeterReading();
        reading.setMeter(meter);
        reading.setValue(value);
        reading.setReadingDate(readingDate != null ? readingDate : LocalDate.now());

        MeterReading savedReading = meterReadingRepository.save(reading);

        // Automatically generate bill
        billingService.generateBill(meter, unitsConsumed, reading.getReadingDate());

        return savedReading;
    }

    public List<MeterReading> getReadingsForMeter(Meter meter) {
        return meterReadingRepository.findByMeterOrderByReadingDateDesc(meter);
    }
}
