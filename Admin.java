package beta_version;

import java.io.*;
import java.util.*;

public class Admin extends User{
    
    public Admin(String userID, String userName, String password, String position){
        super(userID,userName,password,position);
    }
    
    public void AdminMenu(){
        boolean not_exit = true;
        do{
        Scanner sc = new Scanner(System.in); int choice;
        System.out.println("""
                           ====
                           Menu
                           ====
                           1. Create user
                           2. Edit user
                           3. Delete user
                           4. View user
                           
                           5. Exit
                           """);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        switch (choice){
            case 1 -> {
                addUser();
            }
                
            case 2 -> {
                deleteUser();
            }
            
            case 3 -> {
                editUser();
            }
            
            case 4 -> {
                viewUser();
            }
            
            case 5 ->{
                not_exit = false;
                System.out.println("Logging out ...");
            }
            
            default -> {
                System.out.println("Please enter a valid choice.");
            }
            
        }
        
        } while (not_exit);
    }
    
    public void addUser(){
        Scanner sc = new Scanner(System.in); int choice; String[] userInfo = new String[4]; int flag = 1;
        String fileName = "User Info";
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
        
        System.out.println("""
                           ========
                           New User
                           ========
                           """);
        System.out.print("Username: ");
        userInfo[1] = sc.nextLine();
        System.out.print("Password: ");
        userInfo[2] = sc.nextLine();
        System.out.println("""
                           Position
                           1. Admin
                           2. Sales Manager
                           3. Purchase Manager
                           
                           Choice: 
                           """);
        while (flag == 1){
            choice = sc.nextInt();
            userInfo[3] = switch (choice) {
            case 1 -> "Admin";
            case 2 -> "SM";
            case 3 -> "PM";
            default -> "X";
            };
        
            if (userInfo[3].equals("X"))
                System.out.println("Please enter a valid choice.");
            else
                flag = 0;           
        }
        
        String[] last_user = userArray.get(userArray.size() - 1);
        System.out.println("Last User ID: " + last_user[0]);
        System.out.print("New User ID: ");
        sc.nextLine();
        userInfo[0] = sc.nextLine();

        try {
            // Create a FileWriter in append mode
            FileWriter fileWriter = new FileWriter(fileName, true);

            // Create a BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the userInfo array to the file
            bufferedWriter.newLine();
            for (String data : userInfo) {
                bufferedWriter.write(data + " ");
            }
            
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("User information appended to the file.");
        } 
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        
    }
    
    public void deleteUser(){
        System.out.println("Delete User");
    }
    
    public void editUser(){
        System.out.println("Edit User");
    }
    
    public void viewUser(){
        String fileName = "User Info";
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
        
        int i = 1; String string = "=".repeat(50);
        for (String[] user : userArray) {
            if (i == 1){
                 System.out.format("""
                               %s    
                               %-7s %-10s %-10s %-10s %-10s
                               %s
                               """,string, "USER", "ID", "Name", "Position", "Password", string);
            }

            
            System.out.format("User %d: %-10s %-10s %-10s %-10s \n", i, user[0], user[1], user[3], user[2]);
            i++;
        }
        
        System.out.format("%s \n\n", string);
    } 
}
