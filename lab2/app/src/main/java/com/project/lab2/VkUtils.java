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

    public static VKList<VKApiComment> loadComments(int offset) {
        final VKList<VKApiComment> result = new VKList<>();
        final VKRequest requestComments;
        final VKParameters params = new VKParameters();
        params.put(VKApiConst.OWNER_ID, "1");
        params.put(VKApiConst.POST_ID, "1725537");
        params.put(VKApiConst.COUNT, "15");
        params.put(VKApiConst.OFFSET, offset);

        requestComments = new VKRequest("wall.getComments", params);
        requestComments.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                result.addAll(deserialize(response));
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);

            }
        });

        return result;
    }

    //deserializing json response with deleted attachments from comments (with them - app crashes)
    private static VKList<VKApiComment> deserialize(VKResponse response) {
        Gson gson = new Gson();
        VKList<VKApiComment> result = new VKList<VKApiComment>();
        try {
            JSONArray commentsArray = response.json.getJSONObject("response").getJSONArray("items");

            for (int i = 0; i < commentsArray.length(); ++i) {
                JSONObject currentJsonComment = (JSONObject) commentsArray.get(i);
                currentJsonComment.remove("attachments");
                String js = String.valueOf(currentJsonComment);

                VKApiComment currentComment = gson.fromJson(js, VKApiComment.class);
                result.add(currentComment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
