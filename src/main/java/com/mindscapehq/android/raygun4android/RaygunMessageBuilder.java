package main.java.com.mindscapehq.android.raygun4android;

import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.mindscapehq.android.raygun4android.messages.RaygunClientMessage;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunEnvironmentMessage;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunErrorMessage;
import main.java.com.mindscapehq.android.raygun4android.messages.RaygunMessage;

public class RaygunMessageBuilder implements IRaygunMessageBuilder {
  private RaygunMessage raygunMessage;

  public RaygunMessageBuilder() {
    raygunMessage = new RaygunMessage();
  }

  @Override
  public RaygunMessage build() {
    return raygunMessage;
  }

  @Override
  public IRaygunMessageBuilder setMachineName(String machineName) {
    raygunMessage.getDetails().setMachineName(machineName);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setExceptionDetails(Throwable throwable) {
    raygunMessage.getDetails().setError(new RaygunErrorMessage(throwable));

    Map customData = raygunMessage.getDetails().getUserCustomData();
    if (customData == null)
    {
      customData = new HashMap();
    }
    customData.put("full_message", throwable.getMessage());
    raygunMessage.getDetails().setUserCustomData(customData);

    return this;
  }

  @Override
  public IRaygunMessageBuilder setClientDetails() {
    raygunMessage.getDetails().setClient(new RaygunClientMessage());
    return this;
  }

  @Override
  public IRaygunMessageBuilder setEnvironmentDetails(Context context) {
    raygunMessage.getDetails().setEnvironment(new RaygunEnvironmentMessage(context));
    return this;
  }

  @Override
  public IRaygunMessageBuilder setVersion(String version) {
    raygunMessage.getDetails().setVersion(version);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setTags(List tags) {
    raygunMessage.getDetails().setTags(tags);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setUserCustomData(Map userCustomData) {
    raygunMessage.getDetails().setUserCustomData(userCustomData);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setAppContext(String identifier) {
    raygunMessage.getDetails().setAppContext(identifier);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setUserContext(Context context) {
    raygunMessage.getDetails().setUserContext(context);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setNetworkInfo(Context context) {
    raygunMessage.getDetails().setNetworkInfo(context);
    return this;
  }

  @Override
  public IRaygunMessageBuilder setGroupingKey(String groupingKey) {
    raygunMessage.getDetails().setGroupingKey(groupingKey);
    return this;
  }

  public static RaygunMessageBuilder instance() {
    return new RaygunMessageBuilder();
  }
}
