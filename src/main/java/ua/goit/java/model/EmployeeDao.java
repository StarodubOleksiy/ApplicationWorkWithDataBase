package ua.goit.java.model;



import java.util.List;

/**
 * Created by Администратор on 06.05.16.
 */
public interface EmployeeDao {
    Employee load(String name);
    public void deleteEmployeeById(int id);

    List<Employee> getAll();
   void createEmployee(Employee employee);
}
