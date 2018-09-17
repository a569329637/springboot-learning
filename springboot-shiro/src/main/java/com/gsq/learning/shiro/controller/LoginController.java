package com.gsq.learning.shiro.controller;

import com.gsq.learning.shiro.auth.session.MySession;
import com.gsq.learning.shiro.auth.session.MySessionDao;
import com.gsq.learning.shiro.auth.token.PasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2018/9/16
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MySessionDao mySessionDao;


    @GetMapping("/login/success")
    public String loginSuccess() {
        return "this is login success page";
    }

    @GetMapping("/login/unauthorized")
    public String loginUnauthorized() {
        return "this is login unauthorized page";
    }

    @GetMapping("/login/page")
    public String getLogin() {
        return "this is login page";
    }

    @GetMapping("/login")
    public Object postLogin(@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "password", required = false) String password) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        mySessionDao.updateStatus(session.getId().toString(), MySession.OnlineStatus.online);

        try {
            PasswordToken passwordToken = new PasswordToken(username, password);
            subject.login(passwordToken);
        } catch (UnknownAccountException e) {
            logger.error("账号错误, exception = {}", e);
        } catch (Exception e) {
            logger.error("exception = {}", e);
        }

        logger.info("{}", subject.hasRole("role1"));

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", session.getId());
        data.put("login", subject.isAuthenticated());
        Map<String, Object> res = new HashMap<>();
        res.put("code", "0000");
        res.put("msg", "成功");
        res.put("data", data);
        return res;
    }

    @GetMapping("/logout")
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        mySessionDao.updateStatus(session.getId().toString(), MySession.OnlineStatus.offline);
        subject.logout();
        return "logout";
    }
}
