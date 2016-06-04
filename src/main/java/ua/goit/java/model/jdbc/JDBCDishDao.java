package ua.goit.java.model.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.model.Dish;
import ua.goit.java.model.DishDao;
import ua.goit.java.model.Employee;
import ua.goit.java.model.EmployeeDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 26.05.16.
 */
public class JDBCDishDao implements DishDao {
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(DishDao.class);



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish load(String name)
    {
        try( Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM DISH WHERE name = ?"))
        {
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return createDish(resultSet);
            } else
            {
                throw new RuntimeException("Cannot find dish with name "+name);
            }
        }  catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +  e);
            throw new RuntimeException(e);
        }

    }

    private Dish createDish(ResultSet resultSet) throws SQLException
    {
        Dish dish = new Dish();
        dish.setDish_Id(resultSet.getInt("dish_id"));
        dish.setIngradients_Id(resultSet.getInt("ingradients_id"));
        dish.setName(resultSet.getString("name"));
        dish.setWeight(resultSet.getFloat("weight"));
        dish.setPrice(resultSet.getFloat("cost"));
         return dish;
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> getAll()
    {
        LOGGER.info("Connecting to DB");
        List<Dish> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql ="SELECT * FROM DISH";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Dish dish = createDish(resultSet);
                result.add(dish);
            }

            LOGGER.info("Successfully connected to DB!!!");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteDishById(int id) {
        String sql = "DELETE FROM DISH WHERE dish_id = ? ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occured while connecting to DB: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createDish(Dish dish)
    {
        createDishWithTemplate(dish);
    }

    private void createDishWithTemplate(Dish dish) {
        String sql = "INSERT INTO DISH VALUES (?, ?, ?, ?, ?)";
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(sql,dish.getDish_Id(),dish.getIngradients_Id(),
                dish.getName(),dish.getWeight(),
                dish.getPrice());
    }


}
