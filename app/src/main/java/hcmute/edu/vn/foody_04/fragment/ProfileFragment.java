package hcmute.edu.vn.foody_04.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import hcmute.edu.vn.foody_04.MainActivity;
import hcmute.edu.vn.foody_04.R;
import hcmute.edu.vn.foody_04.UserInformationActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View mainView;
    private Intent intent;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        referenceComponent();
        // Inflate the layout for this fragment
        return mainView;
    }

    private void referenceComponent(){
        // Components
        LinearLayout user_information = mainView.findViewById(R.id.layout_user_information);
        user_information.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    startActivity(new Intent(getActivity(), UserInformationActivity.class));
                                                }
                                            });

        LinearLayout payment = mainView.findViewById(R.id.account_btn_layout_payment);
        payment.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           intent = new Intent(getActivity(), MainActivity.class);
                                           intent.putExtra("request", "payment");
                                           startActivity(intent);
                                       }
        });

        LinearLayout history = mainView.findViewById(R.id.account_btn_layout_history);
        history.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           intent = new Intent(getActivity(), MainActivity.class);
                                           intent.putExtra("request", "history");
                                           startActivity(intent);
                                       }
        });

        LinearLayout check = mainView.findViewById(R.id.account_btn_layout_check);
        check.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         intent = new Intent(getActivity(), MainActivity.class);
                                         intent.putExtra("request", "check");
                                         startActivity(intent);
                                     }
        });

        LinearLayout hint = mainView.findViewById(R.id.account_btn_layout_hint);
        hint.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra("request", "hint");
                                        startActivity(intent);
                                    }
        });

//        LinearLayout policy = mainView.findViewById(R.id.account_btn_layout_policy);

        LinearLayout logout = mainView.findViewById(R.id.account_btn_layout_logout);
        logout.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Toast.makeText(getActivity(),
                                                  "???? ????ng xu???t kh???i h??? th???ng!",
                                                  Toast.LENGTH_SHORT).show();
                                          requireActivity().finish();
                                      }
        });

        TextView txtUser_name = mainView.findViewById(R.id.account_user_name);
        txtUser_name.setText(MainActivity.user.getName());
    }
}