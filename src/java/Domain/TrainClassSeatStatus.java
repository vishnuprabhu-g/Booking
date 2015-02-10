package Domain;

public class TrainClassSeatStatus implements Cloneable {

    public long trainClassSeatStatusId;
    public long tClassStatusId;
    public int seatNo;
    public boolean availability;
    public long pnr;
    public long typeId;
    public int id;
    public int box;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
