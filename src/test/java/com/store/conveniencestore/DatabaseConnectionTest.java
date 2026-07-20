package com.store.conveniencestore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConnectionDetails connectionDetails;

    @Test
    public void testConnection() throws SQLException{
        try (Connection connection = dataSource.getConnection()){
            System.out.println("数据库地址: " +
                    connection.getMetaData().getURL());
            System.out.println("数据库名称: "+
                    connection.getCatalog());
            System.out.println("连接是否有效: "+
                    connection.isValid(2));
        }


    }
}
