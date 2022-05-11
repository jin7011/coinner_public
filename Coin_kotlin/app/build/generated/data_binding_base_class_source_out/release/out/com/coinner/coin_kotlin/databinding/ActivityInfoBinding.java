// Generated by data binding compiler. Do not edit!
package com.coinner.coin_kotlin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.coinner.coin_kotlin.R;
import com.coinner.coin_kotlin.info.User;
import com.coinner.coin_kotlin.infoactivity.InfoActivity;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityInfoBinding extends ViewDataBinding {
  @NonNull
  public final TextView appNotificationT;

  @NonNull
  public final TextView appversionT;

  @NonNull
  public final TextView changeNickT;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final TextView mailT;

  @NonNull
  public final TextView myPostT;

  @NonNull
  public final TextView nicknameT;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView withdraw;

  @Bindable
  protected User mUser;

  @Bindable
  protected InfoActivity mInfoActivity;

  protected ActivityInfoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView appNotificationT, TextView appversionT, TextView changeNickT, ImageView imageView3,
      TextView mailT, TextView myPostT, TextView nicknameT, TextView textView2, TextView withdraw) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appNotificationT = appNotificationT;
    this.appversionT = appversionT;
    this.changeNickT = changeNickT;
    this.imageView3 = imageView3;
    this.mailT = mailT;
    this.myPostT = myPostT;
    this.nicknameT = nicknameT;
    this.textView2 = textView2;
    this.withdraw = withdraw;
  }

  public abstract void setUser(@Nullable User User);

  @Nullable
  public User getUser() {
    return mUser;
  }

  public abstract void setInfoActivity(@Nullable InfoActivity infoActivity);

  @Nullable
  public InfoActivity getInfoActivity() {
    return mInfoActivity;
  }

  @NonNull
  public static ActivityInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_info, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityInfoBinding>inflateInternal(inflater, R.layout.activity_info, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_info, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityInfoBinding>inflateInternal(inflater, R.layout.activity_info, null, false, component);
  }

  public static ActivityInfoBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityInfoBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityInfoBinding)bind(component, view, R.layout.activity_info);
  }
}
