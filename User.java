
package beta_version;

public class User {

    public String userID, userName, password, position;
    
    // Constructor
    public User(String userID, String userName, String password, String position) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.position = position;

    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

