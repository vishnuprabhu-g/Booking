package Domain;

public class Passenger {

    public long pnr;
    public String name;
    public int age;
    public int gender;
    public int seat_no;
    public int statusId;
    public int sno;
    public int initialStatusId;
    public int initialSeatNo;
    public int fare;
    public String coach;
    public int no;

    @Override
    public String toString() {
        return this.name + this.age + this.seat_no;
    }
}
