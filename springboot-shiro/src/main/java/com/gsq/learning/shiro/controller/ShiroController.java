package com.gsq.learning.shiro.controller;

import com.gsq.learning.shiro.auth.session.MySessionDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guishangquan
 * @date 2018/9/16
 */
@RestController
public class ShiroController {

    @GetMapping("/shiro/principals")
    public Object principals() {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        return principals;
    }

    @GetMapping("/shiro/session")
    public Object shiroSession() {
        return MySessionDao.memoryMap;
    }

    @GetMapping("/session")
    public Object session() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.getSession();
    }

}
