package ua.goit.java.controllers;

/**
 * Created by Администратор on 20.05.16.
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

public class EmployeeController {
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }




    public CookedmealsDAO getCookedmealsDao() {
        return cookedmealsDao;
    }

    public void setCookedmealsDao(CookedmealsDAO cookedmealsDao) {
        this.cookedmealsDao = cookedmealsDao;
    }

    private PlatformTransactionManager txManager;
    private EmployeeDao employeeDao;
     private CookedmealsDAO cookedmealsDao;




    @Transactional
    public List<Employee> getAllEmployees()
    {
        TransactionStatus status =txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Employee> result = employeeDao.getAll();
            txManager.commit(status);
            return result;
        } catch(Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

 @Transactional(propagation = Propagation.REQUIRED)
    public Employee getEmployeesByName() throws  IOException
    {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter the name of employee:");
        String name = br.readLine();
        return employeeDao.load(name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteEmployeeById() throws  IOException
    {
        System.out.println("Enter id of  which employee are you going to delete:");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        int id = Integer.parseInt( br.readLine());
        employeeDao.deleteEmployeeById(id);
        System.out.println("Employee was deleted successfully!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createEmployee() throws IOException {
        System.out.println("Adding new employee to data base");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter id of new employee:");
        int id = Integer.parseInt( br.readLine());
        System.out.println("Enter first name of new employee:");
        String firstName =  br.readLine();
        System.out.println("Enter last name of new employee:");
        String lastName =  br.readLine();
        System.out.println("Enter birthDay date of new employee:");
        String birthDay =  br.readLine();
        System.out.println("Enter phone number of new employee:");
        int phone =  Integer.parseInt(br.readLine());
        System.out.println("Enter salary of new employee:");
        float salary =  Float.parseFloat(br.readLine());
        System.out.println("Enter position of new employee:");
        String position  =  br.readLine();
        employeeDao.createEmployee(new Employee(id,firstName,lastName,birthDay,phone, salary,position));
        System.out.println("A new employee was added successfully!!!");
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public Cookedmeals getCookedMealsByNumber(int number)
    {
        return cookedmealsDao.load(number);
    }


    @Transactional
    public List<Cookedmeals> getAllCookedMeals()
    {
        TransactionStatus status =txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Cookedmeals> result = cookedmealsDao.getAll();
            txManager.commit(status);
            return result;
        } catch(Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }







}
