package com.nashtech.rookies;

import com.nashtech.rookies.entity.Asset;
import com.nashtech.rookies.entity.Assignment;
import com.nashtech.rookies.entity.Category;
import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.repository.AssetRepository;
import com.nashtech.rookies.repository.AssignmentRepository;
import com.nashtech.rookies.repository.CategoryRepository;
import com.nashtech.rookies.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Random;

@Configuration
public class initDatabase {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Bean
    CommandLineRunner loadDatabase(UsersRepository usersRepository,
                                   AssetRepository assetRepository,
                                   CategoryRepository categoryRepository,
                                   AssignmentRepository assignmentRepository) {
        return args -> {
            usersRepository.deleteAll();
            assetRepository.deleteAll();
            categoryRepository.deleteAll();
            assignmentRepository.deleteAll();

            //            region Users
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
//          endregion

            //            region Category
            for (int i = 1; i <= 5; i++){
                categoryRepository.save(new Category(
                        "category" + i,
                        "CATEGORY" + i
                ));
            }
//            endregion

            //            region    Asset
            for (int i = 1; i <= 30; i++){
                assetRepository.save(new Asset(
                        "asset" + i,
                        "ASSETS" + i,
                        "Day la asset, ahihi :v",
                        "HCM",
                        randomStateAsset(),
                        categoryRepository.findById((long) new Random().nextInt(5) + 1).get()
                ));
            }

            assetRepository.save(new Asset(
                    "asset31",
                    "ASSETS31",
                    "Day la asset, ahihi :v",
                    "HCM",
                    "Assigned",
                    categoryRepository.findById((long) new Random().nextInt(5) + 1).get()
            ));

            assetRepository.save(new Asset(
                    "asset31",
                    "ASSETS31",
                    "Day la asset, ahihi :v",
                    "HCM",
                    "Not available",
                    categoryRepository.findById((long) new Random().nextInt(5) + 1).get()
            ));

//            endregion

            //            region    Assignment
            for(int i = 0; i <= 30; i++){
                assignmentRepository.save(new Assignment(
                        "Note something blabla ...",
                        randomStateAssignment(),
                        new Date(),
                        new Date(),
                        new Date(),
                        usersRepository.findById((long) new Random().nextInt(30) + 1 ).get(),
                        usersRepository.findById((long) new Random().nextInt(30) + 1 ).get(),
                        assetRepository.findById((long) new Random().nextInt(30) + 1 ).get()
                ));
            }
            //            endregion

        };
    }

    private String randomStateAsset() {
    	String[] states = {"Available", "Not available", "Assigned", "Waiting for recycling", "Recycled"};
    	return states[new Random().nextInt(states.length)];
    }

    private String randomStateAssignment() {
    	String[] states = {"Accepted", "Waiting for acceptance"};
    	return states[new Random().nextInt(states.length)];
    }

}
