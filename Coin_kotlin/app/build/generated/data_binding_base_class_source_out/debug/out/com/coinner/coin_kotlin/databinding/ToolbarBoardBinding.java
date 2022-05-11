// Generated by view binder compiler. Do not edit!
package com.coinner.coin_kotlin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.coinner.coin_kotlin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ToolbarBoardBinding implements ViewBinding {
  @NonNull
  private final Toolbar rootView;

  @NonNull
  public final TextView boardcoinNameT;

  @NonNull
  public final ImageView boardresetBtn;

  @NonNull
  public final ImageView boardsearchBtn;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final ImageView postBtn;

  private ToolbarBoardBinding(@NonNull Toolbar rootView, @NonNull TextView boardcoinNameT,
      @NonNull ImageView boardresetBtn, @NonNull ImageView boardsearchBtn, @NonNull ImageView logo,
      @NonNull ImageView postBtn) {
    this.rootView = rootView;
    this.boardcoinNameT = boardcoinNameT;
    this.boardresetBtn = boardresetBtn;
    this.boardsearchBtn = boardsearchBtn;
    this.logo = logo;
    this.postBtn = postBtn;
  }

  @Override
  @NonNull
  public Toolbar getRoot() {
    return rootView;
  }

  @NonNull
  public static ToolbarBoardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ToolbarBoardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.toolbar_board, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ToolbarBoardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.boardcoin_nameT;
      TextView boardcoinNameT = ViewBindings.findChildViewById(rootView, id);
      if (boardcoinNameT == null) {
        break missingId;
      }

      id = R.id.boardreset_btn;
      ImageView boardresetBtn = ViewBindings.findChildViewById(rootView, id);
      if (boardresetBtn == null) {
        break missingId;
      }

      id = R.id.boardsearch_btn;
      ImageView boardsearchBtn = ViewBindings.findChildViewById(rootView, id);
      if (boardsearchBtn == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.post_btn;
      ImageView postBtn = ViewBindings.findChildViewById(rootView, id);
      if (postBtn == null) {
        break missingId;
      }

      return new ToolbarBoardBinding((Toolbar) rootView, boardcoinNameT, boardresetBtn,
          boardsearchBtn, logo, postBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
