package com.project.lab2;


import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class VkResultModel {
    private int mTotalItemCount;
    private VKList<VKApiComment> mComments;

    public VkResultModel() {
        this.mComments = new VKList<>();
        this.mTotalItemCount = 0;
    }

    public VkResultModel(int mTotalItemCount, VKList<VKApiComment> mComments) {
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
