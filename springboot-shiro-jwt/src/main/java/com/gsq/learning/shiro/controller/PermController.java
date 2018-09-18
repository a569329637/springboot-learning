package com.gsq.learning.shiro.controller;

import com.gsq.learning.shiro.vo.Message;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
@RestController
public class PermController {

    @GetMapping("/perm/user/create")
    @RequiresPermissions("user:create")
    public Object permUserCreate() {
        return new Message().ok(200, "成功");
    }

    @GetMapping("/perm/user/update")
    @RequiresPermissions("user:update")
    public Object permUserUpdate() {
        return new Message().ok(200, "成功");
    }

    @GetMapping("/perm/user/view")
    @RequiresPermissions("user:view")
    public Object permUserView() {
        return new Message().ok(200, "成功");
    }

    @GetMapping("/role/role1")
    @RequiresRoles({"role1"})
    public Object roleRole1() {
        return new Message().ok(200, "成功");
    }

    @GetMapping("/role/role2")
    @RequiresRoles({"role2"})
    public Object roleRole2() {
        return new Message().ok(200, "成功");
    }

    @GetMapping("/role/role3")
    @RequiresRoles({"role3"})
    public Object roleRole3() {
        return new Message().ok(200, "成功");
    }
}
