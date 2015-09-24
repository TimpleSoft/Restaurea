package keepcoding.io.restaurea.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.adapter.DishAdapter;
import keepcoding.io.restaurea.application.RestaureaApplication;
import keepcoding.io.restaurea.fragment.DishDialogFragment;
import keepcoding.io.restaurea.model.Dish;

public class DishActivity extends AppCompatActivity{

    //public static final String DISHES_LIST_CHANGED_ACTION = "keepcoding.io.restaurea.activity.DishActivity.DISHES_LIST_CHANGED_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        RestaureaApplication app = (RestaureaApplication) getApplication();

        final DishAdapter adaptador = new DishAdapter(DishActivity.this, app.getDishesList());
        ListView dishesList = (ListView) findViewById(R.id.dishesList);
        dishesList.setAdapter(adaptador);
        dishesList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, android.view.View v, int position, long id) {

                        DishDialogFragment dFragment = new DishDialogFragment();
                        dFragment.mDish = (Dish)parent.getItemAtPosition(position);
                        dFragment.show(getSupportFragmentManager(), "Dialog Fragment");

                    }
                });

        dishesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                /*TableActivity.sTable.addDish((Dish) adaptador.getItem(position));
                sendDataSetChangedIntent();
                finish();*/
                return true;

            }
        });

    }

}
