package com.nashtech.rookies.services.impl;

import com.nashtech.rookies.entity.Users;
import com.nashtech.rookies.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements com.nashtech.rookies.services.interfaces.UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

}
