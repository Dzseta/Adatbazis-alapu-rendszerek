package hu.adatb.controller;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;

public class DBController {
    public static Connection connect(){
        Connection conn = null;

        try {
            OracleDataSource ods = new OracleDataSource();
            Class.forName ("oracle.jdbc.OracleDriver");
            ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
            conn = ods.getConnection("test","test");
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return conn;
    }
}
