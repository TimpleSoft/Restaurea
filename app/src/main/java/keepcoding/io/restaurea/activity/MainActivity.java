package keepcoding.io.restaurea.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.application.RestaureaApplication;
import keepcoding.io.restaurea.fragment.TablesListFragment;
import keepcoding.io.restaurea.model.Dish;

public class MainActivity extends AppCompatActivity implements TablesListFragment.TableListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DishesDownloader dishesDownloader = new DishesDownloader();
        dishesDownloader.execute();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.list_of_tables, TablesListFragment.newInstance())
                .commit();

    }

    @Override
    public void onTableSelected(int position) {

        RestaureaApplication.setCurrentTable(position);

        Intent intent = new Intent(MainActivity.this,TableActivity.class);
        startActivity(intent);

    }


    // AsyncTask para descargar en segundo plano la lista de platos.
    private class DishesDownloader extends AsyncTask<String, Integer, Boolean> {

        RestaureaApplication app;

        public DishesDownloader() {
            app = (RestaureaApplication)getApplication();
        }


        protected Boolean doInBackground(String... params) {
            URL url;
            InputStream input = null;
            try {
                url = new URL("http://www.mocky.io/v2/56012b909635788b130aa373");
                HttpURLConnection con = (HttpURLConnection) (url.openConnection());
                con.setConnectTimeout(5000);
                con.connect();
                int responseLength = con.getContentLength();
                byte data[] = new byte[1024];
                long currentBytes  = 0;
                int downloadedBytes;
                input = con.getInputStream();
                StringBuilder sb = new StringBuilder();
                while ((downloadedBytes = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }

                    sb.append(new String(data, 0, downloadedBytes));

                    // Si tuviéramos una longitud de respuesta podríamos incluso actualizar la barra de progreso
                    if (responseLength > 0) {
                        currentBytes += downloadedBytes;
                        publishProgress((int) ((currentBytes * 100) / responseLength));
                    }
                    else {
                        currentBytes++;
                        publishProgress((int)currentBytes * 10);
                    }

                }

                JSONArray respJSON = new JSONArray(sb.toString());

                for(int i=0; i<respJSON.length(); i++){
                    JSONObject obj = respJSON.getJSONObject(i);

                    List<String> allergenList = new ArrayList<>();
                    JSONArray allergens = new JSONArray(obj.getString("allergens"));
                    for(int j=0; j<allergens.length(); j++){
                        allergenList.add(allergens.get(j).toString());
                    }

                    app.addDish(new Dish(
                            obj.getString("title"),
                            obj.getString("photo"),
                            (float) obj.getDouble("price"),
                            allergenList));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            return true;
        }

        protected void onPostExecute(Boolean result) {

            if(result) {

                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.loadLayout);
                relativeLayout.setVisibility(View.GONE);

                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.list_of_tables);
                frameLayout.setVisibility(View.VISIBLE);

            }

        }

    }

}
