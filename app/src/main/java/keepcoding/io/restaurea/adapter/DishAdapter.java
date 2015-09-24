package keepcoding.io.restaurea.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.model.Dish;

public class DishAdapter extends BaseAdapter {

    private ArrayList<Dish> mData = new ArrayList<>();
    private Context mContext;

    public DishAdapter(Context context, ArrayList data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {

        GridView dishAllergensGrid;
        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Dish item = mData.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.dish_item, null);
            holder = new ViewHolder();

            holder.dishAllergensGrid = (GridView) convertView.findViewById(R.id.allergensGrid);
            holder.dishPrice = (TextView) convertView.findViewById(R.id.dishPrice);
            holder.dishName = (TextView) convertView.findViewById(R.id.dishName);
            holder.dishImage = (ImageView) convertView.findViewById(R.id.dishImage);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.position = position;
        holder.dishName.setText(item.getName());
        holder.dishPrice.setText(String.valueOf(item.getPrice()) + "€");


        AllergenAdapter adapter = new AllergenAdapter(mContext, item.getAllergenList(), AllergenAdapter.SIZE_SMALL);
        holder.dishAllergensGrid.setAdapter(adapter);
        holder.dishAllergensGrid.setEnabled(false);

        Picasso.with(mContext)
                .load(item.getPhoto())
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.plato))
                .error(ContextCompat.getDrawable(mContext, R.drawable.plato))
                .into(holder.dishImage);

        return convertView;
    }

}
