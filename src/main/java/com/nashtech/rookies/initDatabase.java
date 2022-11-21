package com.nashtech.rookies;

import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class initDatabase {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Bean
    CommandLineRunner loadDatabase(UsersRepository usersRepository) {
        return args -> {
            usersRepository.deleteAll();

            for(int i = 1; i <= 30; i++) {
                usersRepository.save(new Users(
                        "adhcm" + i,
                        passwordEncoder.encode("123456"),
                        true,
                        "admin",
                        "cute",
                        true,
                        new Date(),
                        "HCM",
                        new Date(),
                        "ADMIN",
                        "HCMADMIN" + i
                ));
            }

            for(int i = 1; i <= 10; i++) {
                usersRepository.save(new Users(
                        "userhcm" + i,
                        passwordEncoder.encode("123456"),
                        true,
                        "admin123",
                        "cute",
                        true,
                        new Date(),
                        "HCM",
                        new Date(),
                        "STAFF",
                        "HCMUSER" + i
                ));
            }

            for(int i = 1; i <= 10; i++) {
                usersRepository.save(new Users(
                        "userhn" + i,
                        passwordEncoder.encode("123456"),
                        true,
                        "admin",
                        "cute",
                        true,
                        new Date(),
                        "HN",
                        new Date(),
                        "STAFF",
                        "HNUSER" + i
                ));
            }

            for(int i = 1; i <= 10; i++) {
                usersRepository.save(new Users(
                        "adhn" + i,
                        passwordEncoder.encode("123456"),
                        true,
                        "admin",
                        "cute",
                        true,
                        new Date(),
                        "HN",
                        new Date(),
                        "ADMIN",
                        "HNADMIN" + i
                ));
            }
            
            
        };
    }
}
