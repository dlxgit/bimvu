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
import static android.provider.Telephony.Mms.Part.FILENAME;

public class  DataManager {

    public static final String TASKLIST_FILE_NAME = "tasklist.json";

    public static ArrayList<TaskItem> loadData(Context context) {

        ArrayList<TaskItem> items = new ArrayList<>();
        //items.add(new TaskItem("asd", "asdd", 1, "1.1.1"));
//
        //items.add(new TaskItem("asdd", "asddd", 1, "1"));
        //saveData(items, context);
        try {

            //saveData(new ArrayList<TaskItem>(), context);
            //FileInputStream inputStream = new FileInputStream(new File(TASKLIST_FILE_NAME));

            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(TASKLIST_FILE_NAME)));

            String line = br.readLine();
            String content = line;
            while( (line != null) && (!line.isEmpty()) ){
                line = br.readLine();
                if(line != null) {
                    content += line;
                }
            }

//            String cont = br.readLine();
            //com.google.gson.stream.JsonReader reader = new com.google.gson.stream.JsonReader(br);
//            String content = new String();
//            String line = br.readLine();
//            while(!line.isEmpty()) {
//                content += line;
//                line = br.readLine();
//            }

            items = new Gson().fromJson(content, new TypeToken<List<TaskItem>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    public static void saveData(ArrayList<TaskItem> items, Context context) {
        try {

            String data = new Gson().toJson(items, new TypeToken<ArrayList<TaskItem>>(){}.getType());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(TASKLIST_FILE_NAME, MODE_PRIVATE)));
            bw.write(data);
            bw.close();
            ArrayList<TaskItem> myItems = loadData(context);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}