package electricity.com.waterandelectricitybillingmanagementsystem.service;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.*;
import electricity.com.waterandelectricitybillingmanagementsystem.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private SystemService systemService;

    @InjectMocks
    private BillingService billingService;

    private User testUser;
    private Meter testMeter;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testMeter = new Meter();
        testMeter.setId(1L);
        testMeter.setUser(testUser);
        testMeter.setType(MeterType.ELECTRICITY);
    }

    @Test
    void testGenerateBillForElectricity() {
        // Arrange
        Double units = 100.0;
        BigDecimal rate = new BigDecimal("50.0");
        LocalDate issueDate = LocalDate.now();

        when(systemService.getElectricityRate()).thenReturn(rate);
        when(billRepository.save(any(Bill.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Bill generatedBill = billingService.generateBill(testMeter, units, issueDate);

        // Assert
        assertNotNull(generatedBill);
        assertTrue(new BigDecimal("5000.0").compareTo(generatedBill.getAmount()) == 0);
        assertEquals(units, generatedBill.getUnits());
        assertEquals(BillStatus.PENDING, generatedBill.getStatus());
        verify(systemService).getElectricityRate();
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    void testGenerateBillForWater() {
        // Arrange
        testMeter.setType(MeterType.WATER);
        Double units = 50.0;
        BigDecimal rate = new BigDecimal("30.0");
        LocalDate issueDate = LocalDate.now();

        when(systemService.getWaterRate()).thenReturn(rate);
        when(billRepository.save(any(Bill.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Bill generatedBill = billingService.generateBill(testMeter, units, issueDate);

        // Assert
        assertNotNull(generatedBill);
        assertTrue(new BigDecimal("1500.0").compareTo(generatedBill.getAmount()) == 0);
        verify(systemService).getWaterRate();
    }
}
