package keepcoding.io.restaurea.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import keepcoding.io.restaurea.model.Dish;
import keepcoding.io.restaurea.model.Table;

public class RestaureaApplication extends Application {

    public static final String TABLE_LIST_CHANGED_ACTION = "keepcoding.io.restaurea.application.TABLE_LIST_CHANGED_ACTION";

    private static RestaureaApplication singleton;

    public RestaureaApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        sDishList = new ArrayList<>();
        sTableList = new ArrayList<>();
        sCurrentTable = -1;

    }

    private static ArrayList<Dish> sDishList;
    private static List<Table> sTableList;
    private static int sCurrentTable;

    public static ArrayList<Dish> getDishesList() {
        return sDishList;
    }

    public static void addDish(Dish dish){
        sDishList.add(dish);
    }

    public static List<Table> getTablesList() {
        return sTableList;
    }

    public static void addTable(Context context){
        // La mesa se identifica con el número, así que la nueva mesa tendrá como número la cantidad
        // de mesas que ya haya más uno.
        sTableList.add(new Table(sTableList.size()+1));

        sendDataSetChangedIntent(context);
    }

    public static int getCurrentTable() {
        return sCurrentTable;
    }

    public static void setCurrentTable(int currentTable) {
        sCurrentTable = currentTable;
    }

    protected static void sendDataSetChangedIntent(Context context) {
        Intent broadcast = new Intent(TABLE_LIST_CHANGED_ACTION);
        context.sendBroadcast(broadcast);
    }

}
