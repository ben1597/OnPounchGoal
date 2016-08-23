package com.example.mimi.onpounchgoal.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mimi.onpounchgoal.MainActivity;
import com.example.mimi.onpounchgoal.R;
import com.example.mimi.onpounchgoal.data.Item;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class AddItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Spinner sp_goal_name;
    private EditText ed_goal_value;
    private TextView tv_goal_unit;
    private Button btn_submit;

    public AddItemFragment() {
        // Required empty public constructor
    }

    public static AddItemFragment newInstance() {
        AddItemFragment fragment = new AddItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sp_goal_name = (Spinner) getView().findViewById(R.id.sp_goal_name);

        ed_goal_value = (EditText) getView().findViewById(R.id.et_goal_num);
        tv_goal_unit = (TextView) getView().findViewById(R.id.tv_goal_unit);
        btn_submit = (Button) getView().findViewById(R.id.btn_submit);


        sp_goal_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getActivity(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                if(parent.getItemAtPosition(position).toString().equals("慢跑")){
                    tv_goal_unit.setText("公里");
                }
                else{
                    tv_goal_unit.setText("下");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(sp_goal_name.getSelectedItem().toString(),ed_goal_value.getText().toString(),
                        tv_goal_unit.getText().toString(),new GregorianCalendar().getTime().toString(),0);

//                item.setId(MainActivity.itemId++);
                String result = MainActivity.myDB.insert(item);
                if (result == "Success") {
                    Toast.makeText(getActivity(), "Insert Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                }
                ((MainActivity)getActivity()).goHomePage();
            }
        });

        String[] spinnerItems = new String[]{"伏地挺身","仰臥起坐","深蹲","慢跑"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,spinnerItems);
        sp_goal_name.setAdapter(adapter);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
