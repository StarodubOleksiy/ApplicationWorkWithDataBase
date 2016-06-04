package ua.goit.java.model;

/**
 * Created by Администратор on 31.05.16.
 */
public class Booking {
    public Booking() {

    }

    public Booking(String order_number, int employee_id, int table_id, String state_type, String booking_date, String state) {
        this.order_number = order_number;
        this.employee_id = employee_id;
        this.table_id = table_id;
        this.state_type = state_type;
        this.booking_date = booking_date;
        this.state = state;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public void setState_type(String state_type) {
        this.state_type = state_type;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrder_number() {
        return order_number;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public String getState_type() {
        return state_type;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getState() {
        return state;
    }

    private String order_number;
    private int employee_id;
    private int table_id;
    private String state_type;
    private String booking_date;
    private String state;


    @Override
    public String toString() {
        return "Booking {" +
                ", order_number='" + order_number + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", table_id;r='" + table_id + '\'' +
                ", state_type='" + state_type + '\'' +
                ", booking_dater='" + booking_date + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

}
