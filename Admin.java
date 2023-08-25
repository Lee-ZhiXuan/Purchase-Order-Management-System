package beta_version;

import java.io.*;
import java.util.*;

public class Admin extends User{
    
    public Admin(String userID, String userName, String password, String position){
        super(userID,userName,password,position);
    }
    
    @Override
    void user_menu(){
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
                editUser();
            }
            
            case 3 -> {
                deleteUser();
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
        List<String[]> userArray = read_file();
      
        System.out.println("""
                           ========
                           New User
                           ========
                           """);
        System.out.print("Username: ");
        userInfo[1] = sc.nextLine();
        System.out.print("Password: ");
        userInfo[2] = sc.nextLine();
        System.out.print("""
                           Position
                            1. Admin
                            2. Sales Manager
                            3. Purchase Manager
                           """);
        while (flag == 1){
            System.out.print("Choice: ");
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
        String numericPart = last_user[0].substring(1);
        int numericValue = Integer.parseInt(numericPart) + 1;
        String newNumericPart = String.format("%02d", numericValue);
        userInfo[0] = "U" + newNumericPart;

        String fileName = "User Info";
        try {
            try ( 
                // Create a FileWriter in append mode
                FileWriter fileWriter = new FileWriter(fileName, true); 
                // Create a BufferedWriter for efficient writing
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                // Write the userInfo array to the file
                bufferedWriter.newLine();
                for (String data : userInfo) {
                    bufferedWriter.write(data + " ");
                }     
            }

            System.out.println("User information appended to the file.");
        } 
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } 
        
        sleep();
    }
    
    public void deleteUser(){
        int flag = 1;
        while(flag == 1){
            Scanner sc = new Scanner(System.in);
            List<String[]> userArray = read_file();
            viewUser();
            System.out.print("User to delete: ");
            String deleteID = sc.nextLine();
        
            int i = 0, check = 0;
            for (String[] user: userArray){
                if (deleteID.equals(user[0])){
                    userArray.remove(i);
                    check = 1;
                    break;
                }
                else{
                    i++;
                }
            }
            
            if (check == 0){
                System.out.print("\nUser not found. Please enter an exist user ID.\n");
                sleep();
                return;
            }
            
            String fileName = "User Info";
            try {
                // Create a BufferedWriter for efficient writing
                try ( 
                    FileWriter fileWriter = new FileWriter(fileName); 
                    // Create a BufferedWriter for efficient writing
                    var bufferedWriter = new BufferedWriter(fileWriter)) {
                    // Write the userInfo array to the file
                    for (String[] data : userArray) {
                        String line = String.join(" ", data);
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }
            } 
            catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        
            System.out.print("\nNew User List: \n");
            viewUser();
            System.out.print("Delete another user (Y/N) ? : ");
            char choice = sc.next().charAt(0);
        
            if (!Objects.equals(choice, "Y") || !Objects.equals(choice, "y")){
                flag = 0;
            }         
        }
    }
    
    public void editUser(){
        Scanner sc = new Scanner(System.in);
        List<String[]> userArray = read_file();
        
        viewUser();
        
        System.out.print("Which ID to edit? : ");
        String requiredID = sc.nextLine();
        
        int check = 0;
        for (String[] user: userArray){ 
            if (requiredID.equals(user[0])){
                check = 1;
                System.out.format("""
                                  \nPrevious Credentials:
                                  1. ID: %s
                                  2. Name: %s
                                  3. Password: %s
                                  4. Role: %s\n
                                  """, user[0], user[1], user[2], user[3]);
                System.out.println("New Credentials: ");
                System.out.print("1. Name: ");
                user[1] = sc.nextLine();
                System.out.print("2. Password: ");
                user[2] = sc.nextLine();
                System.out.println("3. Role: ");
                System.out.print("""
                            1. Admin
                            2. Sales Manager
                            3. Purchase Manager
                           """);
                int flag = 1;
                while (flag == 1){
                    System.out.print("Choice: ");
                    int choice = sc.nextInt();
                    
                    user[3] = switch (choice) {
                    case 1 -> "Admin";
                    case 2 -> "SM";
                    case 3 -> "PM";
                    default -> "X";
                    };
                    
                    if (user[3].equals("X"))
                        System.out.println("Please enter a valid choice.");
                    else
                        flag = 0;
                }         
            }
        }
        
        if (check == 0){
            System.out.println("ID does not exists. Please enter a valid ID.\n");
        }
        else{
            String fileName = "User Info";
            try {
                try ( 
                    // Create a FileWriter in append mode
                    FileWriter fileWriter = new FileWriter(fileName); 
                    // Create a BufferedWriter for efficient writing
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                        // Write the userInfo array to the file
                        for (String[] user : userArray) {
                            String line = String.format("%s %s %s %s%n", user[0], user[1], user[2], user[3]);
                            bufferedWriter.write(line);   
                        }
                        bufferedWriter.close();
                    }
                System.out.println("User information appended to the file.");
            } 
            catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            } 
            sleep();
        }
    }
    
    public void viewUser(){
        List<String[]> userArray = read_file();
        
        int i = 1; String string = "=".repeat(50);
        for (String[] user : userArray) {
            if (i == 1){
                 System.out.format("""
                               %s    
                               %-7s %-10s %-10s %-10s %-10s
                               %s
                               """,string, "USER", "ID", "Name", "Password", "Role", string);
            }

            
            System.out.format("User %d: %-10s %-10s %-10s %-10s \n", i, user[0], user[1], user[2], user[3]);
            i++;
        }
        
        System.out.format("%s \n\n", string);
    } 
    
    public List<String[]> read_file(){
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
        
        return userArray;
    }
}
