package main.java.com.mindscapehq.android.raygun4android;


import java.io.Serializable;

public class MessageApiKey implements Serializable
{
  public String apiKey;
  public String message;
  public String endpoint;

  public MessageApiKey() { }

  public MessageApiKey(String apiKey, String message, String endpoint)
  {
    this.apiKey = apiKey;
    this.message = message;
    this.endpoint = endpoint;
  }
}
