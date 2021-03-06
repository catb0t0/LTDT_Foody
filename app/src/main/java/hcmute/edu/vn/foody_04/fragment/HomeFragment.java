package hcmute.edu.vn.foody_04.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import hcmute.edu.vn.foody_04.CategoryActivity;
import hcmute.edu.vn.foody_04.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Intent intent;
    private View mainView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mainView == null){
            mainView = inflater.inflate(R.layout.fragment_home, container, false);

            ImageView layoutHamburger = mainView.findViewById(R.id.layoutHamburger);
            layoutHamburger.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       intent = new Intent(getActivity(), CategoryActivity.class);
                                                       intent.putExtra("typeFood", "Hamburger");
                                                       startActivity(intent);
                                                   }
            });

            ImageView layoutCake = mainView.findViewById(R.id.layoutTeamilk);
            layoutCake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("typeFood", "Tr?? s???a");
                    startActivity(intent);
                }
            });

            ImageView layoutRice = mainView.findViewById(R.id.layoutRice);
            layoutRice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("typeFood", "C??m s?????n");
                    startActivity(intent);
                }
            });

            ImageView layoutMilkTea = mainView.findViewById(R.id.layoutCake);
            layoutMilkTea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("typeFood", "B??nh ng???t");
                    startActivity(intent);
                }
            });

            ImageView layoutIceCream = mainView.findViewById(R.id.layoutIcecream);
            layoutIceCream.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("typeFood", "Kem");
                    startActivity(intent);
                }
            });

            ImageView layoutWaterFood = mainView.findViewById(R.id.layoutWater);
            layoutWaterFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("typeFood", "M??n n?????c");
                    startActivity(intent);
                }
            });


            ImageView changePage = mainView.findViewById(R.id.imgViewHomeMenu_Main);
            changePage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RestaurantFragment restaurantFragment= new RestaurantFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, restaurantFragment, "Restaurant")
                            .addToBackStack(null)
                            .commit();
                }
            });
            //Restaurant
//            ImageView imageCart = mainView.findViewById(R.id.imgViewMenu_Main);
//            imageCart.setOnClickListener(view1 -> {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("request", "cart");
//                startActivity(intent);
//            });

            SearchView searchBar = mainView.findViewById(R.id.search_bar);
            searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    String textSearch = searchBar.getQuery().toString();
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("nameFood", textSearch);
                    startActivity(intent);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

        return mainView;
    }
}