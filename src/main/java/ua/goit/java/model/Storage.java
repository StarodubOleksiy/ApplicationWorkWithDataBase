package ua.goit.java.model;

/**
 * Created by Администратор on 28.05.16.
 */
public class Storage {
    private String ingradients;
    private int numerosity;

    public Storage(String ingradients, int numerosity) {
        this.ingradients = ingradients;
        this.numerosity = numerosity;
    }

    public Storage() {

    }

    public String getIngradients() {
        return ingradients;
    }
    public int getNumerosity() {
        return numerosity;
    }

    public void setIngradients(String ingradients) {
        this.ingradients = ingradients;
    }

    public void setNumerosity(int numerosity) {
        this.numerosity = numerosity;
    }


    @Override
    public String toString() {
        return "Storage{" +
                ", name='" +ingradients + '\'' +
                ", numerosity =" + numerosity +'\'' +
                '}';
    }

}
