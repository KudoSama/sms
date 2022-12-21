package com.wmj.sms.utls;


import com.wmj.sms.dto.UserInfoDTO;
import com.wmj.sms.dao.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUtils {

    public static UserInfoDTO getUserInfo() {
        //从session获取当前登录用户
        //拿得到：登录过    拿不到：没有登录
       // Admin userInfo = SessionUtils.getCurrentUserInfo();
        User curUser = SessionUtils.getCurUser();
        UserInfoDTO userInfoDTO = new UserInfoDTO();


        userInfoDTO.setId(curUser.getId());
        userInfoDTO.setName(curUser.getName());
        userInfoDTO.setUserType(curUser.getUserType());

        return userInfoDTO;
    }
}
