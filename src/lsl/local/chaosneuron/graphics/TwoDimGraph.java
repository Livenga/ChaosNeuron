package lsl.local.chaosneuron.graphics;

import lsl.local.chaosneuron.graphics.Points;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.util.Log;
import android.widget.Toast;
import android.util.AttributeSet;
import java.util.ArrayList;


public class TwoDimGraph extends View {
  private final static String TAG    = "TwoDimGraph";
  private final static boolean DEBUG = true;

  private Bitmap  fullImage = null;
  private Context mContext;
  private boolean isUpdate = false;


  // グラフの情報
  private String  x_axis_title = "x(t)";
  private String  y_axis_title = "y(t)";
  private float   x_range = 0.0f;
  private float   y_range = 0.0f;
  private float   startX, stopX;
  private float   startY, stopY;
  private float   pointSize = 1.0f;
  private boolean isLine    = false;
  private boolean isFixAxis = false;
  private ArrayList<Points> dataset = null;

  // グラフエリア情報
  float marginX, marginY;
  float lengthX, lengthY;


  public TwoDimGraph(Context context, AttributeSet attr) {
    super(context, attr);
    this.mContext = context;
    this.isUpdate = true;
  }
  public TwoDimGraph(Context context) {
    this(context, null);
  }

  // 表示時のCanvasの状態をここで処理
  private float len_par_range_X, len_par_range_Y;
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float rangeX, rangeY;
    float maxX, maxY, rmaxX, rmaxY;
    float minX, minY, rminX, rminY;

    init();

    if(!isUpdate)
      canvas.drawColor(Color.WHITE);

    if(fullImage == null || isUpdate) {
      canvas.drawColor(Color.WHITE);
      Paint mPaint = new Paint();

      fullImage = Bitmap.createBitmap(getWidth(), getHeight(),
          Config.ARGB_8888);
      Canvas cv = new Canvas(fullImage);
      cv.drawColor(Color.WHITE);

      mPaint = setPaint(1.0f, 0x00, 0x00, 0x00, false);
      drawAxis(mPaint, cv);
      //drawAxisTitle(mPaint, cv);

      // グラフ描画開始
      if(dataset != null) {
        if(isFixAxis) {
          minX = this.startX;
          maxX = this.stopX;
          minY = this.startY;
          maxY = this.stopY;
        } else {
          maxX = getXMaxValue(dataset); rmaxX = roundUp(maxX);
          maxY = getYMaxValue(dataset); rmaxY = roundUp(maxY);

          minX = getXMinValue(dataset); rminX = roundDown(minX);
          minY = getYMinValue(dataset); rminY = roundDown(minY);
          this.startX = minX;
          this.stopX  = maxX;

          this.startY = minY;
          this.stopY  = maxY;
        }


        //len_par_range_X = (lengthX - marginX) / (rmaxX - (rminX > 0.0f ? 0.0f : rminX));
        //len_par_range_Y = (lengthY - marginY) / (rmaxY - (rminY > 0.0f ? 0.0f : rminY));
        //len_par_range_X = (lengthX - marginX) / (maxX - minX);
        //len_par_range_Y = (lengthY - marginY) / (maxY - minY);
        len_par_range_X = (lengthX - marginX) / (stopX - startX);
        len_par_range_Y = (lengthY - marginY) / (stopY - startY);

        // 原点変更
        //cv.translate(marginX, lengthY - (len_par_range_Y * ((rminY < 0.0) ? -rminY : rminY)));
        cv.translate(marginX + (len_par_range_X * ((startX < 0.0f) ? -startX : 0.0f)),
            lengthY - (len_par_range_Y * ((startY < 0.0f) ? -startY : 0.0f)));

        // 点, 線の描画
        if(!isLine) {
          mPaint = setPaint(pointSize, 0x00, 0x00, 0xFF, false);
          drawPoint(mPaint, cv);
        } else if(isLine) {
          mPaint = setPaint(pointSize, 0x00, 0x00, 0xFF, true);
          drawLine(mPaint, cv);
        }
        mPaint = null;
      }

      cv.translate(0, 0); // 原点の初期化
    }

    isUpdate = false;
    canvas.drawBitmap(fullImage, 0, 0, null);
  }



  /**
  */
  private float getXMaxValue(ArrayList<Points> pts) {
    float maxof = pts.get(0).getX();
    for(int i = 1; i < pts.size(); i++)
      if(maxof < pts.get(i).getX()) maxof = pts.get(i).getX();
    return maxof;
  }
  private float getYMaxValue(ArrayList<Points> pts) {
    float maxof = pts.get(0).getY();
    for(int i = 1; i < pts.size(); i++)
      if(maxof < pts.get(i).getY()) maxof = pts.get(i).getY();
    return maxof;
  }
  private float getXMinValue(ArrayList<Points> pts) {
    float minof = pts.get(0).getX();
    for(int i = 1; i < pts.size(); i++)
      if(minof > pts.get(i).getX()) minof = pts.get(i).getX();
    return minof;
  }
  private float getYMinValue(ArrayList<Points> pts) {
    float minof = pts.get(0).getY();
    for(int i = 1; i < pts.size(); i++)
      if(minof > pts.get(i).getY()) minof = pts.get(i).getY();
    return minof;
  }
  private float roundUp(float val) {
    int inte_val = (int)val;
    return ((float)inte_val + 1.0f);
  }
  private float roundDown(float val) {
    return (float)((int)val);
  }


  /**
  */
  private void init() {
    this.marginX = (float)getWidth()  / 20;
    this.lengthX = (float)getWidth()  - this.marginX;

    this.marginY = (float)getHeight() / 10;
    this.lengthY = (float)getHeight() - this.marginY;
  }

  /**
  */
  private Paint setPaint(float stroke, int r, int g, int b, boolean anti) {
    Paint paint = new Paint();
    paint.setAntiAlias(anti);
    paint.setStrokeWidth(stroke);
    paint.setARGB(0xFF,
        (r > 0xFF) ? 0xFF : (r < 0x00) ? 0x00 : r,
        (g > 0xFF) ? 0xFF : (g < 0x00) ? 0x00 : g,
        (b > 0xFF) ? 0xFF : (b < 0x00) ? 0x00 : b);
    return paint;
  }

  /**
  */
  private void drawAxis(Paint paint, Canvas cv) {
    // X軸
    cv.drawLine(marginX, lengthY,
        lengthX, lengthY, paint);
    //cv.drawLine(marginX, marginY, //(float)getWidth() - marginX, marginY, paint);

    // Y軸
    cv.drawLine(marginX, (float)getHeight() - marginY,
        marginX, marginY, paint);
    //cv.drawLine((float)getWidth() - marginX, (float)getHeight() - marginY, (float)getWidth() - marginX, marginY, paint);
  }

  /**
  */
  private void drawAxisTitle(Paint paint, Canvas cv) {
    cv.drawText(x_axis_title, 100, 150, paint);
    cv.drawText(y_axis_title, 200, 150, paint);
  }

  /**
  */
  private void drawXAxisScale(Paint paint, Canvas cv) {
  }
  private void drawYAxisScale(Paint paint, Canvas cv) {
  }


  /**
  */
  private void drawPoint(Paint paint, Canvas cv) {
    float convX, convY;
    float minX;
    if(isFixAxis) {
      minX = this.startX;
    } else {
      minX = getXMinValue(dataset);
    }

    minX = (minX < 0.0f) ? 0.0f : minX;

    for(int i = 0; i < dataset.size(); i++) {
      convX =  len_par_range_X * (dataset.get(i).getX() - minX);
      convY = -len_par_range_Y * dataset.get(i).getY();
      //Log.d(TAG, "Draw Point: " + convX + ":" + convY);

      cv.drawPoint(convX, convY, paint);
    }
  }


  /**
  */
  private void drawLine(Paint paint, Canvas cv) {
    float startX, startY;
    float stopX,  stopY;
    for(int i = 0; i < dataset.size() - 1; i++) {
      startX =  len_par_range_X * dataset.get(i).getX();
      startY = -len_par_range_Y * dataset.get(i).getY();
      stopX  =  len_par_range_X * dataset.get(i + 1).getX();
      stopY  = -len_par_range_Y * dataset.get(i + 1).getY();

      cv.drawLine(startX, startY, stopX, stopY, paint);
    }
  }


  /**
  */
  public void setPointSize(float size) {
    this.pointSize = size;
  }


  /**
  */
  public void
    setAxisScale(float startX, float stopX, float startY, float stopY) {
      isFixAxis = true;
      this.startX = startX;
      this.stopX  = stopX;
      this.startY = startY;
      this.stopY  = stopY;
    }


  /**
  */
  public void setData(ArrayList<Points> dataset, boolean isLine) {
    this.dataset = dataset;
    isUpdate = true;
    this.isLine = isLine;
    invalidate();
  }


  /**
  */
  public void cleanCanvas() {
    this.isUpdate = true;
    this.dataset  = null;
    //this.dataset  = new ArrayList<Points>();
    postInvalidate();
  }


  /**
  */
  public void updateCanvas() {
    this.isUpdate = true;
    invalidate();
  }
}
