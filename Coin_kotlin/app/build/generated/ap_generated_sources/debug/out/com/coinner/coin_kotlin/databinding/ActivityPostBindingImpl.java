package com.coinner.coin_kotlin.databinding;
import com.coinner.coin_kotlin.R;
import com.coinner.coin_kotlin.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPostBindingImpl extends ActivityPostBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_loadingview, 8);
        sViewsWithIds.put(R.id.toolbar_post, 9);
        sViewsWithIds.put(R.id.toolbar_title, 10);
        sViewsWithIds.put(R.id.scrollView3, 11);
        sViewsWithIds.put(R.id.face, 12);
        sViewsWithIds.put(R.id.imageView_post, 13);
        sViewsWithIds.put(R.id.good_btn_Frame, 14);
        sViewsWithIds.put(R.id.opt_comment, 15);
        sViewsWithIds.put(R.id.textView4, 16);
        sViewsWithIds.put(R.id.con, 17);
        sViewsWithIds.put(R.id.imageView2, 18);
        sViewsWithIds.put(R.id.formatsLinearLayout, 19);
        sViewsWithIds.put(R.id.formatsRecycler, 20);
        sViewsWithIds.put(R.id.commentLinearLayout, 21);
        sViewsWithIds.put(R.id.commentRecycler, 22);
        sViewsWithIds.put(R.id.commentFrame, 23);
        sViewsWithIds.put(R.id.commentT_LinearLayout, 24);
        sViewsWithIds.put(R.id.Add_commentT, 25);
        sViewsWithIds.put(R.id.Add_comment_btn, 26);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPostBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private ActivityPostBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[26]
            , (android.widget.EditText) bindings[25]
            , (android.widget.FrameLayout) bindings[23]
            , (android.widget.LinearLayout) bindings[21]
            , (android.widget.TextView) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[22]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.LinearLayout) bindings[17]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[19]
            , (androidx.recyclerview.widget.RecyclerView) bindings[20]
            , (android.widget.FrameLayout) bindings[14]
            , (android.widget.TextView) bindings[6]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageView) bindings[15]
            , (bindings[8] != null) ? com.coinner.coin_kotlin.databinding.ViewLoaderBinding.bind((android.view.View) bindings[8]) : null
            , (androidx.core.widget.NestedScrollView) bindings[11]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[4]
            , (androidx.appcompat.widget.Toolbar) bindings[9]
            , (android.widget.TextView) bindings[10]
            );
        this.commentNumPostT.setTag(null);
        this.constraintLayout.setTag(null);
        this.contentPostT.setTag(null);
        this.datePostT.setTag(null);
        this.goodNumPostT.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nicknamePostT.setTag(null);
        this.titlePostT.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.Post == variableId) {
            setPost((com.coinner.coin_kotlin.info.Post) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPost(@Nullable com.coinner.coin_kotlin.info.Post Post) {
        this.mPost = Post;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.Post);
        super.requestRebind();
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
        java.lang.String stringValueOfPostCommentNum = null;
        java.lang.String postContent = null;
        java.lang.String postTitle = null;
        java.lang.String postNickname = null;
        int postCommentNum = 0;
        int postLove = 0;
        java.lang.String stringValueOfPostLove = null;
        java.lang.String postDateFormateForLayout = null;
        com.coinner.coin_kotlin.info.Post post = mPost;

        if ((dirtyFlags & 0x3L) != 0) {



                if (post != null) {
                    // read Post.content
                    postContent = post.getContent();
                    // read Post.title
                    postTitle = post.getTitle();
                    // read Post.nickname
                    postNickname = post.getNickname();
                    // read Post.commentNum
                    postCommentNum = post.getCommentNum();
                    // read Post.love
                    postLove = post.getLove();
                    // read Post.dateFormate_for_layout
                    postDateFormateForLayout = post.getDateFormate_for_layout();
                }


                // read String.valueOf(Post.commentNum)
                stringValueOfPostCommentNum = java.lang.String.valueOf(postCommentNum);
                // read String.valueOf(Post.love)
                stringValueOfPostLove = java.lang.String.valueOf(postLove);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.commentNumPostT, stringValueOfPostCommentNum);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.contentPostT, postContent);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.datePostT, postDateFormateForLayout);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.goodNumPostT, stringValueOfPostLove);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.nicknamePostT, postNickname);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.titlePostT, postTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): Post
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}