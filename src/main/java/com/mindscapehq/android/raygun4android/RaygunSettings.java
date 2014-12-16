package main.java.com.mindscapehq.android.raygun4android;

import java.net.URL;

public final class RaygunSettings {

  private static String apiEndpoint;

  private RaygunSettings() { }

  public static String getApiEndpoint() {
    if (apiEndpoint == null) {
      apiEndpoint = "https://api.raygun.io/entries";
    }

    return apiEndpoint;
  }

  public static void setApiEndpoint(URL url) {
    apiEndpoint = url.toString();
  }
}
