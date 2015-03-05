<%@page import="java.sql.SQLException"%>
<%@page import="Domain.*"%>
<%@page import="Do.*"%>
<%
    QueryDO qDo = new QueryDO();

    try {
        qDo.executeQry("update train_class_seat_status set availability=1");
        util.CommitUtil.commit();
        qDo.executeQry("update train_class_rac_status set availability=1");
        util.CommitUtil.commit();
        qDo.executeQry("update train_class_status set waiting=1,initial_waiting=1,available=28,rac=1,chart=0 where train_class_status_id=1");
        util.CommitUtil.commit();
        qDo.executeQry("update train_class_status set waiting=1,initial_waiting=1,available=14,rac=1,chart=0 where train_class_status_id=2");
        util.CommitUtil.commit();
        qDo.executeQry("delete from reservation");
        qDo.executeQry("delete from seat_passenger");
        util.CommitUtil.commit();
        qDo.executeQry("delete from passengers_tickets");
        util.CommitUtil.commit();
        qDo.executeQry("delete from passengers");
        util.CommitUtil.commit();
        qDo.executeQry("delete from under_passenger");
        util.CommitUtil.commit();
    } catch (SQLException ex) {
        out.println("Reset failure..");
        System.out.println(ex);
    }

%>
<h4>System reset is successful..!</h4>