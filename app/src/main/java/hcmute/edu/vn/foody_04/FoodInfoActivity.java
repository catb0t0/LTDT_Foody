package hcmute.edu.vn.foody_04;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.FoodSize;
import hcmute.edu.vn.foody_04.Beans.Order;
import hcmute.edu.vn.foody_04.Beans.OrderDetail;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;
import hcmute.edu.vn.foody_04.database.DbHandler;

public class FoodInfoActivity extends AppCompatActivity {

    private ImageView image;
    private LinearLayout layout_sizeS, layout_sizeM, layout_sizeL;
    private TextView tvName, tvDescription, tvPrice,
            tvRestaurantName, tvRestaurantAddress,
            tvPriceSizeS,tvPriceSizeM, tvPriceSizeL;
    private Button btnAddtoCart;
    private FoodSize foodSize;
    private DAO dao;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        referenceComponent();

        Intent intent = getIntent();
        dao = new DAO(this);

        if(intent != null){
            Bundle bundle = intent.getBundleExtra("foodDetail");

            Food food = (Food) bundle.getSerializable("food");
            //Restaurant restaurant = (Restaurant) bundle.getSerializable("restaurant");
            FoodSize foodSizeS = (FoodSize) bundle.getSerializable("foodSizeS");
            FoodSize foodSizeM = (FoodSize) bundle.getSerializable("foodSizeM");
            FoodSize foodSizeL = (FoodSize) bundle.getSerializable("foodSizeL");

            if(food!= null){
                tvName.setText(food.getName());
                tvDescription.setText(food.getDescription());
                image.setImageBitmap(DbHandler.convertByteArrayToBitmap(food.getImage()));

                if(foodSizeS != null){
                    tvPriceSizeS.setText(getRoundPrice(foodSizeS.getPrice()));
                    layout_sizeS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvPrice.setText(tvPriceSizeS.getText());
                        }
                    });
                } else {
                    layout_sizeS.setVisibility(View.INVISIBLE);
                }

                if(foodSizeM != null){
                    tvPriceSizeM.setText(getRoundPrice(foodSizeM.getPrice()));
                    layout_sizeM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvPrice.setText(tvPriceSizeM.getText());
                        }
                    });
                } else {
                    layout_sizeM.setVisibility(View.INVISIBLE);
                }

                if(foodSizeL != null){
                    tvPriceSizeL.setText(getRoundPrice(foodSizeL.getPrice()));
                    layout_sizeL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvPrice.setText(tvPriceSizeL.getText());
                        }
                    });
                } else {
                    layout_sizeL.setVisibility(View.INVISIBLE);
                }

                btnAddtoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Make cart if don't have
                        Cursor cursor = dao.getCart(/*user.getId()*/1);
                        if (!cursor.moveToFirst()){
                            dao.addOrder(new Order(1, /*user.getId()*/1, "", "", 0d, "Craft"));
                            cursor = dao.getCart(/*user.getId()*/1);
                        }//này hết bug

                        // add order detail
                        cursor.moveToFirst();
                        foodSize=new FoodSize(1,1,9000.0);//
                        dao.addOrderDetail(new OrderDetail(cursor.getInt(0),
                                foodSize.getFoodId(), foodSize.getSize(), foodSize.getPrice()));

                        Toast.makeText(FoodInfoActivity.this, "Thêm món ăn vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
                //tvRestaurantName.setText(String.format("Tên cửa hàng \n%s", restaurant.getName()));
                //tvRestaurantAddress.setText(String.format("Địa chỉ\n%s", restaurant.getAddress()));

                tvRestaurantName.setText("000SSS");
                tvRestaurantAddress.setText("restaurant.getAddress())");

                Double defaultPrice = bundle.getDouble("defaultPrice");
                tvPrice.setText(getRoundPrice(defaultPrice));
            }
        }
    }

    private String getRoundPrice(Double price){
        return Math.round(price) + " VNĐ";
    }

    private void referenceComponent(){
        ImageView btnBack = findViewById(R.id.imgViewMenu_Back);
        btnBack.setOnClickListener(view -> this.finish());

        tvName = findViewById(R.id.txtViewFoodName);
        tvDescription = findViewById(R.id.txtViewDesciption);
        tvPrice = findViewById(R.id.tvPrice);
        image = findViewById(R.id.imgViewFoodInfo);

        layout_sizeS = findViewById(R.id.layout_size_S);
        layout_sizeM = findViewById(R.id.layout_size_M);
        layout_sizeL = findViewById(R.id.layout_size_L);

        tvPriceSizeS = findViewById(R.id.tvPriceSizeS);
        tvPriceSizeM = findViewById(R.id.tvPriceSizeM);
        tvPriceSizeL = findViewById(R.id.tvPriceSizeL);

        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvRestaurantAddress = findViewById(R.id.tvRestaurantAddress);

        btnAddtoCart = findViewById(R.id.btnAddToCart);
    }
}