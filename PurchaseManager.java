package beta_version;

import java.util.*;

public class PurchaseManager extends User{
    
    Scanner sc = new Scanner(System.in);
    
    PurchaseReq pr = new PurchaseReq(); PurchaseOrder po = new PurchaseOrder(); Supplier sp = new Supplier(); Item i = new Item();
    
    public PurchaseManager(String userID, String userName, String password, String position) {
        super(userID, userName, password, position);
    }

    @Override
    void menu(){
        boolean exit = false;
        
        do{
        System.out.println("""
                           =============================
                                      PM Menu
                           =============================
                           1. View Item
                           2. Supplier Menu
                           3. Purchase Requisition Menu
                           4. Purchase Order Menu
                           
                           0. Return
                           =============================
                           """);
        System.out.print("Choice: ");
        
        try{
            int choice = sc.nextInt();
            switch (choice){
                case 1 -> i.view();
                case 2 -> supplierList();           
                case 3 -> purchaseRequisitionMenu();            
                case 4 -> purchaseOrderMenu();          
                case 0 ->{
                    exit = true;
                    System.out.println("Logging out ...");
                }
                default -> System.out.println("Please enter a valid choice.");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter an integer.");
        }
        
        } while (exit != true);
    }
    
    public void supplierList(){
        int choice; boolean exit = false;
         do{

            System.out.println("""
                       =============
                       Supplier Menu
                       =============
                       1. View
                       2. Create
                       3. Edit
                       4. Delete

                       0. Return
                       =============
                       """);
            System.out.print("Choice: ");
            
            try{
                choice = sc.nextInt();
                switch(choice){
                    case 0 -> exit = true;
                    case 1 -> sp.viewSupplier();
                    case 2 -> sp.addSupplier();
                    case 3 -> sp.editSupplier();
                    case 4 -> sp.deleteSupplier();
                    default -> System.out.println("Please enter a valid input.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
            }
            
        }while(exit != true);
    }
    
    public void purchaseRequisitionMenu(){
        boolean exit  = false;
        
        do{
           System.out.println("""
                           =========================
                           Purchase Requisition Menu
                           =========================
                           1. View
                           2. Approval
                           
                           0. Return
                           =========================
                           """);
            System.out.print("Choice: ");
            
            try{
                int choice = sc.nextInt();
                switch (choice){
                    case 1 -> pr.view();
                    case 2 -> pr.ReqApproval();
                    case 0 -> exit = true;
                    default -> System.out.println("Please enter a valid choice.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
            }
            
        }while (exit != true);
        
    }
    
    public void purchaseOrderMenu(){
        boolean exit = false;
        do{
            System.out.println("""
                       ===================
                       Purchase Order Menu
                       ===================
                       1. View
                       2. Create
                       3. Edit
                       4. Delete

                       0. Return
                       ===================
                       """);
            System.out.print("Choice: ");
            
            try{
                int choice = sc.nextInt();
                switch (choice){
                    case 0 -> exit = true;
                    case 1 -> po.view();
                    case 2 -> po.create(getID());
                    case 3 -> po.edit(getID());
                    case 4 -> po.delete();
                    default -> System.out.println("Please enter a valid choice.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter an integer.");
                sleep(); 
            }
            
        }while(exit != true);
    }
}
