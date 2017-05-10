package com.project.tasktracker;


import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class  DataManager {

    public static final String TASKLIST_FILE_NAME = "tasklist.json";

    public static ArrayList<TaskItem> loadData(Context context) {

        ArrayList<TaskItem> items = new ArrayList<>();
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
            items = new Gson().fromJson(content, new TypeToken<List<TaskItem>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void saveData(ArrayList<TaskItem> items, Context context) {
        try {

            String data = new Gson().toJson(items, new TypeToken<ArrayList<TaskItem>>(){}.getType());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(TASKLIST_FILE_NAME, MODE_PRIVATE)));
            bw.write(data);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}