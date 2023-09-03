package Java_OOP_Assignment;

import java.util.*;
import java.io.*;

public class Login {
    
    String userID, userPass;
    String filePath = "C:\\Users\\Asus\\OneDrive - Asia Pacific University\\Documents\\NetBeansProjects\\Assignment\\Beta_Version\\Txt_Files\\Credentials\\User Credentials.txt\\";
    
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
        System.out.print("Password: ");
        userPass = sc.nextLine();
    }

    public String[] check_account(){
        List<String[]> userArray = new ArrayList<>(); 
        
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
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
                
                if (userPass.equals(user[2]))
                    return user;
                else{
                    System.out.println("Incorrect Password.");
                    break;
                }
            }
        }

        if (flag == 1){
            System.out.println("Username does not exists.");
        }
        
        return null;
    }
}
