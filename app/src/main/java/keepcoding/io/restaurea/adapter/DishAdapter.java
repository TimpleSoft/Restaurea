package keepcoding.io.restaurea.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        LinearLayout allergensLayout;
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

            holder.allergensLayout = (LinearLayout) convertView.findViewById(R.id.dishAllergens);
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

        //if(item.getAllergenList().size() != 0) {
        holder.allergensLayout.removeAllViewsInLayout();
        for (String allergen : item.getAllergenList()) {
            ImageView img = new ImageView(mContext);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int)mContext.getResources().getDimension(R.dimen.dimen_allergen),
                    (int)mContext.getResources().getDimension(R.dimen.dimen_allergen));
            img.setLayoutParams(params);

            String uri = "@drawable/" + allergen;
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());

            Drawable res = ContextCompat.getDrawable(mContext, imageResource);
            img.setImageDrawable(res);

            holder.allergensLayout.addView(img);
        }
        //}else{
        //    holder.allergensLayout.removeAllViewsInLayout();
        //}

        Picasso.with(mContext)
                .load(item.getPhoto())
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.plato))
                .error(ContextCompat.getDrawable(mContext, R.drawable.plato))
                .into(holder.dishImage);

        return convertView;
    }

}
