package com.gsq.learning.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2018/9/21
 */
@Controller
public class CookiesController {

    /**
     * 框架自带的 FindByIndexNameSessionRepository
     */
    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    /**
     * 通过用户去找 session
     * @param username
     * @return
     */
    @RequestMapping("/session/{username}")
    @ResponseBody
    public Map findByUsername(@PathVariable String username) {
        System.out.println("username = " + username);
        Map<String, ? extends Session> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        return usersSessions;
    }

    /**
     * hash类型，将内存中的session信息序列化到了redis中
     * spring:session:sessions:91b50e9a-eea2-41db-9391-6396f366b73e
     * string类型
     * spring:session:sessions:expires:000281a5-228e-4652-93d8-3edc930594af
     * set类型
     * spring:session:expirations:1537529040000
     *
     */
    @RequestMapping("/cookies")
    @ResponseBody
    public Object cookie(@RequestParam("browser") String browser, HttpServletRequest request,
                         HttpSession session) {
        //取出session中的browser
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在session，设置browser=" + browser);
            session.setAttribute("browser", browser);
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return request.getCookies();
    }
}
