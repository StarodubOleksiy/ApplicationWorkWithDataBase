package ua.goit.java.model;

/**
 * Created by Администратор on 26.05.16.
 */
public class Dish {


    public int getDish_Id() {
        return dish_Id;
    }

    public int getIngradients_Id() {
        return ingradients_Id;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    public float getPrice() {
        return price;
    }

    public void setDish_Id(int dish_Id) {
        this.dish_Id = dish_Id;
    }

    public void setIngradients_Id(int ingradients_Id) {
        this.ingradients_Id = ingradients_Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private int dish_Id;
    private int ingradients_Id;
    private String name;
    private float weight;
    private float price;

    public Dish(int dish_Id, int ingradients_Id, String name, float weight, float price) {
        this.dish_Id = dish_Id;
        this.ingradients_Id = ingradients_Id;
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Dish() {

    }


    @Override
    public String toString() {
        return "Dish{" +
                ", dish_Id='" +dish_Id + '\'' +
                ", ingradients_Id='" + ingradients_Id + '\'' +
                ", name=" + name +
                ", weight='" + weight + '\'' +
                ", price='" + price + '\'' +
                 '}';
    }
}
