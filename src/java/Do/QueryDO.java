/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author vishnu-pt517
 */
public class QueryDO {

    public void executeQry(String qry) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(qry);
        ps.execute();
    }
}
