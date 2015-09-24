package keepcoding.io.restaurea.model;

import java.util.List;

public class Dish {

    private String mName;
    private String mPhoto;
    private float mPrice;
    private List<String> mAllergenList;
    private String mObservaciones;

    public Dish(String name, String photo, float price, List<String> allergenList) {
        mName = name;
        mPhoto = photo;
        mPrice = price;
        mAllergenList = allergenList;
        mObservaciones = "";

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public List<String> getAllergenList() {
        return mAllergenList;
    }

    public void setAllergenList(List<String> allergenList) {
        mAllergenList = allergenList;
    }

    public String getObservaciones() {
        return mObservaciones;
    }

    public void setObservaciones(String observaciones) {
        mObservaciones = observaciones;
    }

    @Override
    public String toString() {
        return getName();
    }
}
