import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("System started.");

        Scanner Sc = new Scanner(System.in);
        int selection = 0;

        do {
            //Print header
            System.out.println("\n=========================");
            System.out.println("Testing Menu");
            System.out.println("=========================");

            System.out.println("View Requisitions: 1");
            System.out.println("Create new Requisition: 2");
            System.out.println("Edit Requisition: 3");
            System.out.println("Remove Requisition: 4");
            System.out.println("Exit: 0");
            System.out.print("\nSelection: ");
            selection = Sc.nextInt();
            Sc.nextLine();
            System.out.println();

            switch (selection) {
                case 0 -> System.out.println("Closing program.");
                case 1 -> PurchaseReq.ViewPurchaseReq();
                case 2 -> PurchaseReq.AddPurchaseReq();
                case 3 -> PurchaseReq.EditPurchaseReq();
                case 4 -> PurchaseReq.RemovePurchaseReq();
                default -> System.out.println("Invalid Selection, please try again.");
            }
        } while (selection != 0);
    }
}

