package hcmute.edu.vn.foody_04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody_04.Beans.Food;
import hcmute.edu.vn.foody_04.Beans.FoodSize;
import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.Beans.User;
import hcmute.edu.vn.foody_04.DAO.DAO;
import hcmute.edu.vn.foody_04.components.FoodCard;

public class CategoryActivity extends AppCompatActivity {
    private LinearLayout foodCartContainer;
    private DAO dao;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        foodCartContainer = findViewById(R.id.categoryFoodCartContainer);
        referencesComponents();
        dao = new DAO(this);
        getFoodData(null);

    }

    private void referencesComponents(){
        ImageView image = findViewById(R.id.imgView_CategoryCart);
        image.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
            intent.putExtra("request","cart");
            startActivity(intent);
        });

        SearchView searchBar = findViewById(R.id.CategorySearch);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String nameFood = searchBar.getQuery().toString();
                getFoodData(nameFood);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        foodCartContainer = findViewById(R.id.categoryFoodCartContainer);
    }

    private void getFoodData(String nameFood) {
        foodCartContainer.removeAllViews();
        // Add food cart to layout container
        Intent intent_get_data = getIntent();

        ArrayList<Food> foodArrayList;

        if (nameFood != null) {
            foodArrayList = dao.getFoodByKeyWord(nameFood);
        } else {
            String getFoodList = intent_get_data.getStringExtra("typeFood");
            if (getFoodList == null) {
                getFoodList = intent_get_data.getStringExtra("nameFood");
                foodArrayList = dao.getFoodByKeyWord(getFoodList);
            } else {
                foodArrayList = dao.getFoodByType(getFoodList);
            }
        }

        for (Food food : foodArrayList) {
            Restaurant restaurant = dao.getRestaurantInformation(food.getRestaurantId());
            FoodSize foodSize = dao.getFoodDefaultSize(food.getId());

            FoodCard foodCard = new FoodCard(this, food, foodSize.getPrice(), restaurant.getName());
            //FoodCard foodCard = new FoodCard(this, food, foodSize.getPrice(), "restaurant.getName()");

            foodCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CategoryActivity.this, FoodInfoActivity.class);
                    ArrayList<FoodSize> foodSizeArrayList = dao.getAllFoodSize(food.getId());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("food", food);
                    bundle.putSerializable("restaurant", restaurant);
                    bundle.putSerializable("foodSizeS", foodSizeArrayList.get(0));

                    if (foodSizeArrayList.size() < 3) {
                        bundle.putSerializable("foodSizeM", foodSizeArrayList.get(1));
                    }
                    if (foodSizeArrayList.size() == 3) {
                        bundle.putSerializable("foodSizeM", foodSizeArrayList.get(1));
                        bundle.putSerializable("foodSizeL", foodSizeArrayList.get(2));
                    }

                    bundle.putSerializable("defaultFoodSize", foodSize);

                    intent.putExtra("foodDetail", bundle);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(CategoryActivity.this, "Không thể hiển thị thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            foodCartContainer.addView(foodCard);
        }
    }
}