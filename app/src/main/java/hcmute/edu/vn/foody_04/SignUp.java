package hcmute.edu.vn.foody_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;

public class SignUp extends AppCompatActivity {
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dao = new DAO(this);

        TextView txtDangKy = findViewById(R.id.btnRegDangKy);
        TextView txtDangNhap = findViewById(R.id.txtViewRegDangNhap);

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.editTextRegisUsername)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.editTextRegisPass)).getText().toString().trim();
                String password2 = ((EditText) findViewById(R.id.editTextRegisPass2)).getText().toString().trim();
                if(username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(SignUp.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password2.equals(password)){
                    Toast.makeText(SignUp.this, "Mật khẩu xác nhận không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(dao.UserExited(username)){
                    Toast.makeText(SignUp.this, "Người dùng đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.addUser(new User(null, "", "Male", "1/1/2000", "", username, password));
                    Toast.makeText(SignUp.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    setResult(0, intent);
                    finish();
                }
            }
        });

        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}