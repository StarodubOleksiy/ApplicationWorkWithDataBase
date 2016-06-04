package ua.goit.java.model.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.goit.java.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class JDBCBookingDao implements BookingDao {
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingDao.class);


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private Booking createBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setOrder_number(resultSet.getString("order_number"));
        booking.setEmployee_id(resultSet.getInt("employee_id"));
        booking.setTable_id(resultSet.getInt("table_id"));
        booking.setState_type(resultSet.getString("state_type"));
        booking.setBooking_date(resultSet.getString("date"));
        booking.setState(resultSet.getString("state"));
        return booking;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Booking> getOpen() {
        LOGGER.info("Connecting to DB");
        List<Booking> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM BOOKING WHERE state=?")) {
            statement.setString(1, "open");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(createBooking(resultSet));
            }
            return result;
         } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Booking> getClose() {
        LOGGER.info("Connecting to DB");
        List<Booking> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM BOOKING WHERE state=?")) {
            statement.setString(1, "close");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(createBooking(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createBooking(Booking booking)
    {
        createBookingWithTemplate(booking);
    }



    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteBooking(String order_number)
    {
      try( Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM BOOKING WHERE order_number = ?"))
        {
            statement.setString(1,order_number);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
               Booking booking = createBooking(resultSet);
                if(!booking.getState().equals("close"))
                {
                    String sql = "DELETE FROM BOOKING WHERE order_number = ?";
                    JdbcTemplate template = new JdbcTemplate(dataSource);
                    template.update(sql,booking.getOrder_number());

                } else {
                    System.out.println("booking.getState() = "+booking.getState());
                    for(int i = 0; i <booking.getState().length(); ++i)
                        System.out.println(booking.getState().charAt(i));
                    throw new RuntimeException("Cannot delete booking with state=close");
                }
                } else
            {
                throw new RuntimeException("Cannot find booking with order_number "+order_number);
            }
        }  catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +  e);
            throw new RuntimeException(e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void setCloseBooking(String order_number) {
            String sql = "update BOOKING set state = 'close' where order_number = ?";
            JdbcTemplate template = new JdbcTemplate(dataSource);
            template.update(sql,order_number);
       }

    private void createBookingWithTemplate(Booking booking) {
        String sql = "INSERT INTO BOOKING VALUES (?, ?, ?, ?, ?, ?)";
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(sql,booking.getOrder_number(),booking.getEmployee_id(),
                booking.getTable_id(), booking.getState_type(),
                booking.getState(), booking.getBooking_date());
    }


}



