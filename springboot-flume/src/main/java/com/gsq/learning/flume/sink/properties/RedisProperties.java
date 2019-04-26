package com.gsq.learning.flume.sink.properties;

/**
 * @author guishangquan
 * @date 2019/4/26
 */
public class RedisProperties {
    public static String hostName = "redisHost";
    public static String passwordName = "redisPassword";
    public static String portName = "redisPort";
    public static String databaseName = "redisDatabase";

    private String host;
    private String password;
    private Integer port;
    private Integer database;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "RedisProperties{" +
                "host='" + host + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", database=" + database +
                '}';
    }
}
