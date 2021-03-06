package com.mindscapehq.android.raygun4android.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import main.java.com.mindscapehq.android.raygun4android.RaygunClient;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunUserInfo;

import java.util.*;

/**
 * This class shows you how you can use Raygun4Android to send an exception to Raygun when one occurs.
 * Create an application in the Raygun dashboard, copy its API key, paste it into this app's AndroidManifest.xml,
 * then run it and hit the button.
 */
public class RaygunSampleApp extends Activity {

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.main);

    Map initialCustomData = new HashMap();

    initialCustomData.put("firstkey", "firstvalue");

    RaygunClient.init(getApplicationContext()); // This sets up the client with the API key as provided in your AndroidManifest.xml
    RaygunClient.attachExceptionHandler();      // This attaches a pre-made exception handler to catch all uncaught exceptions, and send them to Raygun

    RaygunClient.setUserCustomData(initialCustomData);

    final Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        RaygunUserInfo user = new RaygunUserInfo();
        user.FullName = "User Name";
        user.FirstName = "User";
        user.Email = "a@b.com";
        user.Uuid = "a uuid";
        user.IsAnonymous = false;

        Map tw = new HashMap();

        tw.put("secondkey", "secondvalue");

        RaygunClient.setUser(user);
        RaygunClient.send(new Exception("Congratulations, you have sent errors with Raygun4Android"), null, tw); // Example 1: Manual exception creation & sending


        int i = 3 / 0;                          // Example 2: A real exception will be thrown here, which will be caught & sent by RaygunClient
      }
    });
  }
}
