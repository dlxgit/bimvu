package com.project.lab2;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class DataManager {

    public static final String TASKLIST_FILE_NAME = "commentlist.json";

    public static VkData loadData(Context context) {

        VkData result = new VkData();
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
            result = new Gson().fromJson(content, new TypeToken<VkData>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void saveData(VkData model, Context context) {
        try {
            String strData = new Gson().toJson(model, new TypeToken<VkData>(){}.getType());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(TASKLIST_FILE_NAME, MODE_PRIVATE)));
            bw.write(strData);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}