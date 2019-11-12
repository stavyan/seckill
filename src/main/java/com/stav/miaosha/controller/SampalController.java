package com.stav.miaosha.controller;

import com.stav.miaosha.domain.User;
import com.stav.miaosha.redis.UserKey;
import com.stav.miaosha.result.Result;
import com.stav.miaosha.redis.RedisService;
import com.stav.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampalController {


    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @GetMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet () {
        User user = userService.getUserById(1);
        return Result.success(user);
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result redisSet () {
        User user = new User();
        user.setId(1);
        user.setName("stav");
        boolean set = redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(set);
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result redisGet () {
        String str = redisService.get(UserKey.getById, "" + 1, String.class);
        return Result.success(str);
    }
}
