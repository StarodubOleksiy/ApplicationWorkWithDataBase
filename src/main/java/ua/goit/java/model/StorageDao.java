package ua.goit.java.model;

/**
 * Created by Администратор on 28.05.16.
 */

import java.util.List;

public interface StorageDao {
    void createStorage(Storage storage);
    Storage load(String name);
    void deleteIngradient(String name);
    void changeNumerosity(int numerosity,String name);
    List<Storage> getAll();
    public List<Storage> getEndedIngradients(int deadLineNumerosity);
}
