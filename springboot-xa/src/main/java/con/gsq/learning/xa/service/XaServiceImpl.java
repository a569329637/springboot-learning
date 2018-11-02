package con.gsq.learning.xa.service;

import com.atomikos.logging.Logger;
import com.atomikos.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author guishangquan
 * @date 2018/11/2
 */
@Service("xaService")
public class XaServiceImpl implements XaService {

    Logger logger = LoggerFactory.createLogger(XaServiceImpl.class);

    @Autowired
    @Qualifier("jdbcTemplate1")
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("jdbcTemplate2")
    private JdbcTemplate jdbcTemplate2;

    @Override
    @Transactional
    public void test(Integer a, Integer b) {
        String sql = "insert into person(id, name, age) values(?, ?, ?)";
        int ret1 = jdbcTemplate1.update(sql, a, "name1", 19);
        logger.logInfo("ret1 = " + ret1);
        int ret2 = jdbcTemplate2.update(sql, b, "name2", 18);
        logger.logInfo("ret2 = " + ret2);
    }
}
