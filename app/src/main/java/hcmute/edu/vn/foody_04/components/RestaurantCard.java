package hcmute.edu.vn.foody_04.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.R;
import hcmute.edu.vn.foody_04.database.DbHandler;


public class RestaurantCard extends LinearLayout {
    private Restaurant restaurant;


    public RestaurantCard(Context context, Restaurant restaurant) {
        super(context);
        this.restaurant = restaurant;
        initControl(context);
    }

    public RestaurantCard(Context context) {
        super(context);
        initControl(context);
    }

    private void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.restaurant_card, this);

        ImageView image = findViewById(R.id.imvRestaurant);
        TextView tvName = findViewById(R.id.tvRestaurantCardName);
        TextView tvTime = findViewById(R.id.tvRestaurantCardTime);
        TextView rvAddress = findViewById(R.id.rvRestaurantCardAddress);

        // Set information for food cart
        image.setImageBitmap(DbHandler.convertByteArrayToBitmap(restaurant.getImage()));
        tvName.setText(restaurant.getName());
        tvTime.setText(restaurant.getTime());
        rvAddress.setText(restaurant.getAddress());
    }
}
