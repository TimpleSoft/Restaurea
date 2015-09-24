package keepcoding.io.restaurea.model;

import java.util.ArrayList;

public class Table {

    private int mNumber;
    private ArrayList<Dish> mDishList;

    public Table(int number) {
        mNumber = number;
        mDishList = new ArrayList<>();
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public void addDish(Dish dish){
        mDishList.add(dish);
    }

    public ArrayList<Dish> getDishesList(){
        return mDishList;
    }

    public float getBill(){
        float bill = 0F;
        for (Dish dish: mDishList) {
            bill += dish.getPrice();
        }
        return bill;
    }

    @Override
    public String toString() {
        return "Mesa Nº " + String.valueOf(getNumber());
    }

}
