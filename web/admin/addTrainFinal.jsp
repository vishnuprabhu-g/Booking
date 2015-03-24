<%@page import="java.sql.Time"%>
<%@page import="Domain.TrainStation"%>
<%@page import="Do.TrainStationDO"%>
<%@page import="Do.TrainDO"%>
<%@page import="Do.StationDO"%>
<%@page import="Domain.Train"%>
<%@page import="java.util.List"%>
<%
    try {
        String trainName = (String) session.getAttribute("trainName");
        String trainNumberStr = (String) session.getAttribute("trainNumber");
        Long trainNumber = Long.parseLong(trainNumberStr);
        String from = (String) session.getAttribute("from");
        String to = (String) session.getAttribute("to");
        String endKM = (String) session.getAttribute("endKm");

        List<String> stations = (List) session.getAttribute("stations");
        List<Integer> kms = (List) session.getAttribute("kms");
        StationDO stationDO = new StationDO();
        TrainDO trainDO = new TrainDO();

        Train train = new Train();
        train.trainId = trainNumber;
        train.name = trainName;
        train.fromStationId = stationDO.getByName(from).id;
        train.toStationId = stationDO.getByName(to).id;
        trainDO.add(train);

        TrainStationDO tsdo = new TrainStationDO();
        int i = 1;
        for (String s : stations) {
            TrainStation ts = new TrainStation();
            ts.trainId = trainNumber;
            ts.stationId = stationDO.getByName(s).id;
            ts.arrival = new Time(System.currentTimeMillis());
            ts.dept = ts.arrival;
            ts.order = i++;
            tsdo.add(ts);
        }
        TrainStation ts = new TrainStation();
        ts.trainId = trainNumber;
        ts.arrival = new Time(System.currentTimeMillis());
        ts.dept = ts.arrival;
        ts.order = 0;
        ts.stationId = stationDO.getByName(from).id;
        tsdo.add(ts);

        ts.order = -1;
        ts.stationId = stationDO.getByName(to).id;
        tsdo.add(ts);

        out.println("Added successfully");
        util.CommitUtil.commit();
    } catch (Exception e) {
        out.println(e.getMessage());
        e.printStackTrace();
        util.CommitUtil.rollBack();
    }

%>