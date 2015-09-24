package keepcoding.io.restaurea.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import keepcoding.io.restaurea.model.Table;

public class TablesListFragment extends Fragment {

    private TableListListener mListener;
    private TableBroadcastReceiver mBroadcastReceiver;

    public static TablesListFragment newInstance() {
        return new TablesListFragment();
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
        View root = inflater.inflate(R.layout.fragment_table_list, container, false);

        final RestaureaApplication app = (RestaureaApplication) getActivity().getApplication();

        ListView list = (ListView)root.findViewById(R.id.tableList);
        final ArrayAdapter<Table> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, // Recuerda que esto es el layout de cada fila
                app.getTablesList());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mListener != null) {
                    mListener.onTableSelected(position);
                }

            }
        });

        mBroadcastReceiver = new TableBroadcastReceiver(adapter);
        // Me suscribo a notificaciones broadcast
        getActivity().registerReceiver(
                mBroadcastReceiver,
                new IntentFilter(RestaureaApplication.TABLE_LIST_CHANGED_ACTION));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Dejo de enterarme de cambios en el modelo Tables
        getActivity().unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (TableListListener) getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (TableListListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    // Métodos de menú
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_table_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add_table) {
            RestaureaApplication app = (RestaureaApplication) getActivity().getApplication();
            app.addTable(getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TableBroadcastReceiver extends BroadcastReceiver {
        private ArrayAdapter mAdapter;

        // Necesito el adapter al que voy a avisar de que hay nuevos datos
        public TableBroadcastReceiver(ArrayAdapter adapter) {
            super();
            mAdapter = adapter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Hay nuevos cambios, aviso al adaptador para que vuelva a recargarse
            mAdapter.notifyDataSetChanged();
        }
    }


    public interface TableListListener {
        void onTableSelected(int position);
    }

}
