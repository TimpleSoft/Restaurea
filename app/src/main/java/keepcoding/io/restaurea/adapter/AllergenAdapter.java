package keepcoding.io.restaurea.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import keepcoding.io.restaurea.R;

public class AllergenAdapter  extends BaseAdapter {

    public static final int SIZE_SMALL = 0;
    public static final int SIZE_BIG = 1;

    private ArrayList<String> mData = new ArrayList<>();
    private Context mContext;
    private int mSize;

    public AllergenAdapter(Context context, ArrayList data, int size) {
        mContext = context;
        mData = data;
        mSize = size;
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
        ImageView imgAllergen;
        int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        String item = mData.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(mSize == SIZE_BIG) {
                convertView = inflater.inflate(R.layout.allergen_item_big, null);
            }else{
                convertView = inflater.inflate(R.layout.allergen_item_small, null);
            }
            holder = new ViewHolder();
            holder.imgAllergen = (ImageView) convertView.findViewById(R.id.imgAllergen);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.position = position;
        String uri = "@drawable/" + item;
        int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());

        Drawable res = ContextCompat.getDrawable(mContext, imageResource);
        holder.imgAllergen.setImageDrawable(res);

        return convertView;
    }
}
