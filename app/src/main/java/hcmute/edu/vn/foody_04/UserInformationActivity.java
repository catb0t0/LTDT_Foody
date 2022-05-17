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

    private EditText editTextUsername, editTextUserphone, editTextUserDoB;
    private String newUserName, newUserPhone, newUserDoB, newUserGender, newUserPassword;
    private Button btnChange, btnCancel;
    private Spinner spinnerUserGender;
    private Calendar calendar;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        referencesComponent();
        editTextUsername.setText(MainActivity.user.getName());
        editTextUserphone.setText(MainActivity.user.getPhone());
        editTextUserDoB.setText(MainActivity.user.getDateOfBirth());
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
        editTextUsername = findViewById(R.id.editText_user_name);
        editTextUserphone = findViewById(R.id.editText_user_phone);
        editTextUserDoB = findViewById(R.id.user_birthday_pick);
        editTextUserDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDate();
            }
        });

        spinnerUserGender = findViewById(R.id.spinner_user_gender);
        ArrayAdapter<CharSequence> genders = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_dropdown_item);
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserGender.setAdapter(genders);
        spinnerUserGender.setOnItemSelectedListener(this);

        btnChange = findViewById(R.id.btnChangeUserInformation);
        btnChange.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             newUserName = editTextUsername.getText().toString().trim();
                                             newUserGender = spinnerUserGender.getSelectedItem().toString();
                                             newUserPhone = editTextUserphone.getText().toString().trim();
                                             newUserDoB = editTextUserDoB.getText().toString();
                                             newUserPassword = MainActivity.user.getPassword();

                                             if (newUserName.isEmpty() || newUserPhone.isEmpty() || newUserDoB.isEmpty()) {
                                                 Toast.makeText(UserInformationActivity.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                                             } else {
                                                 String oldPassword = MainActivity.user.getPassword();
                                                 User editUser = new User(MainActivity.user.getId(),
                                                         newUserName, newUserGender, newUserDoB, newUserPhone,
                                                         MainActivity.user.getUsername(),
                                                         newUserPassword);
                                                 dao.updateUser(editUser);

                                                 MainActivity.user = editUser;
                                                 finish();
                                             }
                                         }
        });

        btnCancel = findViewById(R.id.btnCancelChangeUserInformation);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void PickDate() {
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            editTextUserDoB.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerUserGender.setTextAlignment(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}