package hcmute.edu.vn.foody_04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import hcmute.edu.vn.foody_04.Beans.Order;

public class PaymentActivity extends AppCompatActivity {
    private String name, phone, address, dateOfOrder;
    private Calendar calendar;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        referencesComponents();
    }

    private void referencesComponents(){
        TextView tvUser_name = findViewById(R.id.editText_payment_name);
        TextView tvUserPhone = findViewById(R.id.editText_payment_phone);
        TextView tvUserAddress = findViewById(R.id.editText_payment_address);

        tvUser_name.setText(MainActivity.user.getName());
        tvUserPhone.setText(MainActivity.user.getPhone());

        Button btnThanhToan = findViewById(R.id.btnThanhToanThanhToan);
        btnThanhToan.setOnClickListener(view ->
        {
            name = tvUser_name.getText().toString();
            phone = tvUserPhone.getText().toString();
            address = tvUserAddress.getText().toString();
            if(name.isEmpty() || phone.isEmpty() || address.isEmpty()){
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
            dateOfOrder = day + "/" + month + "/" + year;

            Order order = new Order(1, MainActivity.user.getId(), address, dateOfOrder,  0d, "Prepared");
            startActivity(new Intent(PaymentActivity.this, MainActivity.class));
        });
    }
}