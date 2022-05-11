package com.coinner.coin_kotlin;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.coinner.coin_kotlin.databinding.ActivityAdmobBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityAlarmBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityAlarmSettingBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityBoardBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityChangeNicknameBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityInfoBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityLoginBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityMainBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityMypostBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityNoticeBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityPolicyBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityPostBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivitySearchBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivitySourceBindingImpl;
import com.coinner.coin_kotlin.databinding.ActivityWriteBindingImpl;
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
  private static final int LAYOUT_ACTIVITYADMOB = 1;

  private static final int LAYOUT_ACTIVITYALARM = 2;

  private static final int LAYOUT_ACTIVITYALARMSETTING = 3;

  private static final int LAYOUT_ACTIVITYBOARD = 4;

  private static final int LAYOUT_ACTIVITYCHANGENICKNAME = 5;

  private static final int LAYOUT_ACTIVITYINFO = 6;

  private static final int LAYOUT_ACTIVITYLOGIN = 7;

  private static final int LAYOUT_ACTIVITYMAIN = 8;

  private static final int LAYOUT_ACTIVITYMYPOST = 9;

  private static final int LAYOUT_ACTIVITYNOTICE = 10;

  private static final int LAYOUT_ACTIVITYPOLICY = 11;

  private static final int LAYOUT_ACTIVITYPOST = 12;

  private static final int LAYOUT_ACTIVITYSEARCH = 13;

  private static final int LAYOUT_ACTIVITYSOURCE = 14;

  private static final int LAYOUT_ACTIVITYWRITE = 15;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(15);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_admob, LAYOUT_ACTIVITYADMOB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_alarm, LAYOUT_ACTIVITYALARM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_alarm_setting, LAYOUT_ACTIVITYALARMSETTING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_board, LAYOUT_ACTIVITYBOARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_change_nickname, LAYOUT_ACTIVITYCHANGENICKNAME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_info, LAYOUT_ACTIVITYINFO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_mypost, LAYOUT_ACTIVITYMYPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_notice, LAYOUT_ACTIVITYNOTICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_policy, LAYOUT_ACTIVITYPOLICY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_post, LAYOUT_ACTIVITYPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_search, LAYOUT_ACTIVITYSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_source, LAYOUT_ACTIVITYSOURCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.coinner.coin_kotlin.R.layout.activity_write, LAYOUT_ACTIVITYWRITE);
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
        case  LAYOUT_ACTIVITYADMOB: {
          if ("layout/activity_admob_0".equals(tag)) {
            return new ActivityAdmobBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_admob is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYALARM: {
          if ("layout/activity_alarm_0".equals(tag)) {
            return new ActivityAlarmBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_alarm is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYALARMSETTING: {
          if ("layout/activity_alarm_setting_0".equals(tag)) {
            return new ActivityAlarmSettingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_alarm_setting is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYBOARD: {
          if ("layout/activity_board_0".equals(tag)) {
            return new ActivityBoardBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_board is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCHANGENICKNAME: {
          if ("layout/activity_change_nickname_0".equals(tag)) {
            return new ActivityChangeNicknameBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_change_nickname is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYINFO: {
          if ("layout/activity_info_0".equals(tag)) {
            return new ActivityInfoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_info is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMYPOST: {
          if ("layout/activity_mypost_0".equals(tag)) {
            return new ActivityMypostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mypost is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYNOTICE: {
          if ("layout/activity_notice_0".equals(tag)) {
            return new ActivityNoticeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_notice is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPOLICY: {
          if ("layout/activity_policy_0".equals(tag)) {
            return new ActivityPolicyBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_policy is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPOST: {
          if ("layout/activity_post_0".equals(tag)) {
            return new ActivityPostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_post is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSEARCH: {
          if ("layout/activity_search_0".equals(tag)) {
            return new ActivitySearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_search is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSOURCE: {
          if ("layout/activity_source_0".equals(tag)) {
            return new ActivitySourceBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_source is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYWRITE: {
          if ("layout/activity_write_0".equals(tag)) {
            return new ActivityWriteBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_write is invalid. Received: " + tag);
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
    static final SparseArray<String> sKeys = new SparseArray<String>(4);

    static {
      sKeys.put(1, "Post");
      sKeys.put(2, "User");
      sKeys.put(0, "_all");
      sKeys.put(3, "infoActivity");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(15);

    static {
      sKeys.put("layout/activity_admob_0", com.coinner.coin_kotlin.R.layout.activity_admob);
      sKeys.put("layout/activity_alarm_0", com.coinner.coin_kotlin.R.layout.activity_alarm);
      sKeys.put("layout/activity_alarm_setting_0", com.coinner.coin_kotlin.R.layout.activity_alarm_setting);
      sKeys.put("layout/activity_board_0", com.coinner.coin_kotlin.R.layout.activity_board);
      sKeys.put("layout/activity_change_nickname_0", com.coinner.coin_kotlin.R.layout.activity_change_nickname);
      sKeys.put("layout/activity_info_0", com.coinner.coin_kotlin.R.layout.activity_info);
      sKeys.put("layout/activity_login_0", com.coinner.coin_kotlin.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.coinner.coin_kotlin.R.layout.activity_main);
      sKeys.put("layout/activity_mypost_0", com.coinner.coin_kotlin.R.layout.activity_mypost);
      sKeys.put("layout/activity_notice_0", com.coinner.coin_kotlin.R.layout.activity_notice);
      sKeys.put("layout/activity_policy_0", com.coinner.coin_kotlin.R.layout.activity_policy);
      sKeys.put("layout/activity_post_0", com.coinner.coin_kotlin.R.layout.activity_post);
      sKeys.put("layout/activity_search_0", com.coinner.coin_kotlin.R.layout.activity_search);
      sKeys.put("layout/activity_source_0", com.coinner.coin_kotlin.R.layout.activity_source);
      sKeys.put("layout/activity_write_0", com.coinner.coin_kotlin.R.layout.activity_write);
    }
  }
}
