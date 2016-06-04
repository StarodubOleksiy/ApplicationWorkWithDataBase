package ua.goit.java.model.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.model.Cookedmeals;
import ua.goit.java.model.CookedmealsDAO;
import ua.goit.java.model.Dish;
import ua.goit.java.model.DishDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 30.05.16.
 */
public class JDBCCookedmealsDao  implements CookedmealsDAO {
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(CookedmealsDAO.class);



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Cookedmeals load(int number)
    {
        try( Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM COOKEDMEALS WHERE number = ?"))
        {
            statement.setInt(1,number);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return createCookedmeals(resultSet);
            } else
            {
                throw new RuntimeException("Cannot find dish with name "+number);
            }
        }  catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +  e);
            throw new RuntimeException(e);
        }

    }


    private Cookedmeals createCookedmeals(ResultSet resultSet) throws SQLException
    {
        Cookedmeals cookedMeals = new Cookedmeals();
        cookedMeals.setNumberOfDish(resultSet.getInt("number"));
        cookedMeals.setDate(resultSet.getString("date"));
        return cookedMeals;
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Cookedmeals> getAll()
    {
        LOGGER.info("Connecting to DB");
        List<Cookedmeals> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql ="SELECT * FROM COOKEDMEALS";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Cookedmeals createMeal = createCookedmeals(resultSet);
                result.add(createMeal);
            }

            LOGGER.info("Successfully connected to DB!!!");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
