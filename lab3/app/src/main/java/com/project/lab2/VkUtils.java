package com.project.lab2;

import com.google.gson.Gson;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VkUtils {
    public static final int REQUEST_COMMENTS_COUNT = 15;

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
    }

    private static VKParameters initRequestParameters(int offset) {
        final VKParameters result = new VKParameters();
        result.put(VKApiConst.OWNER_ID, "1");
        result.put(VKApiConst.POST_ID, "1725537");
        result.put(VKApiConst.COUNT, REQUEST_COMMENTS_COUNT);
        result.put(VKApiConst.OFFSET, offset);
        return result;
    }

    //deserializing json response with deleted attachments from comments (with them - app crashes)
    private static VkResultModel deserializeList(VKResponse response) {
        Gson gson = new Gson();

        VKList<VKApiComment> result = new VKList<VKApiComment>();
        int commentsCount = 0;
        try {
            JSONArray commentsArray = response.json.getJSONObject("response").getJSONArray("items");
            commentsCount = response.json.getJSONObject("response").getInt("count");
            //commentsCount = gson.fromJson(response.json.getJSONObject("response").getJSONObject("count").toString(), int.class);

            for (int i = commentsArray.length() - 1;  i >= 0; --i) {
                JSONObject currentJsonComment = (JSONObject) commentsArray.get(i);
                currentJsonComment.remove("attachments");
                String js = String.valueOf(currentJsonComment);

                VKApiComment currentComment = gson.fromJson(js, VKApiComment.class);
                result.add(currentComment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new VkResultModel(commentsCount, result);
    }
}