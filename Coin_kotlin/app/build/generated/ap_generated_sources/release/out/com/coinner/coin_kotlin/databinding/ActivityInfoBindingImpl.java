package com.coinner.coin_kotlin.databinding;
import com.coinner.coin_kotlin.R;
import com.coinner.coin_kotlin.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityInfoBindingImpl extends ActivityInfoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.imageView3, 3);
        sViewsWithIds.put(R.id.change_nickT, 4);
        sViewsWithIds.put(R.id.myPostT, 5);
        sViewsWithIds.put(R.id.withdraw, 6);
        sViewsWithIds.put(R.id.appversionT, 7);
        sViewsWithIds.put(R.id.textView2, 8);
        sViewsWithIds.put(R.id.app_notificationT, 9);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityInfoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private ActivityInfoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[6]
            );
        this.mailT.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nicknameT.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.User == variableId) {
            setUser((com.coinner.coin_kotlin.info.User) variable);
        }
        else if (BR.infoActivity == variableId) {
            setInfoActivity((com.coinner.coin_kotlin.infoactivity.InfoActivity) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setUser(@Nullable com.coinner.coin_kotlin.info.User User) {
        this.mUser = User;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.User);
        super.requestRebind();
    }
    public void setInfoActivity(@Nullable com.coinner.coin_kotlin.infoactivity.InfoActivity InfoActivity) {
        this.mInfoActivity = InfoActivity;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.coinner.coin_kotlin.info.User user = mUser;
        java.lang.String userMail = null;
        java.lang.String userNickname = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (user != null) {
                    // read User.mail
                    userMail = user.getMail();
                    // read User.nickname
                    userNickname = user.getNickname();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mailT, userMail);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.nicknameT, userNickname);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): User
        flag 1 (0x2L): infoActivity
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}