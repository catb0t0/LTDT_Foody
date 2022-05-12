package hcmute.edu.vn.foody_04.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.Beans.OrderDetail;
import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.MainActivity;
import hcmute.edu.vn.foody_04.PaymentActivity;
import hcmute.edu.vn.foody_04.R;
import hcmute.edu.vn.foody_04.ViewOrderActivity;
import hcmute.edu.vn.foody_04.components.CartCard;
import hcmute.edu.vn.foody_04.components.OrderCard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View mainView;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout cartContainer;
    private static String status;
    private LinearLayout btnDangDen, btnLichSu, btnGioHang;
    Button btnThanhToan;
    private TextView tvGioHang, tvDangDen, tvLichSu;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_chat, container, false);

        cartContainer = mainView.findViewById(R.id.cartContainer);

        btnGioHang = mainView.findViewById(R.id.btnGioHang);


        btnDangDen = mainView.findViewById(R.id.btnDangDen);


        btnLichSu = mainView.findViewById(R.id.btnLichSu);


        tvGioHang = mainView.findViewById(R.id.tvGioHang);
        tvDangDen = mainView.findViewById(R.id.tvDangDen);
        tvLichSu = mainView.findViewById(R.id.tvLichSu);

        btnThanhToan = mainView.findViewById(R.id.btnChatThanhToan);
        btnThanhToan.setOnClickListener(view ->{
            //startActivity(new Intent(getActivity(), PaymentActivity.class));
        });

        LoadOrder("craft");
        status = "craft";

        btnGioHang.setOnClickListener(view ->{
            resetAttribute();
            btnGioHang.setBackground(ContextCompat.getDrawable(getContext(),R.color.blue));
            tvGioHang.setTextColor(Color.WHITE);

            LoadOrder("craft");
        });

        btnDangDen.setOnClickListener(view->{
            resetAttribute();
            btnDangDen.setBackground(ContextCompat.getDrawable(getContext(),R.color.blue));
            tvDangDen.setTextColor(Color.WHITE);

            LoadOrder("moving");
        });

        btnLichSu.setOnClickListener(view -> {
            resetAttribute();
            btnLichSu.setBackground(ContextCompat.getDrawable(getContext(),R.color.blue));
            tvLichSu.setTextColor(Color.WHITE);

            LoadOrder("history");
        });
        tvGioHang = mainView.findViewById(R.id.tvGioHang);
        tvDangDen = mainView.findViewById(R.id.tvDangDen);
        tvLichSu = mainView.findViewById(R.id.tvLichSu);

        Button btnThanhToan = mainView.findViewById(R.id.btnChatThanhToan);
        btnThanhToan.setOnClickListener(view -> {
            if (!status.equals("craft"))
                return;

            Cursor cursor = MainActivity.dao.getCart(MainActivity.user.getId());
            if (!cursor.moveToFirst())
                return;

            PaymentActivity.user = MainActivity.user;
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra("orderId", cursor.getInt(0));
            startActivity(intent);
        });

        return mainView;
    }

    private void LoadOrder(String type){
        status = type;
        cartContainer.removeAllViews();

        switch (type) {
            case "craft":
                Cursor cursor = MainActivity.dao.getCart(MainActivity.user.getId());
                if (!cursor.moveToFirst())
                    return;
                cursor.moveToFirst();
                ArrayList<OrderDetail> orderDetailArrayList = MainActivity.dao.getCartDetailList(cursor.getInt(0));
                if (orderDetailArrayList.size() > 0) {
                    Food food;
                    Restaurant restaurant;
                    for (OrderDetail orderDetail : orderDetailArrayList) {
                        food = MainActivity.dao.getFoodById(orderDetail.getFoodId());
                        restaurant = MainActivity.dao.getRestaurantInformation(food.getRestaurantId());
                        CartCard card = new CartCard(getContext(), food, restaurant.getAddress(), orderDetail);
                        cartContainer.addView(card);
                    }
                }
                break;
            case "moving": {
                ArrayList<Order> orderArrayList = MainActivity.dao.getOrderOfUser(MainActivity.user.getId(), "Moving");
                if (orderArrayList.size() > 0) {
                    for (Order order : orderArrayList) {
                        OrderCard card = new OrderCard(getContext(), order);
                        card.setOnClickListener(view -> {
                            Intent intent = new Intent(getContext(), ViewOrderActivity.class);
                            intent.putExtra("order", order);
                            startActivity(intent);
                        });
                        cartContainer.addView(card);
                    }
                }
                break;
            }
            case "history": {
                ArrayList<Order> orderArrayList = MainActivity.dao.getOrderOfUser(MainActivity.user.getId(), "Delivered");
                if (orderArrayList.size() > 0) {
                    for (Order order : orderArrayList) {
                        OrderCard card = new OrderCard(getContext(), order);
                        card.setOnClickListener(view -> {
                            Intent intent = new Intent(getContext(), ViewOrderActivity.class);
                            intent.putExtra("order", order);
                            startActivity(intent);
                        });
                        cartContainer.addView(card);
                    }
                }
                break;
            }
        }
    }

    private void resetAttribute(){
        btnGioHang.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_white));
        btnDangDen.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_white));
        btnLichSu.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_white));
        tvGioHang.setTextColor(Color.BLACK);
        tvLichSu.setTextColor(Color.BLACK);
        tvDangDen.setTextColor(Color.BLACK);
    }
}