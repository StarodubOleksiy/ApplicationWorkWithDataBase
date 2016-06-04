package ua.goit.java.model.jdbc;

import ch.qos.logback.classic.jul.LevelChangePropagator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.java.model.Employee;
import ua.goit.java.model.EmployeeDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class JDBCEmployeeDao implements EmployeeDao {


    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee load(String name)
    {
        try( Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                         connection.prepareStatement("SELECT * FROM EMPLOYEE WHERE firstname = ?"))
        {
               statement.setString(1,name);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                   return createEmployee(resultSet);
                 } else
                {
                    throw new RuntimeException("Cannot find employee with name "+name);
                }
            }  catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +  e);
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createEmployee(Employee employee)
    {
      createEmployeeWithConnection(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
   public void deleteEmployeeById(int id) {
        String sql = "DELETE FROM EMPLOYEE WHERE employee_id = ?; ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occured while connecting to DB: " + e);
            throw new RuntimeException(e);
        }
    }


    private void createEmployeeWithConnection(Employee employee) {
        String sql = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employee.getID());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getBirthDay_date());
            statement.setInt(5, employee.getTelephone());
            statement.setString(6, employee.getPosition());
            statement.setFloat(7, employee.getSalary());
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occured while connecting to DB: " + e);
            throw new RuntimeException(e);
        }
    }




    private Employee createEmployee(ResultSet resultSet) throws SQLException
    {
        Employee employee = new Employee();
        employee.setID(resultSet.getInt("employee_id"));
        employee.setFirstName(resultSet.getString("firstname"));
        employee.setLastName(resultSet.getString("lastname"));
        employee.setBirthDay_date(resultSet.getString("birthday"));
        employee.setTelephone(resultSet.getInt("telephone"));
        employee.setSalary(resultSet.getFloat("salary"));
        employee.setPosition(resultSet.getString("position"));
        return employee;
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getAll()
    {
        LOGGER.info("Connecting to DB");
        List<Employee> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql ="SELECT * FROM EMPLOYEE";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Employee employee = createEmployee(resultSet);
                result.add(employee);
            }

            LOGGER.info("Successfully connected to DB!!!");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +e);
        throw new RuntimeException(e);
        }
        return result;
    }



}
