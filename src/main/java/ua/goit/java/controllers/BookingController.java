package ua.goit.java.controllers;

/**
 * Created by Администратор on 03.06.16.
 */
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ua.goit.java.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BookingController {
    private BookingDao bookingDao;
    private PlatformTransactionManager txManager;

    public void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    public BookingDao getBookingDao() {
        return bookingDao;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }



    @Transactional
    public List<Booking> getOpenBooking()
    {
        TransactionStatus status =txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Booking> result = bookingDao.getOpen();
            txManager.commit(status);
            return result;
        } catch(Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<Booking> getCloseBooking()
    {
        TransactionStatus status =txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Booking> result = bookingDao.getClose();
            txManager.commit(status);
            return result;
        } catch(Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createBooking() throws IOException {
        System.out.println("Adding new booking to data base");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter new order number:");
        String orderNumber =  br.readLine();
        System.out.println("Enter employee_id of new booking:");
        int employee_ID = Integer.parseInt( br.readLine());
        System.out.println("Enter table_id of new booking:");
        int table_ID = Integer.parseInt( br.readLine());
        System.out.println("Enter the state_type of new booking:");
        String state_Type =  br.readLine();
        System.out.println("Enter date of new booking:");
        String date =  br.readLine();
        String state = new String("open");
        bookingDao.createBooking(new Booking(orderNumber, employee_ID, table_ID, state_Type,date,state));
        System.out.println("A new booking was added successfully!!!");
    }

    @Transactional
    public void setCloseBooking() throws  IOException
    {
        System.out.println("setCloseBooking");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter order number which you want to set close");
        String orderNumber =  br.readLine();
        bookingDao.setCloseBooking(orderNumber);
        System.out.println("Booking set close successfully!");
    }

    @Transactional
    public void deleteBooking() throws  IOException
    {
        System.out.println("delete Booking");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter order number of booking which you want to delete");
        String orderNumber =  br.readLine();
        bookingDao.deleteBooking(orderNumber);
        System.out.println("Booking was deleted successfully!");
    }

}
