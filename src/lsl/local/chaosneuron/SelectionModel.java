package lsl.local.chaosneuron;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.lang.Math;


public class SelectionModel extends Fragment
  implements View.OnClickListener
{
  private final static String TAG    = "SelectionModel";
  private final static boolean DEBUG = true;

  private Context mContext = null;
  private View rootView    = null;
  private Button btn_logistic, btn_chaos;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.selection, container, false);
    this.mContext = getActivity();
    findViews();
    return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void findViews() {
    btn_logistic = (Button)rootView.findViewById(R.id.btn_logistic);
    btn_chaos    = (Button)rootView.findViewById(R.id.btn_chaos_neuron);

    btn_logistic.setOnClickListener(this);
    btn_chaos.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;

    switch(v.getId()) {
      case R.id.btn_logistic:
        intent = new Intent(this.mContext, LogisticMapActivity.class);
        break;
      case R.id.btn_chaos_neuron:
        intent = new Intent(this.mContext, NeuronModel.class);
        break;
    }

    if(intent != null)
      this.mContext.startActivity(intent);
  }
}
