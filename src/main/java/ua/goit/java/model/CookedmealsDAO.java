package ua.goit.java.model;

import java.util.List;

/**
 * Created by Администратор on 30.05.16.
 */
public interface CookedmealsDAO {
    Cookedmeals load(int number);

    List<Cookedmeals> getAll();
}
