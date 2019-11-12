package com.stav.miaosha.service;

import com.stav.miaosha.dao.MiaoshaUserDao;
import com.stav.miaosha.domain.MiaoshaUser;
import com.stav.miaosha.result.CodeMsg;
import com.stav.miaosha.util.MD5Util;
import com.stav.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaUserService {

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById (long id) {
        return this.miaoshaUserDao.getUserById(id);
    }

    public CodeMsg loginCheck(LoginVo loginVo) throws Exception {
        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        // 判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        // 验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();

        String calcPass = MD5Util.formPassToDBPass(password, dbSalt);
        System.out.println(calcPass);
        if (!calcPass.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
