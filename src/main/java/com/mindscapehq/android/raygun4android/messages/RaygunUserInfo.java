package main.java.com.mindscapehq.android.raygun4android.messages;

public class RaygunUserInfo
{
  public Boolean IsAnonymous;

  public String Email;

  public String FullName;

  public String FirstName;

  public String LastName;

  public String Uuid;

  public String Identifier;

  /**
   * Set the current user's info to be transmitted - any parameters can be null if the data is not available or
   * you do not wish to send it.
   * @param firstName The user's first name
   * @param lastName The user's last name
   * @param fullName If setting their name, you can set this too
   * @param emailAddress User's email address
   * @param uuid Device identifier - if this is null we will attempt to generate it automatically (legacy behavior).
   * @param isAnonymous Whether this user data represents an anonymous user
   * @param identifier Unique identifier for this user. Set this to the internal identifier you use to look up users,
   *                   or a correlation ID for anonymous users if you have one. It doesn't have to be unique, but we will treat
   *                   any duplicated values as the same user. If you use their email address here, pass it in as the 'emailAddress' parameter too.
   * @return HTTP result code - 202 if successful, 403 if API key invalid, 400 if bad message (invalid properties)
   */
  public RaygunUserInfo(String identifier, String firstName, String lastName, String fullName, String emailAddress, String uuid, Boolean isAnonymous)
  {
    FirstName = firstName;
    LastName = lastName;
    FullName = fullName;
    Email = emailAddress;
    Uuid = uuid;
    IsAnonymous = isAnonymous;
    Identifier = identifier;
  }

  public RaygunUserInfo() { }
}