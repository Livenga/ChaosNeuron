package lsl.local.chaosneuron.logistic;

import lsl.local.chaosneuron.graphics.*;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class TimeSeriesFragment extends Fragment 
  implements View.OnClickListener {
  private View rootView = null;

  private Button btnRun = null;
  private TwoDimGraph graphTimeSeries = null;
  
  private EditText etNumYears  = null;
  private EditText etInitValue = null;
  private EditText etBirthRate = null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(
        lsl.local.chaosneuron.R.layout.logistic_time_series, container, false);

    findViews();
    return rootView;
  }

  private void findViews() {
    btnRun          = (Button)rootView.findViewById(lsl.local.chaosneuron.R.id.btn_run_logistic_time);
    graphTimeSeries = (TwoDimGraph)rootView.findViewById(lsl.local.chaosneuron.R.id.logistic_graph_time);

    etNumYears  = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_lts_num_years);
    etInitValue = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_lts_initial_value_xn);
    etBirthRate = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_lts_num_birth_rate);


    btnRun.setOnClickListener(this);
  }

  private int   num_years;
  private float birth_rate, initial_value;
  @Override
  public void onClick(View v) {
    ArrayList<Points> dataset = null;
    switch(v.getId()) {
      case lsl.local.chaosneuron.R.id.btn_run_logistic_time:
        setValues();
        graphTimeSeries.setAxisScale(0.0f, (float)num_years, 0.0f, 1.0f);
        dataset = Logistic.TimeSeries(num_years, birth_rate, initial_value);
        break;
    }

    if(dataset != null)
      graphTimeSeries.setData(dataset, true);
    dataset = null;
  }

  private void setValues() {
    String tmpString = null;

    tmpString = etNumYears.getText().toString();
    if(tmpString.length() > 0) {
      num_years = Integer.parseInt(tmpString);
      num_years = (num_years > 0 && num_years <= 5000) ? num_years : 100;
    } else {
      num_years = 100;
      etNumYears.setText("" + num_years);
    }

    tmpString = etInitValue.getText().toString();
    if(tmpString.length() > 0) {
      initial_value = Float.valueOf(tmpString);
      initial_value = (initial_value > 0.0f && initial_value <= 1.0f)
        ? initial_value : 0.5f;
    } else {
      initial_value = 0.5f;
      etInitValue.setText("" + initial_value);
    }

    tmpString = etBirthRate.getText().toString();
    if(tmpString.length() > 0) {
      birth_rate = Float.valueOf(tmpString);
      birth_rate = (birth_rate > 0.0f && birth_rate < 4.0f)
        ? birth_rate : 2.0f;
    } else {
      birth_rate = 2.0f;
      etBirthRate.setText("" + birth_rate);
    }

    tmpString = null;
  }
}
