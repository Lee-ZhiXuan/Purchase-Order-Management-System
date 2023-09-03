package Java_OOP_Assignment;

public abstract class User {

    private String userID, userName, password, position;
    
    // Constructor
    public User(){}

    public User(String userID, String userName, String password, String position) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.position = position;
    }
   
    public String getID(){
        return this.userID;  
    }
    
    public void sleep(){
        try {
            // Sleep for 2 seconds (2000 milliseconds)
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) {
        }
    }
    
    abstract void menu();
}

