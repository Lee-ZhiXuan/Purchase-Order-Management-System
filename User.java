public class User {
    private String userID;
    private String userName;
    private String password;
    private int position;

    // Constructor
    public User(String A, String B, String C, int D) {
        this.userID = A;
        this.userName = B;
        this.password = C;
        this. position = D;
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

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}



