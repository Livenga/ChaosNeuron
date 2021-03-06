package lsl.local.chaosneuron;

import lsl.local.chaosneuron.logistic.*;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LogisticMapActivity extends Activity 
  implements ActionBar.TabListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // タスクバーの非表示化
    Window window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.logistic_activity);

    ActionBar actionBar = getActionBar();

    Tab tabTimeSeries = actionBar.newTab();
    tabTimeSeries.setTag("time_series");
    tabTimeSeries.setText("Time Series");
    tabTimeSeries.setTabListener(this);
    actionBar.addTab(tabTimeSeries);

    Tab tabReturnMap = actionBar.newTab();
    tabReturnMap.setTag("return_map");
    tabReturnMap.setText("Return Map");
    tabReturnMap.setTabListener(this);
    actionBar.addTab(tabReturnMap);

    Tab tabBifurcationDiagram = actionBar.newTab();
    tabBifurcationDiagram.setTag("bifurcation_diagram");
    tabBifurcationDiagram.setText("Bifurcation Diagram");
    tabBifurcationDiagram.setTabListener(this);
    actionBar.addTab(tabBifurcationDiagram);

    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    Fragment fragment = null;

    if(tab.getTag().toString().equals("time_series")) {
      fragment = new TimeSeriesFragment();
    } else if(tab.getTag().toString().equals("return_map")) {
      fragment = new ReturnMapFragment();
    } else if(tab.getTag().toString().equals("bifurcation_diagram")) {
      fragment = new BifurcationDiagramFragment();
    }


    if(fragment != null)
      ft.replace(R.id.logistic_main, fragment, tab.getTag().toString());
  }

  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {
  }

  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
  }
}
