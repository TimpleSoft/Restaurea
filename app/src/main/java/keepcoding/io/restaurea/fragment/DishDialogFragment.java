package keepcoding.io.restaurea.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import keepcoding.io.restaurea.R;
import keepcoding.io.restaurea.application.RestaureaApplication;
import keepcoding.io.restaurea.model.Dish;

public class DishDialogFragment extends DialogFragment {

    public static final String DISHES_LIST_CHANGED_ACTION = "keepcoding.io.restaurea.fragment.DishDialogFragment.DISHES_LIST_CHANGED_ACTION";

    public static Dish mDish;

    private EditText txtObservaciones;
    private boolean mIsEditable;


    public static DishDialogFragment newInstance() {
        return new DishDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_dish, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        if(getArguments() != null) {
            mIsEditable = getArguments().getBoolean("isEditable", true);
        }else{
            mIsEditable = true;
        }

        TextView dishName = (TextView) view.findViewById(R.id.dishName);
        dishName.setText(mDish.getName());

        txtObservaciones = (EditText) view.findViewById(R.id.txtObservaciones);

        LinearLayout allergensLayout = (LinearLayout) view.findViewById(R.id.dishAllergens);
        for (String allergen : mDish.getAllergenList()) {
            ImageView img = new ImageView(getActivity());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int)getActivity().getResources().getDimension(R.dimen.dimen_allergen_fragment),
                    (int)getActivity().getResources().getDimension(R.dimen.dimen_allergen_fragment));
            img.setLayoutParams(params);

            String uri = "@drawable/" + allergen;
            int imageResource = getActivity().getResources().getIdentifier(uri, null, getActivity().getPackageName());

            Drawable res = ContextCompat.getDrawable(getActivity(), imageResource);
            img.setImageDrawable(res);

            allergensLayout.addView(img);
        }

        ImageView imgTeam = (ImageView) view.findViewById(R.id.dishImage);
        Picasso.with(getActivity())
                .load(mDish.getPhoto())
                .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.plato))
                .error(ContextCompat.getDrawable(getActivity(), R.drawable.plato))
                .into(imgTeam);

        Button btnAddDish = (Button) view.findViewById(R.id.btnAddDish);
        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDish.setObservaciones(txtObservaciones.getText().toString());
                RestaureaApplication.getTablesList().get(RestaureaApplication.getCurrentTable()).addDish(mDish);
                sendDataSetChangedIntent();
                dismiss();
            }
        });

        if(!mIsEditable){
            btnAddDish.setVisibility(View.GONE);
            txtObservaciones.setEnabled(false);
            txtObservaciones.setText(mDish.getObservaciones());
        }

        return view;
    }


    protected void sendDataSetChangedIntent() {
        Intent broadcast = new Intent(DISHES_LIST_CHANGED_ACTION);
        getActivity().sendBroadcast(broadcast);
    }
}
