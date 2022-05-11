// Generated by view binder compiler. Do not edit!
package com.coinner.coin_kotlin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.coinner.coin_kotlin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ViewLoaderBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout loaderLyaout;

  @NonNull
  public final ProgressBar progressBar;

  private ViewLoaderBinding(@NonNull RelativeLayout rootView, @NonNull RelativeLayout loaderLyaout,
      @NonNull ProgressBar progressBar) {
    this.rootView = rootView;
    this.loaderLyaout = loaderLyaout;
    this.progressBar = progressBar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewLoaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewLoaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.view_loader, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewLoaderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout loaderLyaout = (RelativeLayout) rootView;

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      return new ViewLoaderBinding((RelativeLayout) rootView, loaderLyaout, progressBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}