package hcmute.edu.vn.foody_04;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.Beans.OrderDetail;
import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;
import hcmute.edu.vn.foody_04.components.CartCard;

public class ViewOrderActivity extends AppCompatActivity {

    private LinearLayout layout_container;
    private TextView textViewDate, textViewPrice, textViewAddress, textViewStatus;
    private DAO dao;
    private Order order;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        dao = new DAO(this);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");

        referencesComponent();
        LoadData();
    }

    public void referencesComponent(){
        layout_container = findViewById(R.id.layout_container_order_detail);

        textViewDate = findViewById(R.id.tvDateMakeOrderView);
        textViewPrice = findViewById(R.id.tvOrderPriceView);
        textViewAddress = findViewById(R.id.tvOrderAddressView);
        textViewStatus = findViewById(R.id.tvOrderStatusView);

        Button btnDeleteOrder = findViewById(R.id.btnDeleteOrder);
        if(order.getStatus().equals("Delivered") || order.getStatus().equals("Canceled")){
            btnDeleteOrder.setEnabled(false);
        }
        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {


                                                  AlertDialog.Builder dialog = new AlertDialog.Builder(ViewOrderActivity.this);
                                                  dialog.setMessage("Bạn có muốn xóa món đơn hàng này không?");
                                                  dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                                                      order.setStatus("Canceled");
                                                      dao.updateOrder(order);
                                                      Toast.makeText(ViewOrderActivity.this, "Đơn hàng đã bị hủy!", Toast.LENGTH_SHORT).show();

                                                      finish();
                                                  });
                                                  dialog.setNegativeButton("Không", (dialogInterface, i) -> {
                                                  });
                                                  dialog.show();
                                              }
        });

        Button btnCancel = findViewById(R.id.btnCancelOrderView);
        btnCancel.setOnClickListener(view -> finish());
    }

    private void LoadData(){
        textViewDate.setText(String.format("Ngày đặt hàng: %s", order.getDateOfOrder()));
        textViewAddress.setText(String.format("Địa chỉ: %s", order.getAddress()));
        textViewPrice.setText(String.format("Tổng giá trị: %s", getRoundPrice(order.getTotalValue())));
        textViewStatus.setText(String.format("Trạng thái giao hàng: %s",order.getStatus()));

        ArrayList<OrderDetail> orderDetailArrayList = dao.getCartDetailList(order.getId());
        if(orderDetailArrayList.size() > 0){
            Food food;
            Restaurant restaurant;
            for(OrderDetail orderDetail : orderDetailArrayList){
                food = dao.getFoodById(orderDetail.getFoodId());
                restaurant = dao.getRestaurantInformation(food.getRestaurantId());
                CartCard card = new CartCard(this, food, restaurant.getAddress(), orderDetail, false);
                layout_container.addView(card);
            }
        }

    }

    private String getRoundPrice(Double price){
        return Math.round(price) + " VNĐ";
    }
}