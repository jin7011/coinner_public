// Generated by data binding compiler. Do not edit!
package com.coinner.coin_kotlin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.coinner.coin_kotlin.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityWriteBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout TopLayout;

  @NonNull
  public final EditText contentEdit;

  @NonNull
  public final ScrollView contentLayout;

  @NonNull
  public final ConstraintLayout rootConstraintLayout;

  @NonNull
  public final EditText titleEdit;

  @NonNull
  public final LinearLayout titleLayout;

  @NonNull
  public final ViewLoaderBinding writeLoadingview;

  @NonNull
  public final ToolbarWritepostBinding writeToolbar;

  protected ActivityWriteBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout TopLayout, EditText contentEdit, ScrollView contentLayout,
      ConstraintLayout rootConstraintLayout, EditText titleEdit, LinearLayout titleLayout,
      ViewLoaderBinding writeLoadingview, ToolbarWritepostBinding writeToolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.TopLayout = TopLayout;
    this.contentEdit = contentEdit;
    this.contentLayout = contentLayout;
    this.rootConstraintLayout = rootConstraintLayout;
    this.titleEdit = titleEdit;
    this.titleLayout = titleLayout;
    this.writeLoadingview = writeLoadingview;
    this.writeToolbar = writeToolbar;
  }

  @NonNull
  public static ActivityWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_write, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityWriteBinding>inflateInternal(inflater, R.layout.activity_write, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityWriteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_write, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityWriteBinding>inflateInternal(inflater, R.layout.activity_write, null, false, component);
  }

  public static ActivityWriteBinding bind(@NonNull View view) {
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
  public static ActivityWriteBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityWriteBinding)bind(component, view, R.layout.activity_write);
  }
}
