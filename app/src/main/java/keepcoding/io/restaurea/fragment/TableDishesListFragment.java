package keepcoding.io.restaurea.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.application.RestaureaApplication;
import keepcoding.io.restaurea.model.Dish;
import keepcoding.io.restaurea.model.Table;

public class TableDishesListFragment extends Fragment {

    private Table mTable;
    private AddDishListener mListener;
    private DishAddBroadcastReceiver mBroadcastReceiver;

    public static TableDishesListFragment newInstance() {
        return new TableDishesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_table_dishes_list, container, false);

        final RestaureaApplication app = (RestaureaApplication) getActivity().getApplication();

        Log.v("Restaurea", "onCreateView TableDishesListFragment");

        mTable = app.getTablesList().get(app.getCurrentTable());

        ListView list = (ListView)root.findViewById(R.id.tableDishesList);
        final ArrayAdapter<Dish> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, // Recuerda que esto es el layout de cada fila
                mTable.getDishesList());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DishDialogFragment dFragment = new DishDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isEditable", false);
                dFragment.setArguments(bundle);
                dFragment.mDish = (Dish)parent.getItemAtPosition(position);
                dFragment.show(getActivity().getSupportFragmentManager(), "Dialog Fragment");
            }
        });

        mBroadcastReceiver = new DishAddBroadcastReceiver(adapter);
        // Me suscribo a notificaciones broadcast
        getActivity().registerReceiver(
                mBroadcastReceiver,
                new IntentFilter(DishDialogFragment.DISHES_LIST_CHANGED_ACTION));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Dejo de enterarme de cambios en el modelo Tables
        getActivity().unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;
    }

    // Métodos de menú
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_table, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_get_bill) {
            Snackbar.make(getView(),
                    "La cuenta de la " + mTable.toString() + " es de " + String.valueOf(mTable.getBill()) + "€",
                    Snackbar.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_add_dish) {
            mListener.onAddDish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuAddDish = menu.findItem(R.id.action_add_dish);
        menuAddDish.setVisible(getActivity().findViewById(R.id.dishesList) == null);

    }

    private class DishAddBroadcastReceiver extends BroadcastReceiver {
        private ArrayAdapter mAdapter;

        // Necesito el adapter al que voy a avisar de que hay nuevos datos
        public DishAddBroadcastReceiver(ArrayAdapter adapter) {
            super();
            mAdapter = adapter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Hay nuevos cambios, aviso al adaptador para que vuelva a recargarse
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (AddDishListener) getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (AddDishListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }


    public interface AddDishListener {
        void onAddDish();
    }

}
