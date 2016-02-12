package lsl.local.chaosneuron.chaos;

import lsl.local.chaosneuron.graphics.*;
import lsl.local.chaosneuron.chaos.ChaosNeuronModel;

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
  private final static String TAG    = "NeuronModel TimeSeries";
  private final static boolean DEBUG = true;

  private View rootView = null;
  private Button btnRun = null;
  private EditText etNumTimes  = null;
  private EditText etInitValue = null;
  private TwoDimGraph graphTimeSeries = null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(
        lsl.local.chaosneuron.R.layout.neuron_model_time_series, container, false);

    findViews();
    return rootView;
  }

  private void findViews() {
    btnRun = (Button)rootView.findViewById(
        lsl.local.chaosneuron.R.id.btn_run_neuron_model_time);

    etNumTimes = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_nm_num_times);
    etInitValue = (EditText)rootView.findViewById(
        lsl.local.chaosneuron.R.id.et_nm_initial_value_xn);

    graphTimeSeries = (TwoDimGraph)rootView.findViewById(
        lsl.local.chaosneuron.R.id.neuron_model_graph_time);

    btnRun.setOnClickListener(this);
  }

  private int   num_times;
  private float initial_y;

  @Override
  public void onClick(View v) {
    ArrayList<Points> dataset = null;

    switch(v.getId()) {
      case lsl.local.chaosneuron.R.id.btn_run_neuron_model_time:
        setValues();
        dataset = ChaosNeuronModel.TimeSeries(num_times, initial_y);
        break;
    }

    if(dataset != null)
      graphTimeSeries.setData(dataset, true);
    dataset = null;
  }

  private void setValues() {
    String tmpString = null;

    tmpString = etNumTimes.getText().toString();
    if(tmpString.length() > 0) {
      num_times = Integer.parseInt(tmpString);
      num_times = (num_times > 0 && num_times <= 2000) ? num_times : 100;
    } else {
      num_times = 100;
      etNumTimes.setText("" + num_times);
    }

    tmpString = etInitValue.getText().toString();
    if(tmpString.length() > 0) {
      initial_y = Float.valueOf(tmpString);
      initial_y = (initial_y > 0.0f && initial_y <= 1.0f) ? initial_y : 0.5f;
    } else {
      initial_y = 0.5f;
      etInitValue.setText("" + initial_y);
    }
  }
}
