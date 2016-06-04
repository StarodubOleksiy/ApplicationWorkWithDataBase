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

public class StorageController {
    private PlatformTransactionManager txManager;
    private StorageDao storageDao;
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public StorageDao getStorageDao() {
        return storageDao;
    }

    public void setStorageDao(StorageDao storageDao) {
        this.storageDao = storageDao;
    }
    @Transactional
    public List<Storage> getAllIngradients()
    {
        TransactionStatus status =txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Storage> result = storageDao.getAll();
            txManager.commit(status);
            return result;
        } catch(Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Storage getIngradientByName() throws  IOException
    {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter the name of ingradient:");
        String name = br.readLine();
        return storageDao.load(name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createStorage() throws IOException {
        System.out.println("Adding new ingradient to data base");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter name of new ingradient:");
        String name =  br.readLine();
        System.out.println("Enter numerosity of new ingradient:");
        int numerosity = Integer.parseInt( br.readLine());

        storageDao.createStorage(new Storage(name,numerosity));
        System.out.println("A new ingradient was added successfully!!!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteIngradient() throws  IOException
    {
        System.out.println("Enter name of  which ingradient are you going to delete:");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        String name =  br.readLine();
        storageDao.deleteIngradient(name);
        System.out.println("Ingradient was deleted successfully!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void changeNumerosity() throws  IOException
    {
        System.out.println("Enter name of  which ingradient are you going to change numerosity:");
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Enter new numerosity of ingradient:");
        int numerosity = Integer.parseInt( br.readLine());
        System.out.println("Enter name of ingradient:");
        String name =  br.readLine();
        storageDao.changeNumerosity(numerosity,name);
        System.out.println("Numerosity of ingradient was changed successfully!");
    }


    @Transactional
    public List<Storage> getEndedIngradients() throws  IOException
    {
        TransactionStatus status =txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
            System.out.println("Enter new numerosity of  ended ingradient:");
            int deadLinenumerosity = Integer.parseInt( br.readLine());
            List<Storage> result = storageDao.getEndedIngradients(deadLinenumerosity);
            txManager.commit(status);
            return result;
        } catch(Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }



}
