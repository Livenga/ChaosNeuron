package lsl.local.chaosneuron.logistic;

import lsl.local.chaosneuron.graphics.Points;
import android.util.Log;
import java.util.ArrayList;
import java.lang.Math;


public class Logistic {
  /**
   */
  public static ArrayList<Points>
    TimeSeries(int year, float alpha, float x0) {
      Points point;
      ArrayList<Points> point_array = new ArrayList<Points>();

      point = new Points((float)0, x0);
      point.setXY((float)0, x0);
      point_array.add(point);
      point = null;

      float x = x0;
      for(int i = 1; i < year; i++) {
        x = alpha * x * (1.0f - x);
        point = new Points((float)i, x);
        point_array.add(point);
        point = null;
      }
      return point_array;
    }

  /**
   */
  public static ArrayList<Points>
    ReturnMap(int year, float alpha, float x0) {
      float Xn, Xn1;
      Points point;
      ArrayList<Points> point_array = new ArrayList<Points>();

      Xn = x0;
      for(int i = 0; i < year; i++) {
        Xn1 = alpha * Xn * (1.0f - Xn);

        point = new Points(Xn, Xn1);
        point_array.add(point);
        point = null;

        Log.d("dododo", "Xn = " + Xn);
        Xn = Xn1;
      }
      return point_array;
    }

  /**
   */
  public static ArrayList<Points>
    BifurcationDiagram(int year, float x0, float startAlpha, float stopAlpha) {
      float x;
      ArrayList<Points> point_array = new ArrayList<Points>();
      Points point;

      for(float alpha = (startAlpha < 0.0f ? startAlpha : 0.0f); alpha < stopAlpha; alpha += 0.001f) {
        x = x0;

        /*
        point = new Points((float)alpha, x);
        point_array.add(point);
        point = null;
        */

        for(int i = 0; i < year; i++) {
          x = alpha * x * (1.0f - x);

          if(i > 0 && (alpha >= startAlpha)) {
            point = new Points((float)alpha, x);
            point_array.add(point);
            point = null;
          }
        }
      }
      return point_array;
    }
}
