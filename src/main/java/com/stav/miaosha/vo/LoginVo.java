package com.stav.miaosha.vo;

import com.stav.miaosha.validate.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {

    @NotNull
    @Length(min=32)
    private String password;

    @NotNull
    @IsMobile
    private String mobile;
}
