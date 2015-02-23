<%@page import="java.sql.SQLException"%>
<%@page import="Do.*" %>
<%@page import="Domain.*" %>
<%
    String coach = "S2";
    int count = 16;
    TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
    TrainClassRacStatusDO racdo = new TrainClassRacStatusDO();
    try {

        for (int i = 1; i <= count; i++) {
            if (i%8!=0 && (i%8) % 7 == 0) {
            } else if (i % 8 == 0) {
                TrainClassSeatStatus tcss = new TrainClassSeatStatus();
                tcss.availability = true;
                tcss.box = ((i - 1) / 8) + 1;
                tcss.typeId = 4;
                tcss.tClassStatusId = 1;
                tcss.compartment = coach;
                tcss.seatNo=i;
                tcssdo.add(tcss);
            } else {
                TrainClassSeatStatus tcss = new TrainClassSeatStatus();
                tcss.availability = true;
                tcss.box = ((i - 1) / 8) + 1;
                tcss.typeId = (i % 8) % 3;
                if (tcss.typeId == 0) {
                    tcss.typeId = 3;
                }
                tcss.tClassStatusId = 1;
                tcss.compartment = coach;
                tcss.seatNo=i;
                tcssdo.add(tcss);
            }
        }
    } catch (SQLException e) {
        out.println(e);
    }
%>
<%@include file="manualCommit.jsp" %>
