package beta_version;

import java.util.Scanner;

public class SalesManager extends User{
    
    public SalesManager(String userID, String userName, String password, String position) {
        super(userID, userName, password, position);
    }
    
    @Override
    void user_menu(){
        boolean not_exit = true;
        do{
        Scanner sc = new Scanner(System.in); int choice;
        System.out.println("""
                           =======
                           SM Menu
                           =======
                           1. Item Menu
                           2. Requisition Menu
                           3. Generate Report
                           
                           4. Exit
                           """);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        switch (choice){
            case 1 -> {
                itemMenu();
            }
                
            case 2 -> {
                requisitionMenu();
            }
            
            case 3 -> {
               generateReport();
            }
                   
            case 4 ->{
                not_exit = false;
                System.out.println("Logging out ...");
            }
            
            default -> {
                System.out.println("Please enter a valid choice.");
            }
            
        }
        
        } while (not_exit);
    }
    
    public void itemMenu(){
        System.out.println("Item Menu");
    }
    
    public void requisitionMenu(){
        System.out.println("Requisition Menu");
    }
    
    public void generateReport(){
        System.out.println("Generate Report");
    }
}
