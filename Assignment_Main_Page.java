package Java_OOP_Assignment;

public class Assignment_Main_Page{

    public static void main(String[] arg){ 
        Login login = new Login();
        
        login.enter_userInfo();
        String[] userCredentials = login.check_account();
        
        if (userCredentials == null){
            System.out.print("Please try again.");
        }
        else{
            int userPosition = switch (userCredentials[3]) {
            case "Admin" -> 1;
            case "SM" -> 2;
            case "PM" -> 3;
            default -> 4;    
            };
            
            switch (userPosition){
            case 1 -> {
                System.out.println("\nWelcome Admin! \n");
                User admin = new Admin( userCredentials[0],userCredentials[1],userCredentials[2],userCredentials[3] );
                admin.menu();
            }
            case 2 -> {
                System.out.println(" \nWelcome Sales Manager! \n");
                User salesManager = new SalesManager( userCredentials[0],userCredentials[1],userCredentials[2],userCredentials[3] );
                salesManager.menu();
            }
            case 3 -> {
                System.out.println("\nWelcome Purchase Manager! \n");
                User purchaseManager = new PurchaseManager( userCredentials[0],userCredentials[1],userCredentials[2],userCredentials[3] );
                purchaseManager.menu();
            }
            case 4 -> System.out.println("Case 4. Something went wrong, please try it again.");
            
            }
            System.out.println("Thanks for using the app.");
        } 
    }
}

