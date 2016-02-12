package lsl.local.chaosneuron.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.util.Log;
import android.widget.Toast;
import android.util.AttributeSet;

public class Points {
  public float x, y;

  public Points(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Points() {}

  public void setX(float x) {this.x = x;}
  public void setY(float Y) {this.y = y;}
  public void setXY(float x, float y) {
    this.x = x; this.y = y;
  }

  public float getX() {return x;}
  public float getY() {return y;}
}
