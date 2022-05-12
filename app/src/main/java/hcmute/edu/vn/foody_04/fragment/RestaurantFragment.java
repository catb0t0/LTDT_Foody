package hcmute.edu.vn.foody_04.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import hcmute.edu.vn.foody_04.Beans.Restaurant;
import hcmute.edu.vn.foody_04.DAO.DAO;
import hcmute.edu.vn.foody_04.R;
import hcmute.edu.vn.foody_04.components.RestaurantCard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {
    private LinearLayout foodCartContainer;
    private View mainView;
    private DAO dao;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
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
        if(mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_restaurant, container, false);

            ImageView changePage = mainView.findViewById(R.id.imgViewResturantMenu_Main);
            changePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeFragment homeFragment= new HomeFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, homeFragment, "Food")
                            .addToBackStack(null)
                            .commit();
                }
            });

            foodCartContainer = (LinearLayout) mainView.findViewById(R.id.categoryFoodCartContainer);
            getRestaurantData();
        }
        return mainView;
    }

    private void getRestaurantData() {
        try {
            foodCartContainer.removeAllViews();
        }
        catch (Exception e){}
        // Add food cart to layout container

            dao = new DAO(mainView.getContext());
        ArrayList<Restaurant> restaurantArrayList;

        restaurantArrayList = dao.getAllRestaurant();

        for (Restaurant restaurant : restaurantArrayList) {
            RestaurantCard restaurantCard = new RestaurantCard(mainView.getContext(), restaurant);
            //FoodCard foodCard = new FoodCard(this, food, foodSize.getPrice(), "restaurant.getName()");

//            foodCard.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(CategoryActivity.this, FoodInfoActivity.class);
//                    ArrayList<FoodSize> foodSizeArrayList = dao.getAllFoodSize(food.getId());
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("food", food);
//                    bundle.putSerializable("restaurant", restaurant);
//                    bundle.putSerializable("foodSizeS", foodSizeArrayList.get(0));
//
//                    if (foodSizeArrayList.size() < 3) {
//                        bundle.putSerializable("foodSizeM", foodSizeArrayList.get(1));
//                    }
//                    if (foodSizeArrayList.size() == 3) {
//                        bundle.putSerializable("foodSizeM", foodSizeArrayList.get(1));
//                        bundle.putSerializable("foodSizeL", foodSizeArrayList.get(2));
//                    }
//
//                    bundle.putSerializable("defaultFoodSize", foodSize);
//
//                    intent.putExtra("foodDetail", bundle);
//                    try {
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        Toast.makeText(CategoryActivity.this, "Không thể hiển thị thông tin!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            foodCartContainer.addView(restaurantCard);
        }
    }
}