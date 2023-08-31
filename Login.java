package beta_version;

import java.util.*;
import java.io.*;

public class Login {
    
    String userID, userPass;
    
    public Login(){}
    
    public void enter_userInfo(){
        System.out.print("""
                            ===============
                            Login Interface
                            ===============
                            """);
        Scanner sc = new Scanner(System.in);
        System.out.print("User ID: ");
        userID = sc.nextLine();
        
        // sc.nextLine();
        
        System.out.print("Password: ");
        userPass = sc.nextLine();
    }

    public String[] check_account(){
        String fileName = "User Info"; String[] userInfo = new String[4] ;
        List<String[]> userArray = new ArrayList<>(); 
        
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] userCredentials = line.split("\\s+");
                userArray.add(userCredentials);
            }
        } 
        catch (IOException e) { 
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        int flag = 1;
        for (String[] user : userArray) {
            if (userID.equals(user[0])){
                flag = 0;
                
                if (userPass.equals(user[2])){
                    userInfo[0] = user[0];
                    userInfo[1] = user[1];
                    userInfo[2] = user[2];
                    userInfo[3] = user[3];
                    break;
                }
                else{
                    System.out.println("Incorrect Password.");
                    userInfo = null;
                    break;
                }
            }
        }

        if (flag == 1){
            System.out.println("Username does not exists.");
            return null;
        }
       
        return userInfo; 
    }
}
