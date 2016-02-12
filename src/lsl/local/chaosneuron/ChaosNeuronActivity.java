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


public class ChaosNeuronActivity extends Activity
{
  private final static String TAG    = "ChaosNeuronActivity";
  private final static boolean DEBUG = true;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }
}
