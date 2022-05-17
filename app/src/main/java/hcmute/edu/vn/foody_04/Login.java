package hcmute.edu.vn.foody_04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;

public class Login extends AppCompatActivity {
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dao = new DAO(this);

        Button btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        TextView txtViewDK = (TextView) findViewById(R.id.txtViewDangKy);
        EditText txtPassword = findViewById(R.id.editTextPass);
        EditText txtUsername = findViewById(R.id.editTextUsername);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {

                    Toast.makeText(Login.this, "Thiếu thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                User userExist = dao.getUserByUsernameAndPassword(username, password);

                boolean isRightAuthentication = false;
                if (userExist != null) {
                    isRightAuthentication = dao.signIn(userExist);
                }
                if (isRightAuthentication) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("USERNAME",username);
                    extras.putString("PASSWORD",password);
                    intent.putExtras(extras);
                    Login.this.startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtViewDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, SignUp.class);
                Login.this.startActivity(myIntent);
            }
        });
    }
}