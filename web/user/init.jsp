<%@page import="java.sql.SQLException"%>
<%@page import="Domain.*"%>
<%@page import="Do.*"%>
<%
    TrainClassRacStatusDO racDo = new TrainClassRacStatusDO();
        for (int i = 0; i < 18; i++) {
            try {
                TrainClassRacStatus obj = new TrainClassRacStatus();
                obj.availability=true;
                obj.racNo=i+1;
                obj.seatNo=(i/2)*8+7;
                obj.trainClassStatusId=1;
                racDo.add(obj);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

%>
Finished..