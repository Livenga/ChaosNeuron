package lsl.local.chaosneuron.chaos;

import lsl.local.chaosneuron.graphics.Points;
import android.util.Log;
import java.util.ArrayList;
import java.lang.Math;


public class ChaosNeuronModel {
  private static final float k       = 0.5f;
  private static final float alpha   = 1.0f;
  private static final float epsilon = 0.015f;
  private static final float a       = 0.3f;

  /**
  */

  private static float sigmoid(float input_y, float epsilon) {
    return 1.0f / (1.0f + (float)Math.exp(-input_y / epsilon));
  }

  public static ArrayList<Points>
    TimeSeries(int t, float y0) {
      float x, y;
      ArrayList<Points> dataset = new ArrayList<Points>();

      y = y0;
      x = sigmoid(y, epsilon);

      dataset.add(new Points((float)0, y));
      for(int i = 1; i < t; i++) {
        y = k * y - alpha * x + a;
        x = sigmoid(y, epsilon);

        dataset.add(new Points((float)i, y));
      }

      return dataset;
    }

  public static ArrayList<Points>
    ReturnMap(int t, float y0) {
      float x, y, delay_y;
      ArrayList<Points> dataset = new ArrayList<Points>();

      y = y0;
      x = sigmoid(y, epsilon);

      for(int i = 0; i < t - 1; i++) {
        delay_y = y;
        y = k * y - alpha * x + a;
        x = sigmoid(y, epsilon);

        dataset.add(new Points(delay_y, y));
      }

      return dataset;
    }

  public static ArrayList<Points>
    BifurcationDiagram(int t, float y0) {
      float x, y;
      ArrayList<Points> dataset = new ArrayList<Points>();

      for(float cnt_a = 0.00f; cnt_a <= 1.0f; cnt_a += 0.001f) {
        y = y0;
        x = sigmoid(y, epsilon);

        //dataset.add(new Points(cnt_a, y));
        for(int i = 1; i < t; i++) {
          y = k * y - alpha * x + cnt_a;
          x = sigmoid(y, epsilon);

          //if(t > 0)
          dataset.add(new Points(cnt_a, y));
        }
      }

      return dataset;
    }
}
