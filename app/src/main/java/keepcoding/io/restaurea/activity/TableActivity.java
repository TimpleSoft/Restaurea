package keepcoding.io.restaurea.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.application.RestaureaApplication;
import keepcoding.io.restaurea.fragment.DishesListFragment;
import keepcoding.io.restaurea.fragment.TableDishesListFragment;

public class TableActivity extends AppCompatActivity implements TableDishesListFragment.AddDishListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        setTitle(RestaureaApplication.getTablesList().get(RestaureaApplication.getCurrentTable()).toString());

        FragmentManager fm = getSupportFragmentManager();

        // Preguntamos primero si existe el hueco para la lista de mesas en la interfaz (fichero xml)
        // que ha cargado para esta ejecución (caso teléfono o tablet en horizontal)
        if (findViewById(R.id.tableDishesList) != null) {
            // Hay hueco, y habiendo hueco preguntamos si el fragment citylist ya lo teníamos
            // o hay que crearlo
            if (fm.findFragmentById(R.id.tableDishesList) == null) {
                fm.beginTransaction()
                        .add(R.id.tableDishesList, TableDishesListFragment.newInstance())
                        .commit();
            }
        }


        // Preguntamos primero si existe el hueco para la lista de plata de la mesa seleccionada
        // en la interfaz (fichero xml)
        if (findViewById(R.id.list_of_dishes) != null) {

            // Hay hueco, y habiendo hueco preguntamos si el fragment citylist ya lo teníamos
            // o hay que crearlo
            if (fm.findFragmentById(R.id.list_of_dishes) == null) {
                if(RestaureaApplication.getCurrentTable() != -1) {
                    fm.beginTransaction()
                            .add(R.id.list_of_dishes, DishesListFragment.newInstance())
                            .commit();
                }
            }
        }

    }

    @Override
    public void onAddDish() {

        FragmentManager fm = getSupportFragmentManager();

        // Si el frame de lista de platos no está, quiere decir que estamos en modo Portrait
        // por lo que haremos un replace del fragment con la lista de platos.
        // En caso de que sea Landscape no hará nada.
        if (findViewById(R.id.list_of_dishes) == null) {

            // Añadiendo el 'addToBackStack' no es necesario sobreescribir el método onBackPress
            // ya que el FragmentManager se encarga de gestionarlo.
            fm.beginTransaction()
                .replace(R.id.tableDishesList, DishesListFragment.newInstance())
                .addToBackStack(RestaureaApplication.getTablesList().get(RestaureaApplication.getCurrentTable()).toString())
                .commit();

        }
    }

}
