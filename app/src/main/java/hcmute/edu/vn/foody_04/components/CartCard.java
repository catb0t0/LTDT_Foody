package hcmute.edu.vn.foody_04.components;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.OrderDetail;
import hcmute.edu.vn.foody_04.MainActivity;
import hcmute.edu.vn.foody_04.R;
import hcmute.edu.vn.foody_04.database.DbHandler;
import hcmute.edu.vn.foody_04.fragment.ChatFragment;

public class CartCard extends LinearLayout {
    private Food food;
    private String address;
    private OrderDetail card;
    private Boolean activatedDelete;

    public CartCard(Context context) {
        super(context);
        initControl(context);
    }

    public CartCard(Context context, Food food, String address, OrderDetail card) {
        super(context);
        this.food = food;
        this.address = address;
        this.card = card;
        this.activatedDelete = false;
        initControl(context);
    }

    public CartCard(Context context, Food food, String address, OrderDetail card, Boolean activatedDelete) {
        super(context);
        this.food = food;
        this.address = address;
        this.card = card;
        this.activatedDelete = activatedDelete;
        initControl(context);
    }

    @SuppressLint("SetTextI18n")
    private void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cart_card, this);

        ImageView image = findViewById(R.id.imageCartFood);
        TextView tvName = findViewById(R.id.tvFoodNameCart);
        TextView tvAddress = findViewById(R.id.tvFoodRestaurantAddress);
        TextView tvPrice = findViewById(R.id.tvFoodPrice);

        ImageButton btnDelete = findViewById(R.id.btnDeleteCartItem);
        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bạn có muốn xóa món " + food.getName() + " không?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                MainActivity.dao.deleteOrderDetailByOrderIdAndFoodId(card.getOrderId(), food.getId());
                ChatFragment.cartContainer.removeView(this);
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {});
            dialog.show();
        });

        if(!activatedDelete) btnDelete.setVisibility(INVISIBLE);

        // Set information for cart card
        image.setImageBitmap(DbHandler.convertByteArrayToBitmap(food.getImage()));
        tvName.setText(food.getName());
        tvAddress.setText(address);
        tvPrice.setText(getRoundPrice(card.getPrice()));
    }

    private String getRoundPrice(Double price){
        return Math.round(price) + " VNĐ";
    }
}
