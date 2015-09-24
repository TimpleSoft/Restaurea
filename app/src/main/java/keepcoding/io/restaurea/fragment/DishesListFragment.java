package keepcoding.io.restaurea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.adapter.DishAdapter;
import keepcoding.io.restaurea.application.RestaureaApplication;
import keepcoding.io.restaurea.model.Dish;

public class DishesListFragment extends Fragment {

    public static DishesListFragment newInstance() {
        return new DishesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_dishes_list, container, false);

        final DishAdapter adaptador = new DishAdapter(getActivity(),
                RestaureaApplication.getDishesList());
        ListView dishesList = (ListView) root.findViewById(R.id.dishesList);
        dishesList.setAdapter(adaptador);
        dishesList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, android.view.View v, int position, long id) {

                        DishDialogFragment dFragment = new DishDialogFragment();
                        dFragment.mDish = (Dish)parent.getItemAtPosition(position);
                        dFragment.show(getActivity().getSupportFragmentManager(), "Dialog Fragment");

                    }
                });

        return root;
    }
}
