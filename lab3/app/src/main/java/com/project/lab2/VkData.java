package com.project.lab2;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

public class VkData {
    private int mTotalItemCount;
    private VKList<VKApiComment> mComments;
    private int firstOffset;

    public VkData() {
        this.mComments = new VKList<>();
        this.mTotalItemCount = 0;
        this.firstOffset = 0;
    }

    public VkData(int mTotalItemCount, VKList<VKApiComment> mComments) {
        this.mTotalItemCount = mTotalItemCount;
        this.mComments = mComments;
    }

    public VkData(int mTotalItemCount, VKList<VKApiComment> mComments, int firstOffset) {
        this.mTotalItemCount = mTotalItemCount;
        this.mComments = mComments;
        this.firstOffset = firstOffset;
    }

    public String toJsonString() {
        return new Gson().toJson(this, new TypeToken<VkData>(){}.getType());
    }

    public int getFirstOffset() {
        return firstOffset;
    }

    public void setFirstOffset(int firstOffset) {
        this.firstOffset = firstOffset;
    }

    public void setTotalItemCount(int mTotalItemCount) {
        this.mTotalItemCount = mTotalItemCount;
    }

    public void setComments(VKList<VKApiComment> mComments) {
        this.mComments = mComments;
    }

    public int getmTotalItemCount() {
        return mTotalItemCount;
    }

    public VKList<VKApiComment> getmComments() {
        return mComments;
    }
}