package com.team14.sms.utls;


import com.team14.sms.vo.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }


    // 登录保存用户（login方法会使用到）
    public static void saveCurUser(User loginUser) {
        session().setAttribute("curUser", loginUser);
    }

    public static User getCurUser() {
       return  (User)session().getAttribute("curUser");
    }
}
