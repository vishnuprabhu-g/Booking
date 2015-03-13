<%@page import="java.util.List"%>
<%
    String trainName = (String) session.getAttribute("trainName");
    String trainNumber = (String) session.getAttribute("trainNumber");
    String from = (String) session.getAttribute("from");
    String to = (String) session.getAttribute("to");
    String endKM = (String) session.getAttribute("endKm");

    List<String> stations = (List) session.getAttribute("stations");
    List<Integer> kms = (List) session.getAttribute("kms");

    out.println("Added successfully");

%>