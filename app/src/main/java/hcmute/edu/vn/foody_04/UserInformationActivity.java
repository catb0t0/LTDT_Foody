package hcmute.edu.vn.foody_04;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;

public class UserInformationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edUser_name, edUser_phone, edUser_DoB, edUser_password;
    private String newUser_name, newUser_phone, newUser_DoB, newUser_gender, newUser_password;
    private Button btnChange, btnCancel;
    private Spinner spUser_gender;
    private Calendar calendar;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        referencesComponent();
        edUser_name.setText(MainActivity.user.getName());
        edUser_phone.setText(MainActivity.user.getPhone());
        edUser_DoB.setText(MainActivity.user.getDateOfBirth());
        switch (MainActivity.user.getGender()) {
            case "Male":
                break;
            case "Female":
                break;
            default:
                break;
        }

        dao = new DAO(this);
    }

    private void referencesComponent() {
        edUser_name = findViewById(R.id.editText_user_name);
        edUser_phone = findViewById(R.id.editText_user_phone);
        edUser_DoB = findViewById(R.id.user_birthday_pick);
        edUser_DoB.setOnClickListener(view -> PickDate());

        spUser_gender = findViewById(R.id.spinner_user_gender);
        ArrayAdapter<CharSequence> genders = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_dropdown_item);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUser_gender.setAdapter(genders);
        spUser_gender.setOnItemSelectedListener(this);

        btnChange = findViewById(R.id.btnChangeUserInformation);
        btnChange.setOnClickListener(view -> {
            newUser_name = edUser_name.getText().toString().trim();
            newUser_gender = spUser_gender.getSelectedItem().toString();
            newUser_phone = edUser_phone.getText().toString().trim();
            newUser_DoB = edUser_DoB.getText().toString();
            newUser_password = MainActivity.user.getPassword();

            if (newUser_name.isEmpty() || newUser_phone.isEmpty() || newUser_DoB.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                String oldPassword = MainActivity.user.getPassword();
                User editUser = new User(MainActivity.user.getId(),
                        newUser_name, newUser_gender, newUser_DoB, newUser_phone,
                        MainActivity.user.getUsername(),
                        newUser_password);
                dao.updateUser(editUser);

                MainActivity.user = editUser;
                finish();
            }
        });

        btnCancel = findViewById(R.id.btnCancelChangeUserInformation);
        btnCancel.setOnClickListener(view -> finish());
    }

    private void PickDate() {
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            edUser_DoB.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spUser_gender.setTextAlignment(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}