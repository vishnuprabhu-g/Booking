/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author vishnu-pt517
 */
public class CommitUtil {

    public static Connection con = util.ConnectionUtil.getConnection();

    public static void commit() throws SQLException {
        if (con != null) {
            con.commit();
        } else {
            System.out.println("----Null con obj found---");
        }
    }

    public static void rollBack() throws SQLException {
        if (con != null) {
            con.rollback();
        }
    }

    public static void init() throws SQLException {
        if (con != null) {
            con.setAutoCommit(false);
            System.out.println(con.getAutoCommit());
        }
    }
}
