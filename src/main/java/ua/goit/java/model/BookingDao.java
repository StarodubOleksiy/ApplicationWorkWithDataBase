package ua.goit.java.model;

import java.util.List;

/**
 * Created by Администратор on 31.05.16.
 */
public interface BookingDao {

    List<Booking> getOpen();
    List<Booking> getClose();
    void createBooking(Booking booking);
    void deleteBooking(String order_number);
    void setCloseBooking(String order_number);
}
