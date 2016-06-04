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

public class JDBCStorageDao implements StorageDao {


    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageDao.class);



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Storage load(String name)
    {
        try( Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM STORAGE WHERE name = ?"))
        {
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return createStorage(resultSet);
            } else
            {
                throw new RuntimeException("Cannot find employee with name "+name);
            }
        }  catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " +  e);
            throw new RuntimeException(e);
        }

    }





    private Storage createStorage(ResultSet resultSet) throws SQLException
    {
        Storage storage = new Storage();
        storage.setIngradients(resultSet.getString("name"));
        storage.setNumerosity(resultSet.getInt("numerosity"));
        return storage;
    }
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Storage> getAll()
    {
        LOGGER.info("Connecting to DB");
        List<Storage> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql ="SELECT * FROM STORAGE";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Storage storage = createStorage(resultSet);
                result.add(storage);
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
    public void createStorage(Storage storage)
    {
        createStorageWithTemplate(storage);
    }



    private void createStorageWithTemplate(Storage storage) {
        String sql = "INSERT INTO STORAGE VALUES (?, ?)";
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(sql,storage.getIngradients(),storage.getNumerosity());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteIngradient(String name) {
        String sql = "DELETE FROM STORAGE WHERE name = ?; ";
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(sql,name);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeNumerosity(int numerosity,String name) {
        String sql = "update STORAGE set numerosity = ? where name = ?";
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(sql,numerosity,name);

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Storage> getEndedIngradients(int deadLineNumerosity) {
        LOGGER.info("Connecting to DB");
        List<Storage> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM STORAGE WHERE numerosity < ?")) {
            statement.setInt(1, deadLineNumerosity);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(createStorage(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: " + e);
            throw new RuntimeException(e);
        }
    }

}
