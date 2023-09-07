package Java_OOP_Assignment;

import java.util.*;

public class SalesManager extends User{
    
    PurchaseReq pr = new PurchaseReq(); Supplier sp = new Supplier(); Item i = new Item(); DailySales ds = new DailySales();
    Scanner sc = new Scanner(System.in);
    
    public SalesManager(){};
    public SalesManager(String userID, String userName, String password, String position) {
        super(userID, userName, password, position);
    }
    
    @Override
    void menu(){
        boolean  exit = false;
        
        do{           
            System.out.println("""
                               ======================
                                 Sales Manager Menu
                               ======================
                               1. Item Menu
                               2. Daily Sales Menu
                               3. View Supplier
                               4. Requisition Menu

                               0. Return
                               =====================
                               """);
            System.out.print("Choice: ");
            
            try{
                int choice = sc.nextInt();
                switch (choice){
                    case 1 -> itemMenu();
                    case 2 -> dailySalesMenu(); 
                    case 3 -> sp.viewSupplier();
                    case 4 -> requisitionMenu();                                            
                    case 0 ->{
                        exit = true;
                        System.out.println("Logging out ...");
                    }
                    default -> System.out.println("Please enter a valid choice.");
                }               
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
            }
            
        } while (exit != true);
    }
    
    public void itemMenu(){
        boolean  exit = false;  
        do{
            System.out.println("""
                               =================
                                   Item Menu
                               =================
                               1. View
                               2. Create
                               3. Edit
                               4. Delete

                               0. Return
                               =================
                               """);
            System.out.print("Choice: ");
            
            try{
                int choice = sc.nextInt();
                switch (choice){
                    case 1 -> i.view();
                    case 2 -> i.create();
                    case 3 -> i.edit();
                    case 4 -> i.delete();
                    case 0 -> exit = true;
                    default -> System.out.println("Please enter a valid choice.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
            }
            
        } while (exit != true);
    }
    
    public void requisitionMenu(){
        boolean  exit = false;  
        do{
            System.out.println("""
                               ===========================
                                     Requisiton Menu
                               ===========================
                               1. View
                               2. Create
                               3. Edit
                               4. Delete

                               0. Return
                               ===========================
                               """);
            System.out.print("Choice: ");
            
            try{
                int choice = sc.nextInt();
                switch (choice){
                    case 1 -> pr.view();
                    case 2 -> pr.create(getID());
                    case 3 -> pr.edit(getID());
                    case 4 -> pr.delete();
                    case 0 -> exit = true;
                    default -> System.out.println("Please enter a valid choice.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
            }
            
        } while (exit != true);
    }
    
    public void dailySalesMenu(){
        boolean  exit = false;  
        do{
            System.out.println("""
                               ====================
                                 Daily Sales Menu
                               ====================
                               1. View
                               2. Create
                               3. Edit
                               4. Delete

                               0. Return
                               ====================
                               """);
            System.out.print("Choice: ");
            
            try{
                int choice = sc.nextInt();
                switch (choice){
                    case 1 -> ds.view();
                    case 2 -> ds.create();
                    case 3 -> ds.edit();
                    case 4 -> ds.delete();
                    case 0 -> exit = true;
                    default -> System.out.println("Please enter a valid choice.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
            }
            
        } while (exit != true);
    }
}
