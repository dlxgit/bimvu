package com.project.lab2;


import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class VkData {
    private int mTotalItemCount;
    private VKList<VKApiComment> mComments;
    private int firstOffset;
    private boolean isLoadingNew = false;

    public VkData() {
        this.mComments = new VKList<>();
        this.mTotalItemCount = 0;
        this.firstOffset = 0;
    }

    public VkData(int mTotalItemCount, VKList<VKApiComment> mComments) {
        this.mTotalItemCount = mTotalItemCount;
        this.mComments = mComments;
    }

    public VkData(int mTotalItemCount, VKList<VKApiComment> mComments, int firstOffset, boolean isLoadingNew) {
        this.mTotalItemCount = mTotalItemCount;
        this.mComments = mComments;
        this.firstOffset = firstOffset;
        this.isLoadingNew = isLoadingNew;
    }

    public boolean isLoadingNew() {
        return isLoadingNew;
    }

    public void setLoadingNew(boolean loadingNew) {
        isLoadingNew = loadingNew;
    }

    public int getFirstOffset() {
        return firstOffset;
    }

    public void setFirstOffset(int firstOffset) {
        this.firstOffset = firstOffset;
    }

    public void setmTotalItemCount(int mTotalItemCount) {
        this.mTotalItemCount = mTotalItemCount;
    }

    public void setmComments(VKList<VKApiComment> mComments) {
        this.mComments = mComments;
    }

    public int getmTotalItemCount() {
        return mTotalItemCount;
    }

    public VKList<VKApiComment> getmComments() {
        return mComments;
    }
}