package com.nashtech.rookies;

import com.nashtech.rookies.entity.*;
import com.nashtech.rookies.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Configuration
public class initDatabase {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Bean
    CommandLineRunner loadDatabase(UsersRepository usersRepository,
                                   AssetRepository assetRepository,
                                   CategoryRepository categoryRepository,
                                   AssignmentRepository assignmentRepository,
                                   ReturnRequestRepository returnRequestRepository) {
        return args -> {
            usersRepository.deleteAll();
            assetRepository.deleteAll();
            categoryRepository.deleteAll();
            assignmentRepository.deleteAll();
            returnRequestRepository.deleteAll();

            //            region Users
            for(int i = 1; i <= 30; i++) {
                usersRepository.save(new Users(
                        "user" + i,
                        passwordEncoder.encode("User123456!@"),
                        true,
                        "dev",
                        "cute",
                        randomGender(),
                        randomDob(),
                        renderLocationUser(i),
                        new Date(),
                        "ADMIN",
                        renderCodeUser(i)
                ));
            }

            for(int i = 31; i <= 60; i++) {
                usersRepository.save(new Users(
                        "user" + i,
                        passwordEncoder.encode("User123456!@"),
                        true,
                        "dev",
                        "cute",
                        randomGender(),
                        randomDob(),
                        renderLocationUser(i),
                        new Date(),
                        "STAFF",
                        renderCodeUser(i)
                ));
            }

//          endregion

            //            region Category
            for (int i = 1; i <= 5; i++){
                categoryRepository.save(new Category(
                        "category" + i,
                        "CATEGORY" + i + "_"
                ));
            }
//            endregion

            //            region    Asset
            for (int i = 1; i <= 30; i++){
            	Asset asset = new Asset(
                        "asset" + i,
                        "ASSETS" + i,
                        "Day la asset, ahihi :v",
                        "HCM",
                        randomStateAsset(),
                        categoryRepository.findById((long) new Random().nextInt(5) + 1).get(),
                        usersRepository.findById((long) new Random().nextInt(30) + 1).get()
                );
            	asset.setCode(asset.getCategory().getCode() + i);
                assetRepository.save(asset);
            }

            assetRepository.save(new Asset(
                    "asset32",
                    "CATEGORY2_99",
                    "Day la asset, ahihi :v",
                    "HCM",
                    "Assigned",
                    categoryRepository.findById((long) new Random().nextInt(5) + 1).get(),
                    usersRepository.findById(2L).get()
            ));

            assetRepository.save(new Asset(
                    "asset31",
                    "CATEGORY1_99",
                    "Day la asset, ahihi :v",
                    "HCM",
                    "Not available",
                    categoryRepository.findById((long) new Random().nextInt(5) + 1).get(),
                    usersRepository.findById(1L).get()
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
                        assetRepository.findById((long) new Random().nextInt(30) + 1 ).get(),
                        false
                ));
            }
            //            endregion

            for(int i = 0; i <= 30; i++){
                returnRequestRepository.save(new ReturnRequest(
                        randomDate(),
                        randomStateRequestReturn(),
                        assignmentRepository.findById((long) new Random().nextInt(30) + 1 ).get()
                ));
            }

        };
    }

    private String randomStateAsset() {
    	String[] states = {"Available", "Not available", "Assigned", "Waiting for recycling", "Recycled"};
    	return states[new Random().nextInt(states.length)];
    }

    private String randomStateAssignment() {
    	String[] states = {"Accepted", "Waiting for acceptance", "Finished"};
    	return states[new Random().nextInt(states.length)];
    }

    private String renderCodeUser(int i){
        if(i < 10){
            return "SD000" + i;
        }
        else if (i >= 10 && i < 100){
            return "SD00" + i;
        }
        else {
            return "SD0" + i;
        }
    }

    private String renderLocationUser(int i){
        if(i % 2 == 0)
            return "HCM";
        else
            return "HN";
    }

    private boolean randomGender(){
        boolean[] states = {true, false};
        return states[new Random().nextInt(states.length)];
    }

    private Date randomDate() throws ParseException {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2020, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2022, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

        return java.sql.Date.valueOf(randomBirthDate);
    }

    private Date randomDob() throws ParseException {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

        return java.sql.Date.valueOf(randomBirthDate);
    }

    private String randomStateRequestReturn(){
    	String[] states = {"Accepted", "Waiting for acceptance", "Declined"};
    	return states[new Random().nextInt(states.length)];
    }

}
