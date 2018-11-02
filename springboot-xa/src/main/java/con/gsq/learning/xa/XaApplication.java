package con.gsq.learning.xa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author guishangquan
 * @date 2018/11/2
 */
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
public class XaApplication {
    public static void main(String[] args) {
        SpringApplication.run(XaApplication.class, args);
    }
}
