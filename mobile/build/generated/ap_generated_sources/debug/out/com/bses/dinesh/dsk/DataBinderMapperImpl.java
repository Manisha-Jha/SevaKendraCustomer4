package com.bses.dinesh.dsk;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.bses.dinesh.dsk.databinding.ActivityOrderDetailActivityBindingImpl;
import com.bses.dinesh.dsk.databinding.ActivityOrderTestingBindingImpl;
import com.bses.dinesh.dsk.databinding.EditOrderDetailLayoutBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYORDERDETAILACTIVITY = 1;

  private static final int LAYOUT_ACTIVITYORDERTESTING = 2;

  private static final int LAYOUT_EDITORDERDETAILLAYOUT = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bses.dinesh.dsk.R.layout.activity_order_detail_activity, LAYOUT_ACTIVITYORDERDETAILACTIVITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bses.dinesh.dsk.R.layout.activity_order_testing, LAYOUT_ACTIVITYORDERTESTING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bses.dinesh.dsk.R.layout.edit_order_detail_layout, LAYOUT_EDITORDERDETAILLAYOUT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYORDERDETAILACTIVITY: {
          if ("layout/activity_order_detail_activity_0".equals(tag)) {
            return new ActivityOrderDetailActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_order_detail_activity is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYORDERTESTING: {
          if ("layout/activity_order_testing_0".equals(tag)) {
            return new ActivityOrderTestingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_order_testing is invalid. Received: " + tag);
        }
        case  LAYOUT_EDITORDERDETAILLAYOUT: {
          if ("layout/edit_order_detail_layout_0".equals(tag)) {
            return new EditOrderDetailLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for edit_order_detail_layout is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/activity_order_detail_activity_0", com.bses.dinesh.dsk.R.layout.activity_order_detail_activity);
      sKeys.put("layout/activity_order_testing_0", com.bses.dinesh.dsk.R.layout.activity_order_testing);
      sKeys.put("layout/edit_order_detail_layout_0", com.bses.dinesh.dsk.R.layout.edit_order_detail_layout);
    }
  }
}
