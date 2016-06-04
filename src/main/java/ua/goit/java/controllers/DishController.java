package ua.goit.java.controllers;

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

public class DishController {
    private PlatformTransactionManager txManager;
    private DishDao dishDao;
      public void setTxManager(PlatformTransactionManager txManager) {
            this.txManager = txManager;
        }
    public DishDao getDishDao() {
        return dishDao;
    }
    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Transactional
    public List<Dish> getAllDishees() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Dish> result = dishDao.getAll();
            txManager.commit(status);
            return result;
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Dish getDishByName() throws IOException
    {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter the name of dish:");
        String name = br.readLine();
        return dishDao.load(name);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDishById() throws IOException
    {
        System.out.println("Enter id of  which dish are you going to delete:");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        int id = Integer.parseInt( br.readLine());
        dishDao.deleteDishById(id);
        System.out.println("Dish was deleted successfully!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createDish() throws IOException {
        System.out.println("Adding new dish to data base");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter id of new dish:");
        int dish_ID = Integer.parseInt( br.readLine());
        System.out.println("Enter id  of ingradients in this dish:");
        int ingradient_ID = Integer.parseInt( br.readLine());
        System.out.println("Enter the name of new dish:");
        String name =  br.readLine();
        System.out.println("Enter weight of new dish:");
        float weight =  Float.parseFloat(br.readLine());
        System.out.println("Enter price of new dish:");
        float price  =  Float.parseFloat(br.readLine());
        dishDao.createDish(new Dish(dish_ID,ingradient_ID ,name, weight, price ));
        System.out.println("A new dish was added successfully!!!");
    }



}
