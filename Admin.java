package Java_OOP_Assignment;

import java.io.*;
import java.util.*;

public class Admin extends User{
    
    String filePath = "C:\\Users\\Asus\\OneDrive - Asia Pacific University\\Documents\\NetBeansProjects\\Assignment\\Beta_Version\\Txt_Files\\Credentials\\User Credentials.Txt\\";
    
    public Admin(String userID, String userName, String password, String position){
        super(userID,userName,password,position);
    }
    
    @Override
    void menu(){
        boolean exit = false;
        do{
            Scanner sc = new Scanner(System.in); int choice;
            System.out.println("""
                               ==============
                                    Menu
                               ==============
                               1. Create user
                               2. Edit user
                               3. Delete user
                               4. View user

                               0. Exit
                               ===============
                               """);
            System.out.print("Choice: ");

            try{
                choice = sc.nextInt();
                switch (choice){
                case 1 -> addUser();
                case 2 -> editUser();
                case 3 -> deleteUser();
                case 4 -> viewUser();
                case 0 ->{
                    exit = true;
                    System.out.println("Logging out ...");
                }
                default -> System.out.println("Please enter a valid choice.");
                }
            } 
            catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
                sleep();
            }
        } while (exit != true);
    }
    
    public void addUser(){
        Scanner sc = new Scanner(System.in); int choice; String[] userInfo = new String[4]; int flag = 1;
        List<String[]> userArray = read_file();
      
        System.out.print("""
                           ============
                             New User
                           ============
                           """);
        System.out.print("Username: ");
        userInfo[1] = sc.nextLine();
        System.out.print("Password: ");
        userInfo[2] = sc.nextLine();
        System.out.print("""
                           ====================
                                 Position
                           ====================
                            1. Admin
                            2. Sales Manager
                            3. Purchase Manager
                           ====================
                           """);
        while (flag == 1){
            try{
                System.out.print("Choice: ");
                choice = sc.nextInt();
                userInfo[3] = switch (choice) {
                case 1 -> "Admin";
                case 2 -> "SM";
                case 3 -> "PM";
                default -> null;    
                };
                if (userInfo[3] == null){
                    System.out.println("Please enter a valid choice.");
                    sleep();
                }
                else
                    flag = 0;
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
                sleep();
            }          
        }
        
        String[] lastUser = userArray.get(userArray.size() - 1); String lastID = lastUser[0].substring(1);
        int newValue = Integer.parseInt(lastID) + 1; String newID = String.format("%02d", newValue);
        
        userInfo[0] = "U" + newID;

        try {
            try ( 
                // Create a FileWriter in append mode
                FileWriter fileWriter = new FileWriter(filePath, true); 
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
        int flag = 1; List<String[]> userArray = read_file(); 
        Scanner sc = new Scanner(System.in);
        
        while(flag == 1){
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
            
            try {
                // Create a BufferedWriter for efficient writing
                try ( 
                    FileWriter fileWriter = new FileWriter(filePath); 
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
                    try{
                        System.out.print("Choice: ");
                        int choice = sc.nextInt();
                        user[3] = switch (choice) {
                        case 1 -> "Admin";
                        case 2 -> "SM";
                        case 3 -> "PM";
                        default -> null;
                        };
                    }
                    catch(InputMismatchException e){
                    }
                    
                    if (user[3] == null)
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
            try {
                try ( 
                    // Create a FileWriter in append mode
                    FileWriter fileWriter = new FileWriter(filePath); 
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
        
        System.out.println();
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
    
    private List<String[]> read_file(){
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
        
        return userArray;
    }
}
