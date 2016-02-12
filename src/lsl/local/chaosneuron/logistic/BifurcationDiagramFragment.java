package lsl.local.chaosneuron.logistic;

import lsl.local.chaosneuron.graphics.*;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.Integer;
import java.lang.Float;
import java.util.ArrayList;


public class BifurcationDiagramFragment extends Fragment
  implements View.OnClickListener {
  private final static String TAG    = "BifurcationDiagramFragment";
  private final static boolean DEBUG = false;


  private View rootView              = null;
  private EditText etNumYear         = null;
  private EditText etInitValue       = null;
  private EditText etStartBirthRage  = null;
  private Button btnRun              = null;
  private TwoDimGraph graphReturnMap = null;

  private int   num_years        = 100;
  private float initial_value    = 0.5f;
  private float start_birth_rate = 0.0f;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(
        lsl.local.chaosneuron.R.layout.logistic_diagram, container, false);

    findViews();
    return rootView;
  }

  private void findViews() {
    graphReturnMap = (TwoDimGraph)rootView.findViewById(
        lsl.local.chaosneuron.R.id.logistic_graph_diagram);
    btnRun         = (Button)rootView.findViewById(
        lsl.local.chaosneuron.R.id.btn_run_logistic_diagram);

    etNumYear        = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_num_years);
    etInitValue      = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_init_value);
    etStartBirthRage = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_start_birth_rate);

    btnRun.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    ArrayList<Points> dataset = null;
    switch(v.getId()) {
      case lsl.local.chaosneuron.R.id.btn_run_logistic_diagram:
        setValues();
        dataset = Logistic.BifurcationDiagram(num_years, initial_value, start_birth_rate, 4.0f);
        break;
    }

    if(dataset != null)
      graphReturnMap.setData(dataset, false);
    dataset = null;
  }

  private void setValues() {
    String tmpString = null;

    tmpString = etNumYear.getText().toString();
    if(tmpString.length() > 0) {
      num_years = Integer.parseInt(tmpString);
      num_years = (num_years > 0 && num_years < 1001) ? num_years : 100;
    } else {
      num_years = 100;
      etNumYear.setText("" + num_years);
    }
    if(DEBUG)
      Toast.makeText(getActivity(), "Number of Years: " + num_years, Toast.LENGTH_SHORT).show();

    tmpString = etInitValue.getText().toString();
    if(tmpString.length() > 0) {
      initial_value = Float.valueOf(tmpString);
      initial_value = (initial_value > 0.0f && initial_value <= 1.0f) ? initial_value : 0.5f;
    } else {
      initial_value = 0.5f;
      etInitValue.setText("" + initial_value);
    }
    if(DEBUG)
      Toast.makeText(getActivity(), "Initial Value: " + initial_value, Toast.LENGTH_SHORT).show();

    tmpString = etStartBirthRage.getText().toString();
    if(tmpString.length() > 0) {
      start_birth_rate = Float.valueOf(tmpString);
      start_birth_rate = (start_birth_rate >= -2.0f && initial_value < 4.0f) ? start_birth_rate : 0.0f;
    } else {
      etStartBirthRage.setText("" + start_birth_rate);
      start_birth_rate = 0.0f;
    }
    if(DEBUG)
      Toast.makeText(getActivity(), "Start Birth Rage: " + start_birth_rate, Toast.LENGTH_SHORT).show();
  }
}
