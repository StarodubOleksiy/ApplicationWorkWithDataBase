package ua.goit.java.model;

/**
 * Created by Администратор on 30.05.16.
 */
public class Cookedmeals {
    public int getNumberOfDish() {
        return numberOfDish;
    }

    public String getDate() {
        return date;
    }

    public void setNumberOfDish(int numberOfDish) {
        this.numberOfDish = numberOfDish;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private int numberOfDish;
    private String date;
    @Override
    public String toString() {
        return "cooked meals{" +
                ", numberOfDish='" + numberOfDish + '\'' +
                ", dateOfCooking='" + date + '\'' +
                      '}';
    }
}
