package com.nashtech.rookies.utils;

import com.nashtech.rookies.security.userprincal.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static Long getIdFromUserPrinciple() {
        UserPrinciple userPrinciple = new UserPrinciple();

        try {
            userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userPrinciple.getId();
    }
}
