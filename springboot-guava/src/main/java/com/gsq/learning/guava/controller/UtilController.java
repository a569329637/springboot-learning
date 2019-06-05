package com.gsq.learning.guava.controller;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guishangquan
 * @date 2019-06-05
 */
@RestController
@RequestMapping("util")
public class UtilController {

    @GetMapping("precondition")
    public Object precondition() {

        boolean flag = true;
        Preconditions.checkArgument(flag);
        Preconditions.checkState(flag);

        int index = 0;
        int size = 5;
        Preconditions.checkElementIndex(index, size);

        Object obj = new Object();
        Preconditions.checkNotNull(obj);
        return "Success";
    }

    @GetMapping("object")
    public Object object() {

        boolean b1 = Objects.equal("a", "a");
        System.out.println("b1 = " + b1);
        boolean b2 = Objects.equal(null, "a");
        System.out.println("b2 = " + b2);
        boolean b3 = Objects.equal(null, null);
        System.out.println("b3 = " + b3);

        Object obj1 = new Object();
        Object obj2 = new Object();
        int hashCode = Objects.hashCode(obj1, obj2);
        System.out.println("hashCode = " + hashCode);
        return "Success";
    }
}
