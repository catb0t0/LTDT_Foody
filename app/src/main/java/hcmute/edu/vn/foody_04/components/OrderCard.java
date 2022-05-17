package hcmute.edu.vn.foody_04.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.MainActivity;
import hcmute.edu.vn.foody_04.R;

@SuppressLint("ViewConstructor")
public class OrderCard extends LinearLayout {
    private final Order order;

    public OrderCard(Context context, Order order) {
        super(context);
        this.order = order;
        initControl(context);
    }

    private void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.order_card, this);

        TextView tvDate = findViewById(R.id.tvDateMakeOrder);
        TextView tvPrice = findViewById(R.id.tvOrderPrice);
        TextView tvAddress = findViewById(R.id.tvOrderAddress);
        TextView tvStatus = findViewById(R.id.tvOrderStatus);

        Button btnConfirm = findViewById(R.id.btnConfirmOrder);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus("Delivered");
                MainActivity.dao.updateOrder(order);
                Toast.makeText(context, "Đã cập nhật lại trạng thái!", Toast.LENGTH_SHORT).show();
            }
        });
        if(order.getStatus().equals("Delivered")){
            btnConfirm.setEnabled(false);
        }
        if(order.getStatus().equals("Canceled")){
            btnConfirm.setText("ĐÃ HỦY ĐƠN");
            btnConfirm.setEnabled(false);
        }

        tvDate.setText(order.getDateOfOrder());
        tvAddress.setText(order.getAddress());
        tvPrice.setText(getRoundPrice(order.getTotalValue()));
        tvStatus.setText(order.getStatus());
    }

    private String getRoundPrice(Double price){
        return Math.round(price) + " VNĐ";
    }
}
