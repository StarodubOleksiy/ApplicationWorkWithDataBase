package ua.goit.java.model;

/**
 * Created by Администратор on 26.05.16.
 */

import java.util.List;


public interface DishDao {
    Dish load(String name);
    List<Dish> getAll();
    void createDish(Dish dish);
    public void deleteDishById(int id);

}
