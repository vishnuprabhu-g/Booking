<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%
    response.setContentType("APPLICATION/OCTET-STREAM");
    try {
        Long pnr = (Long) session.getAttribute("pnr");
        URL url = new URL("http://vishnu-pt517:8080/Booking/user/ViewBookedTicket.jsp?pnr=" + pnr);
        URLConnection uc = url.openConnection();
        BufferedReader bf = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        String file = util.pdf.htmlToPdf(sb.toString());
        String filename = file;
        String filepath = "e:\\pdfs\\";

        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + filename);

        int i;
        while ((i = fileInputStream.read()) != -1) {
            out.write(i);
        }
        fileInputStream.close();
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
%>