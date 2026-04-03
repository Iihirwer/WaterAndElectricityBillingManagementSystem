package electricity.com.waterandelectricitybillingmanagementsystem;

import java.util.Optional;

import electricity.com.waterandelectricitybillingmanagementsystem.entity.Role;
import electricity.com.waterandelectricitybillingmanagementsystem.entity.User;
import electricity.com.waterandelectricitybillingmanagementsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WaterAndElectricityBillingManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterAndElectricityBillingManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<User> adminOpt = userService.findByUsername("Admin");
            if (adminOpt.isEmpty()) {
                System.out.println("Admin user not found, creating...");
                User admin = new User();
                admin.setUsername("Admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                admin.setEmailVerified(true);
                admin.setFullName("System Administrator");
                admin.setEmail("admin@example.com");
                admin.setPhone("000-000-0000");
                admin.setAddress("System Office");
                userService.save(admin);
                System.out.println("Admin user created.");
            } else {
                User admin = adminOpt.get();
                admin.setEnabled(true);
                admin.setEmailVerified(true);
                userService.save(admin);
                System.out.println("Admin user found, updating status...");
            }

            // Also create a test customer if not exists
            if (userService.findByUsername("customer").isEmpty()) {
                User customer = new User();
                customer.setUsername("customer");
                customer.setPassword(passwordEncoder.encode("customer"));
                customer.setFullName("Test Customer");
                customer.setEmail("customer@example.com");
                customer.setRole(Role.CUSTOMER);
                customer.setEnabled(true);
                customer.setEmailVerified(true);
                userService.save(customer);
                System.out.println("Test customer created.");
            }
        };
    }
}
