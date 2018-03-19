package main.java.com.mindscapehq.android.raygun4android.messages;

public class RaygunErrorMessage {
  private RaygunErrorMessage innerError;
  private String message;
  private String className;
  private RaygunErrorStackTraceLineMessage[] stackTrace;

  public RaygunErrorMessage(Throwable throwable) {
    message = throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
    className = throwable.getClass().getCanonicalName();

    if (throwable instanceof Error && message.contains("signal"))
    {
       String[] parts = message.split("\n");
       message = parts[0];
    }

    if (throwable.getCause() != null) {
      innerError = new RaygunErrorMessage(throwable.getCause());
    }

    StackTraceElement[] ste = throwable.getStackTrace();
    stackTrace = new RaygunErrorStackTraceLineMessage[ste.length];

    for (int i = 0; i < ste.length; i++) {
      stackTrace[i] = new RaygunErrorStackTraceLineMessage(ste[i]);
    }
  }

  public RaygunErrorMessage getInnerError() {
    return innerError;
  }

  public void setInnerError(RaygunErrorMessage innerError) {
    this.innerError = innerError;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public RaygunErrorStackTraceLineMessage[] getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(RaygunErrorStackTraceLineMessage[] stackTrace) {
    this.stackTrace = stackTrace;
  }
}
