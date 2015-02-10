<%
    try {
        int err = Integer.parseInt(request.getParameter("errno"));
        if (err == 1) {
            out.println("Tickets cannot be booked for childern only..!");
        } else if (err == 4) {
            out.println("Invalid input..!Name not in the required format.");
        } else if (err == 3) {
            out.println("No passengers..!");
        } else if (err == 5) {
            out.println("Invalid age..!");
        }
    } catch (Exception e) {
        out.println("No errors to display now..!");
    }
%>