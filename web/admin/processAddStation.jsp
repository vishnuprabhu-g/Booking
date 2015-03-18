<%@page import="Domain.Station"%>
<%@page import="Do.StationDO"%>
<%
    String name = request.getParameter("name").trim();
    StationDO sdo = new StationDO();
    Object o = sdo.getByName(name);
    if (o != null) {
        out.println("Station Alresy exist.!");
    } else {
        try {
            Station s = new Station();
            s.name = name;
            sdo.add(s);
            out.println("Added successfully");
            util.CommitUtil.commit();
        } catch (Exception e) {
            out.println("Exception:" + e.getMessage());
        }
    }
%>