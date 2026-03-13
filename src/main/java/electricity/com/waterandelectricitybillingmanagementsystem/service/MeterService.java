package electricity.com.waterandelectricitybillingmanagementsystem.service;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Meter;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.MeterReading;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.MeterReadingRepository;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeterService {

    private final MeterRepository meterRepository;
    private final MeterReadingRepository meterReadingRepository;

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

        MeterReading reading = new MeterReading();
        reading.setMeter(meter);
        reading.setValue(value);
        reading.setReadingDate(readingDate != null ? readingDate : LocalDate.now());

        return meterReadingRepository.save(reading);
    }

    public List<MeterReading> getReadingsForMeter(Meter meter) {
        return meterReadingRepository.findByMeterOrderByReadingDateDesc(meter);
    }
}
