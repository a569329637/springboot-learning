package con.gsq.learning.xa.controller;

import con.gsq.learning.xa.service.XaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guishangquan
 * @date 2018/11/2
 */
@RestController
public class XaController {

    @Autowired
    private XaService xaService;

    @GetMapping("/xa/test")
    public String xa(
            @RequestParam(value = "a") Integer a,
            @RequestParam(value = "b") Integer b) {

        try {
            xaService.test(a, b);
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }
}
