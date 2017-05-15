package com.project.lab2;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class DataManager {

    public static final String TASKLIST_FILE_NAME = "commentlist.json";

    public static VKList<VKApiComment> loadData(Context context) {

        VKList<VKApiComment> items = new VKList<VKApiComment>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(TASKLIST_FILE_NAME)));
            String line = br.readLine();
            String content = line;
            while( (line != null) && (!line.isEmpty()) ){
                line = br.readLine();
                if(line != null) {
                    content += line;
                }
            }
            items = new Gson().fromJson(content, new TypeToken<VKList<VKApiComment>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void saveData(VKList<VKApiComment> items, Context context) {
        try {
            String data = new Gson().toJson(items, new TypeToken<VKList<VKApiComment>>(){}.getType());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(TASKLIST_FILE_NAME, MODE_PRIVATE)));
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}