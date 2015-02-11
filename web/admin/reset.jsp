<%@page import="java.sql.SQLException"%>
<%@page import="Domain.*"%>
<%@page import="Do.*"%>
<%
    QueryDO qDo = new QueryDO();

    try {
        qDo.executeQry("update train_class_seat_status set availability=1");
        qDo.executeQry("update train_class_rac_status set availability=1,train_class_status_id=1 ");
        qDo.executeQry("update train_class_status set waiting=1,initial_waiting=1,available=63,rac=1,chart=0");
        qDo.executeQry("delete from reservation");
        qDo.executeQry("delete from seat_passenger");
        qDo.executeQry("delete from passengers_tickets");
        qDo.executeQry("delete from passengers");
        qDo.executeQry("delete from under_passenger");
        util.CommitUtil.commit();
    } catch (SQLException ex) {
        System.out.println(ex);
    }

%>
<h4>System reset is successful..!</h4>