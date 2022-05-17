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
import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;
import hcmute.edu.vn.foody_04.database.DbHandler;

public class FoodInfoActivity extends AppCompatActivity {

    private ImageView image;
    private LinearLayout layoutSizeS, layoutSizeM, layoutSizeL;
    private TextView textViewName, textViewDescription, textViewtvPrice,
            textViewRestaurantName, textViewRestaurantAddress,
            textViewPriceSizeS, textViewPriceSizeM, textViewPriceSizeL;
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
            foodSize = new FoodSize();
            Restaurant restaurant = (Restaurant) bundle.getSerializable("restaurant");
            FoodSize foodSizeS = (FoodSize) bundle.getSerializable("foodSizeS");
            FoodSize foodSizeM = (FoodSize) bundle.getSerializable("foodSizeM");
            FoodSize foodSizeL = (FoodSize) bundle.getSerializable("foodSizeL");

            if(food!= null){
                textViewName.setText(food.getName());
                textViewDescription.setText(food.getDescription());
                image.setImageBitmap(DbHandler.convertByteArrayToBitmap(food.getImage()));

                if(foodSizeS != null){
                    textViewPriceSizeS.setText(getRoundPrice(foodSizeS.getPrice()));
                    layoutSizeS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewtvPrice.setText(textViewPriceSizeS.getText());
                            foodSize = foodSizeS;
                        }
                    });
                } else {
                    layoutSizeS.setVisibility(View.INVISIBLE);
                }

                if(foodSizeM != null){
                    textViewPriceSizeM.setText(getRoundPrice(foodSizeM.getPrice()));
                    layoutSizeM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewtvPrice.setText(textViewPriceSizeM.getText());
                            foodSize = foodSizeM;
                        }
                    });
                } else {
                    layoutSizeM.setVisibility(View.INVISIBLE);
                }

                if(foodSizeL != null){
                    textViewPriceSizeL.setText(getRoundPrice(foodSizeL.getPrice()));
                    layoutSizeL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewtvPrice.setText(textViewPriceSizeL.getText());
                            foodSize = foodSizeL;
                        }
                    });
                } else {
                    layoutSizeL.setVisibility(View.INVISIBLE);
                }

                btnAddtoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Make cart if don't have

                        if(foodSize == foodSizeL || foodSize == foodSizeS || foodSize == foodSizeM) {
                            //Cursor cursor = dao.getCart(/*user.getId()*/1);
                            Cursor cursor = dao.getCart(MainActivity.user.getId());
                            if (!cursor.moveToFirst()) {
//                            dao.addOrder(new Order(1, user.getId(), "", "", 0d, "Craft"));
//                            cursor = dao.getCart(user.getId());
                                dao.addOrder(new Order(1, MainActivity.user.getId(), "", "", 0d, "Craft"));
                                cursor = dao.getCart(/*user.getId()*/1);
                            }

                            // add order detail
                            cursor.moveToFirst();
                            dao.addOrderDetail(new OrderDetail(cursor.getInt(0),
                                    foodSize.getFoodId(), foodSize.getSize(), foodSize.getPrice()));

                            Toast.makeText(FoodInfoActivity.this, "Thêm món ăn vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(FoodInfoActivity.this, "Vui lòng chọn size", Toast.LENGTH_SHORT).show();
                    }
                });
                textViewRestaurantName.setText(String.format("Tên cửa hàng \n%s", restaurant.getName()));
                textViewRestaurantAddress.setText(String.format("Địa chỉ\n%s", restaurant.getAddress()));

                //tvRestaurantName.setText("000SSS");
                //tvRestaurantAddress.setText("restaurant.getAddress())");

                Double defaultPrice = bundle.getDouble("defaultPrice");
                textViewtvPrice.setText(getRoundPrice(defaultPrice));
            }
        }
    }

    private String getRoundPrice(Double price){
        return Math.round(price) + " VNĐ";
    }

    private void referenceComponent(){
        ImageView btnBack = findViewById(R.id.imgViewMenu_Back);
        btnBack.setOnClickListener(view -> this.finish());

        textViewName = findViewById(R.id.txtViewFoodName);
        textViewDescription = findViewById(R.id.txtViewDesciption);
        textViewtvPrice = findViewById(R.id.tvPrice);
        image = findViewById(R.id.imgViewFoodInfo);

        layoutSizeS = findViewById(R.id.layout_size_S);
        layoutSizeM = findViewById(R.id.layout_size_M);
        layoutSizeL = findViewById(R.id.layout_size_L);

        textViewPriceSizeS = findViewById(R.id.tvPriceSizeS);
        textViewPriceSizeM = findViewById(R.id.tvPriceSizeM);
        textViewPriceSizeL = findViewById(R.id.tvPriceSizeL);

        textViewRestaurantName = findViewById(R.id.tvRestaurantName);
        textViewRestaurantAddress = findViewById(R.id.tvRestaurantAddress);

        btnAddtoCart = findViewById(R.id.btnAddToCart);
    }
}