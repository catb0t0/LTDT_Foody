package hcmute.edu.vn.foody_04;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Field;

import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;
import hcmute.edu.vn.foody_04.fragment.ChatFragment;
import hcmute.edu.vn.foody_04.fragment.HomeFragment;
import hcmute.edu.vn.foody_04.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static int clickToLogout;
    private boolean Food = true;
    public static User user;
    private Fragment homeFragment, chatFragment, profileFragment;
    public static DAO dao;

    private void referenceFragment(){
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        chatFragment = new ChatFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        clickToLogout = 0;

        referenceFragment();
        dao = new DAO(this);

        Intent intent = getIntent();

        String username = intent.getStringExtra("USERNAME");
        String password = intent.getStringExtra("PASSWORD");

        user = dao.getUserByUsernameAndPassword(username, password);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);


        String request = intent.getStringExtra("request");
        if(request != null) {
            switch (request) {
                case "payment":
                case "history":
                case "check":
                case "cart":
                    loadFragment(chatFragment);
                    navigation.getMenu().getItem(2).setChecked(true);
                    break;
                default:
                    loadFragment(homeFragment);
                    break;
            }
        } else {
            loadFragment(homeFragment);
        }
    }

    @Override
    public void onBackPressed() {
        clickToLogout++;

        if(clickToLogout > 1)
            finish();
        else {
            Toast.makeText(this, "Click thêm lần nữa để đăng xuất!", Toast.LENGTH_SHORT).show();
        }

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                clickToLogout = 0;
            }
        }.start();
    }

    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                loadFragment(homeFragment);
                return true;
            case R.id.navigation_chat:
                loadFragment(chatFragment);
                return true;
            case R.id.navigation_profile:
                loadFragment(profileFragment);
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();

    }
}