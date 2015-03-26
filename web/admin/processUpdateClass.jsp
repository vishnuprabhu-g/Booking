<%@page import="Domain.TrainClass"%>
<%@page import="java.sql.SQLException"%>
<%@page import="Do.TrainClassDO"%>
<%@page import="Do.ClassDO"%>
<%@page import="java.util.List"%>
<%
    long trainID = (Long) session.getAttribute("trainID");
    List<Domain.Class> classes = null;
    ClassDO cdo = null;
    TrainClassDO tcdo = null;
    try {
        tcdo = new TrainClassDO();
        cdo = new ClassDO();
        classes = cdo.getAll();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    for (Domain.Class cls : classes) {
        String val = request.getParameter(new Long(cls.id).toString());
        int value = Integer.parseInt(val);
        if (value >= 0) {
            boolean isUpdate = tcdo.isUpdate(trainID, cls.id);
            TrainClass tc = new TrainClass();
            tc.classID = cls.id;
            tc.trainId = trainID;
            tc.total_compartment = value;
            if (isUpdate) {
                tcdo.update(tc);
            } else {
                tcdo.add(tc);
            }
        }
    }
    util.CommitUtil.commit();
%>
Updated Successfully.
