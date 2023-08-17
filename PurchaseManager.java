package beta_version;

import java.util.Scanner;

public class PurchaseManager extends User{
    
    public PurchaseManager(String userID, String userName, String password, String position) {
        super(userID, userName, password, position);
    }
    
    public void PmMenu(){
        boolean not_exit = true;
        do{
        Scanner sc = new Scanner(System.in); int choice;
        System.out.println("""
                           =======
                           PM Menu
                           =======
                           1. Item List
                           2. Manage Supplier
                           3. Purchase Requisition
                           4. Purchase Order
                           
                           5. Exit
                           """);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        switch (choice){
            case 1 -> {
               itemList();
            }
                
            case 2 -> {
               supplierList();
            }
            
            case 3 -> {
               requisitionMenu();
            }
            
            case 4 -> {
               purchaseOrder();
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
    
    public void itemList(){
        System.out.println("Item List");
    }
    
    public void supplierList(){
        System.out.println("Supplier Menu");
    }
    
    public void requisitionMenu(){
        System.out.println("Requisition Menu");
    }
    
    public void purchaseOrder(){
        System.out.println("Purchase Order");
    }
}
