package com.robincaroff.mygeofencer.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.robincaroff.mygeofencer.models.MyGeofence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Geofences repository using the internal sotrage to persist data
 */


public class InternalStorageMyGeofencesRepository implements MyGeofencesRepositoryProtocol {

    private static final String FILENAME = "geofences.json";

    private Context context;

    public InternalStorageMyGeofencesRepository(Context context) {
        this.context = context;
    }

    @Override
    public void deleteGeofence(MyGeofence geofence) {
        List<MyGeofence> geofences = getGeofences();
        int idx = -1;
        for(int i = 0; i < geofences.size(); i++) {
            if(geofences.get(i).getUuid().equals(geofence.getUuid())) {
                idx = i;
                break;
            }
        }
        if(idx == -1) {
            Log.e(InternalStorageMyGeofencesRepository.class.getSimpleName(), "Cannot remove unknown geofence: " + geofence.getName());
            return;
        }

        geofences.remove(idx);
        saveGeofencesList(geofences);
    }

    @Override
    public List<MyGeofence> getGeofences() {
        String jsonFeed = readFromInternalStorage();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<MyGeofence> geofences = gson.fromJson(jsonFeed, new TypeToken<List<MyGeofence>>(){}.getType());
        if(geofences == null) {
            geofences = new ArrayList<>();
        }
        return geofences;
    }

    @Override
    public void saveGeofence(MyGeofence geofence) {
        List<MyGeofence> geofences = getGeofences();
        geofences.add(geofence);
        saveGeofencesList(geofences);
    }

    private void saveGeofencesList(List<MyGeofence> geofences) {
        Gson gson = new Gson();
        String json = gson.toJson(geofences);
        writeToInternalStorage(json);
    }

    private void writeToInternalStorage(String textContent) {
        try {
            // Get the FileOutputStream object
            FileOutputStream output = context.openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(output, "UTF-8");
            osw.write(textContent);
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromInternalStorage() {
        String textContent = "";

        try {
            // Get the FileInputStream object
            FileInputStream input = context.openFileInput(FILENAME);

            InputStreamReader isr = new InputStreamReader(input,"UTF-8");

            StringBuffer stringBuffer = new StringBuffer(); //Use a StringBuffer to create the string
            int characterValue;
            while((characterValue = isr.read()) != -1) { //Read characters one by one
                stringBuffer.append((char) characterValue);
            }

            textContent = stringBuffer.toString();

            //Don't forget to close it once you are done
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textContent;
    }
}
