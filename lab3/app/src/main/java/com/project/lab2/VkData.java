package com.project.lab2;


import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class VkData {
    private int mTotalItemCount;
    private VKList<VKApiComment> mComments;
    private int firstOffset;

    public VkData() {
        this.mComments = new VKList<>();
        this.mTotalItemCount = 0;
    }

    public VkData(int mTotalItemCount, VKList<VKApiComment> mComments) {
        this.mTotalItemCount = mTotalItemCount;
        this.mComments = mComments;
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
