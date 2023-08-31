package beta_version;

import java.io.*;
import java.util.*;

public class Supplier{
    public Supplier(){}
    
    public void searchSupplier(String itemID){
        List<String[]> supplierArray = read_file();int flag = 0; String string = "=".repeat(30);
        
        System.out.format("""
                        %s
                        Supplier List for Item %s
                        %s
                        """, string, itemID, string);
        
        for(String[] supplier: supplierArray){
            if (itemID.equals(supplier[2])){
                flag = 1;
                System.out.format("""
                                  ID        : %s
                                  Name      : %s
                                  Item Sold : %s
                                  State     : %s
                                  """, supplier[0], supplier[1], supplier[3], supplier[4]);
            }
        }
            
        if (flag == 0){
                System.out.println("No Supplier supply Item " + itemID + ".");
        }    
    }
    
    public boolean checkSupplier(String itemID){
        List<String[]> supplierArray = read_file();
        for(String[] supplier: supplierArray){
            if (itemID.equals(supplier[2])){
                return true;
            }
        }
        return false;        
    }
    
    public void addSupplier(){
        Scanner sc = new Scanner(System.in); String[] supplierInfo = new String[5];
        List<String[]> supplierArray = read_file();
      
        System.out.println("""
                           ============
                           New Supplier
                           ============
                           """);
        System.out.print("Supplier Name: ");
        supplierInfo[1] = sc.nextLine();
        System.out.print("Item Provided: ");
        supplierInfo[2] = sc.nextLine();
        System.out.print("Price: ");
        supplierInfo[3] = sc.nextLine();
        System.out.print("State: ");
        supplierInfo[4] = sc.nextLine();
        
        String[] lastSupplier = supplierArray.get(supplierArray.size() - 1);
        String numericPart = lastSupplier[0].substring(2);
        int numericValue = Integer.parseInt(numericPart) + 1;
        String newNumericPart = String.format("%02d", numericValue);
        supplierInfo[0] = "S" + newNumericPart;

        String fileName = "Supplier Info";
        try {
            try ( 
                // Create a FileWriter in append mode
                FileWriter fileWriter = new FileWriter(fileName, true); 
                // Create a BufferedWriter for efficient writing
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                // Write the userInfo array to the file
                bufferedWriter.newLine();
                for (String data : supplierInfo) {
                    bufferedWriter.write(data + " ");
                }     
            }

            System.out.println("Supplier information appended to the file.");
        } 
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } 
        
        sleep();
    }
    
    public void editSupplier(){
        List<String[]>supplierArray = read_file();
        Scanner sc = new Scanner(System.in);
        
        viewSupplier();
        
        System.out.print("Which ID to edit? : ");
        String requiredID = sc.nextLine();
        
        int check = 0;
        for (String[] supplier: supplierArray){ 
            if (requiredID.equals(supplier[0])){
                check = 1;
                System.out.format("""
                                  \nPrevious Credentials:
                                  1. ID               : %s
                                  2. Name             : %s
                                  3. Item ID Provided : %s
                                  4. Price            : %s
                                  5. State            : %s\n
                                  """, supplier[0], supplier[1], supplier[2], supplier[3], supplier[4]);
                int flag = 1, choice;
                while(flag == 1){
                    System.out.print("""
                                     Which credential to edit ?: 
                                        1. Name
                                        2. Item ID
                                        3. Price
                                        4. State 
                                     """);
                    System.out.print("Choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice){
                        case 1 -> {
                            System.out.print("New Name: ");
                            supplier[1] = sc.nextLine();
                        }
                        case 2->{
                            System.out.print("New Item ID: ");
                            supplier[2] = sc.nextLine();
                        }
                        case 3->{
                            System.out.print("New Price: ");
                            supplier[3] = sc.nextLine();
                        }
                        case 4->{
                            System.out.print("New State: ");
                            supplier[4] = sc.nextLine();
                        }
                        default->{
                            System.out.print("Please enter a valid choice");
                            return;
                        }
                    }
                    
                    String fileName = "Supplier Info";
                    try {
                        try ( 
                            // Create a FileWriter in append mode
                            FileWriter fileWriter = new FileWriter(fileName); 
                            // Create a BufferedWriter for efficient writing
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                                // Write the userInfo array to the file
                                for (String[] user : supplierArray) {
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
                    
                    System.out.print("Continue editing (Y/N)? : ");
                    char decision = sc.next().charAt(0);
                    if (!Objects.equals(decision, "Y") || !Objects.equals(decision, "y")){
                        flag = 0;
                    }
                }
            }
        }
        
        if (check == 0){
            System.out.println("ID does not exists. Please enter a valid ID.\n");
        }
    }
    
    public void deleteSupplier(){
        List<String[]>supplierArray = read_file(); int flag = 1;
        Scanner sc = new Scanner(System.in);
        
        while(flag == 1){
            viewSupplier();
            System.out.print("Supplier to delete: ");
            String deleteID = sc.nextLine();
        
            int i = 0, check = 0;
            for (String[] user: supplierArray){
                if (deleteID.equals(user[0])){
                    supplierArray.remove(i);
                    check = 1;
                    break;
                }
                else{
                    i++;
                }
            }
            
            if (check == 0){
                System.out.print("\nSupplier not found. Please enter an exist supplier ID.\n");
                sleep();
                return;
            }
            
            String fileName = "Supplier Info";
            try {
                // Create a BufferedWriter for efficient writing
                try ( 
                    FileWriter fileWriter = new FileWriter(fileName); 
                    // Create a BufferedWriter for efficient writing
                    var bufferedWriter = new BufferedWriter(fileWriter)) {
                    // Write the userInfo array to the file
                    for (String[] data : supplierArray) {
                        String line = String.join(" ", data);
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }
            } 
            catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        
            System.out.print("\nNew Supplier List: \n");
            viewSupplier();
            System.out.print("Delete another Supplier (Y/N) ? : ");
            char choice = sc.next().charAt(0);
        
            if (!Objects.equals(choice, "Y") || !Objects.equals(choice, "y")){
                flag = 0;
            } 
        }  
    }
    
    public void viewSupplier(){
        List<String[]>supplierArray = read_file();
        
        int i = 1; String string = "=".repeat(62);
        for (String[] supplier : supplierArray) {
            if (i == 1){
                 System.out.format("""
                               %s    
                               %-11s %-10s %-10s %-10s %-10s %-10s
                               %s
                               """,string, "SUPPLIER", "ID", "Name", "ITEM ID", "ITEM PRICE", "State", string);
            }

            
            System.out.format("Supplier %d: %-10s %-10s %-10s %-10s %-10s\n", i, supplier[0], supplier[1], supplier[2], supplier[3], supplier[4]);
            i++;
        }
        
        System.out.format("%s \n\n", string);
    }
    
    private List<String[]> read_file(){
        List<String[]> supplierArray = new ArrayList<>();
        String fileName = "Supplier Info";
        
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] supplierCredentials = line.split("\\s+");
                supplierArray.add(supplierCredentials);
            }
        } 
        catch (IOException e) { 
            System.err.println("Error reading file: " + e.getMessage());
        }
        return supplierArray;
    } 
    
    private void sleep(){
         try {
            // Sleep for 2 seconds (2000 milliseconds)
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }
    }
}
