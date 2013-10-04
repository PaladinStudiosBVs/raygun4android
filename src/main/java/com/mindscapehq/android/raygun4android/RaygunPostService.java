package main.java.com.mindscapehq.android.raygun4android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class RaygunPostService extends Service
{

  private int tCount = 0;
  private Intent _intent;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId)
  {
    _intent = intent;
    final Bundle bundle = intent.getExtras();

    Thread t = new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        String message = (String) bundle.get("msg");
        String apiKey = (String) bundle.get("apikey");
        if (hasInternetConnection())
        {
          RaygunClient.Post(apiKey, message);
          synchronized (this)
          {
            File[] fileList = getCacheDir().listFiles();
            for (File f : fileList)
            {
              try
              {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                while (ois.available() == 1)
                {
                  MessageApiKey messageApiKey = (MessageApiKey) ois.readObject();
                  RaygunClient.Post(messageApiKey.apiKey, messageApiKey.message);
                  f.delete();
                }
              } catch (FileNotFoundException e)
              {
                Log.e("Raygun4Android", "Error loading cached message from filesystem - " + e.getMessage());

              } catch (IOException e)
              {
                Log.e("Raygun4Android", "Error reading cached message from filesystem - " + e.getMessage());
                e.printStackTrace();
              } catch (ClassNotFoundException e)
              {
                Log.e("Raygun4Android", "Error in cached message from filesystem - " + e.getMessage());
              }
            }
          }
        }
        else
        {
          synchronized (this)
          {
            int file = 0;
            File[] files = getCacheDir().listFiles();
            if (files != null)
            {
              for (File f : files)
              {
                String fileName = Integer.toString(file);
                if (!f.getName().equals(fileName))
                {
                  break;
                }
                else
                {
                  file++;
                }
              }
            }
            File fn = new File(getCacheDir(), Integer.toString(file));
            try
            {
              MessageApiKey messageApiKey = new MessageApiKey(apiKey, message);
              ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fn));
              out.writeObject(messageApiKey);
              out.close();
              Log.i("Raygun4Android", "Wrote file to disk");
            } catch (FileNotFoundException e)
            {
              Log.e("Raygun4Android", "Error creating file when caching message to filesystem - " + e.getMessage());
            } catch (IOException e)
            {
              Log.e("Raygun4Android", "Error writing message to filesystem - " + e.getMessage());
            }
          }
        }
        tCount--;
        if (tCount == 0)
        {
          stopSelf();
        }
      }
    });
    t.setDaemon(true);
    tCount++;
    t.start();
    return START_NOT_STICKY;
  }

  private boolean hasInternetConnection()
  {
    ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    stopService(_intent);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}