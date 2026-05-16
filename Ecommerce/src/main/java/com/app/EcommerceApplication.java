package com.app;

import java.util.List;

import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class EcommerceApplication {
	
@Bean
public ModelMapper modelMapper(){
    return new ModelMapper();
}

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                User admin = new User();
                admin.setName("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Admin created");
            }
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
