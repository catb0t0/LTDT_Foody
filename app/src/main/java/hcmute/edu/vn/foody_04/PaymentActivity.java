package hcmute.edu.vn.foody_04;

import static hcmute.edu.vn.foody_04.MainActivity.dao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.Beans.OrderDetail;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.fragment.ShippingFragment;

public class PaymentActivity extends AppCompatActivity {
    private String name, phone, address, dateOfOrder;
    private static double sum;
    public static User user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        intent = getIntent();

        TextView tvUser_name = findViewById(R.id.editText_payment_name);
        TextView tvUserPhone = findViewById(R.id.editText_payment_phone);
        TextView tvUserAddress = findViewById(R.id.editText_payment_address);
        TextView tvTotalValue = findViewById(R.id.editText_payment_MoneySum);

        tvUser_name.setText(MainActivity.user.getName());
        tvUserPhone.setText(MainActivity.user.getPhone());

        // get order
        Integer orderId = intent.getIntExtra("orderId", 0);
        ArrayList<OrderDetail> orderDetailArrayList = dao.getCartDetailList(orderId);
        sum = 0;
        for(OrderDetail orderDetail : orderDetailArrayList){
            sum += orderDetail.getPrice();
        }
        tvTotalValue.setText(String.format("%s VNĐ", sum));

        Button btnThanhToan = findViewById(R.id.btnThanhToanThanhToan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = tvUser_name.getText().toString();
                phone = tvUserPhone.getText().toString();
                address = tvUserAddress.getText().toString();
                if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dateOfOrder = dao.getDate();

                Order order = new Order(orderId, user.getId(), address, dateOfOrder, sum, "Moving");
                dao.updateOrder(order);

                Toast.makeText(PaymentActivity.this, "Đã thanh toán thành công!", Toast.LENGTH_SHORT).show();
                ShippingFragment.cartContainer.removeAllViews();

                finish();
            }
        });

        Button btnCancel = findViewById(R.id.btnThanhToanHuyBo);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}