package com.project.lab2;


import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class VkDownloader {

    /*
    public static VkResultModel loadComments(int offset) {
        final VkResultModel result = new VkResultModel();
        final VKParameters params = initRequestParameters(offset);
        final VKRequest requestComments = new VKRequest("wall.getComments", params);

        requestComments.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VkResultModel res = deserializeList(response);
                result.setmTotalItemCount(res.getmTotalItemCount());
                result.setmComments(res.getmComments());
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });

        return result;

        */
}
