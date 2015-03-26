<%@page import="Do.ClassDO"%>
<%
    String name = request.getParameter("name");
    ClassDO cdo = new ClassDO();
    Domain.Class cls = new Domain.Class();
    cls.name = name;
    cdo.add(cls);
%>
Added Successfully.