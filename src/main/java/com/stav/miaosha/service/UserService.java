package com.stav.miaosha.service;

import com.stav.miaosha.dao.UserDao;
import com.stav.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserById (int id) {
        return this.userDao.getUserById(id);

    }
}
