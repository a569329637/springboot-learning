package con.gsq.learning.xa.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author guishangquan
 * @date 2018/11/2
 */
@Configuration
public class DatabaseConfig {

    @Bean("xaDataSource1")
    public DruidXADataSource getXaDataSource1() {
        DruidXADataSource xaDataSource = new DruidXADataSource();
        xaDataSource.setUrl("jdbc:mysql://localhost:3306/jtm1?useUnicode=true&characterEncoding=UTF8&useSSL=false");
        xaDataSource.setUsername("root");
        xaDataSource.setPassword("root");
        return xaDataSource;
    }

    @Bean("xaDataSource2")
    public DruidXADataSource getXaDataSource2() {
        DruidXADataSource xaDataSource = new DruidXADataSource();
        xaDataSource.setUrl("jdbc:mysql://localhost:3306/jtm2?useUnicode=true&characterEncoding=UTF8&useSSL=false");
        xaDataSource.setUsername("root");
        xaDataSource.setPassword("root");
        return xaDataSource;
    }

    @Bean(value = "dataSource1")
    public DataSource getDataSource1(@Qualifier("xaDataSource1") DruidXADataSource dataSource) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        return atomikosDataSourceBean;
    }

    @Bean(value = "dataSource2")
    public DataSource getDataSource2(@Qualifier("xaDataSource2") DruidXADataSource dataSource) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        return atomikosDataSourceBean;
    }

    @Bean("jdbcTemplate1")
    public JdbcTemplate getJdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean("jdbcTemplate2")
    public JdbcTemplate getJdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
