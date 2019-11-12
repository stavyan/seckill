package com.stav.miaosha.controller;

import com.stav.miaosha.domain.MiaoshaUser;
import com.stav.miaosha.domain.User;
import com.stav.miaosha.result.CodeMsg;
import com.stav.miaosha.result.Result;
import com.stav.miaosha.service.MiaoshaUserService;
import com.stav.miaosha.util.ValidatorUtil;
import com.stav.miaosha.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo) throws Exception {
        log.info(loginVo.toString());
//        String passInput = loginVo.getPassword();
//        String mobileInput = loginVo.getMobile();
//        if (StringUtils.isEmpty(passInput)) {
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if (StringUtils.isEmpty(mobileInput)) {
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//
//        if (!ValidatorUtil.isMobile(mobileInput)) {
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        // 登录
        CodeMsg cm = this.miaoshaUserService.loginCheck(loginVo);
        if (cm.getCode() == 0) {
            System.out.println("success");
            return Result.success(true);
        } else {
            return Result.error(cm);
        }
    }
}
