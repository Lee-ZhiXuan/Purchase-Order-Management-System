package beta_version;

import java.util.*;
import java.io.*;

class PurchaseReq implements SalesObject{
    private String reqID;
    private int reqQuantity;
    private int reqStatus;
    private String reqDate;
    private String itemID;

    final static String filePath = "C:\\Users\\Asus\\OneDrive - Asia Pacific University\\Documents\\NetBeansProjects\\Assignment\\Beta_Version\\Txt_Files\\Item\\Requisition.Txt\\";
    final static String filePath2 = "C:\\Users\\Asus\\OneDrive - Asia Pacific University\\Documents\\NetBeansProjects\\Assignment\\Beta_Version\\Txt_Files\\Item\\Requisition_Buffer.Txt\\";
    
    // Constructor
    public PurchaseReq(String ReqID, String ItemID, int ReqQuantity, int ReqStatus, String ReqDate) {
        this.reqID = ReqID;
        this.reqQuantity = ReqQuantity;
        this.reqStatus = ReqStatus;
        this.reqDate = ReqDate;
        this.itemID = ItemID;

    }

    public PurchaseReq() {}

    // Getters and Setters
    public String getReqID() {
        return reqID;
    }
    public int getReqQuantity() {
        return reqQuantity;
    }
    public int getReqStatus() {
        return reqStatus;
    }
    public String getReqDate() {
        return reqDate;
    }
    public String getItemID() {
        return itemID;
    }

    Scanner Sc = new Scanner(System.in);


    // View Purchase Requisition Method
    @Override
    public void view() {
        Scanner sc = new Scanner(System.in);
        displayReq("View Requisition List");
        System.out.print("\nPress Enter to return...");
        sc.nextLine();
        System.out.println();
    }


    // Create Purchase Requisition Method
    public void create(String userID) {
        // String reqID; int reqQuantity; String reqDate; String itemID;
        int selection;

        do {
            //Print header
            System.out.println("\n===========================");
            System.out.println("Create Purchase Requisition");
            System.out.println("===========================");
            System.out.println("Create new Requisition: 1");
            System.out.println("Back: 0");

            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }

            Sc.nextLine();
            System.out.println();

            switch (selection) {
                case 1 -> {
                    System.out.print("Enter Requisition ID: ");
                    reqID = Sc.nextLine();
                    System.out.print("Enter Item ID: ");
                    itemID = Sc.nextLine();
                    System.out.print("Enter Requisition Quantity: ");
                    reqQuantity = Sc.nextInt();
                    Sc.nextLine();
                    System.out.print("Enter Requisition Date (DD-MM-YYYY): ");
                    reqDate = Sc.nextLine();
                    PurchaseReq req = new PurchaseReq(reqID, itemID, reqQuantity, 1, reqDate);
                    
                    try {
                        try (FileWriter tfw = new FileWriter(filePath, true)) {
                            tfw.write("\n" + req.getReqID() + " " + req.getItemID() + " " + req.getReqQuantity() + " " + req.getReqStatus() + " " + req.getReqDate() + " " + userID);
                        }
                    } catch (IOException e) {
                        System.out.println("\nAn error occurred.");
                    }   System.out.println("\nPurchase requisition successfully created.");
                    System.out.print("\nPress Enter to continue...");
                    Sc.nextLine();
                    System.out.println();
                }
                case 0 -> System.out.println("Back to previous menu.\n");
                default -> System.out.println("Invalid Selection, please try again.");
            }
        } while (selection != 0);
    }
    
    @Override
    public void create() {}

    // Edit Purchase Requisition Method
    public void edit(String userID) {
        int count = displayReq("Edit Purchase Requisitions");
        int selection;

        do {
            System.out.println("Select the purchase requisition you wish to edit.");
            System.out.println("Back: 0");

            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else if (selection < 1 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            } else {
                selectedDisplayReq(selection);

                //Call edit field method
                System.out.print("Enter Requisition ID: ");
                String newReqID = Sc.nextLine();
                System.out.print("Enter Item ID: ");
                String newItemID = Sc.nextLine();
                System.out.print("Enter Requisition Quantity: ");
                int newReqQuantity = Sc.nextInt();
                Sc.nextLine();
                System.out.print("Enter new date (DD-MM-YYYY): ");
                String newReqDate = Sc.nextLine();
                System.out.println("\nPending  = 1");
                System.out.println("Approved = 2");
                System.out.println("Rejected = 3");
                System.out.print("Enter Requisition Status: ");
                int newReqStatus = Sc.nextInt();
                Sc.nextLine();

                PurchaseReq.EditFileLine(selection, newReqID, newItemID, newReqQuantity, newReqStatus, newReqDate, userID);
                ReplaceReq();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }
    
    @Override
    public void edit() {}
    
    // Edit method
    static void EditFileLine(int selection, String reqID, String itemID, int reqQuantity, int reqStatus, String reqDate, String userID) {  
        try (BufferedReader rd = new BufferedReader(new FileReader(filePath));
             BufferedWriter wr = new BufferedWriter(new FileWriter(filePath2))) {

            String line;
            int count = 0;

            while ((line = rd.readLine()) != null) {
                if (count != 0) {
                    wr.newLine();
                }
                if (count != selection) {
                    wr.write(line);
                } else {
                    wr.write(reqID + " " + itemID + " " + reqQuantity + " " + reqStatus + " " + reqDate + " " + userID);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    // Delete Purchase Requisition Method
    @Override
    public void delete() {
        int count = displayReq("Remove Purchase Requisition");
        int selection;

        do {
            System.out.println("Select the purchase requisition you wish to remove.");
            System.out.println("Back: 0");
            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else if (selection < 1 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            } else {
                selectedDisplayReq(selection);

                //Call delete field method
                System.out.println("Do you wish to delete the selected purchase requisition?");
                System.out.println("Type out YES to confirm.");
                System.out.print("\nConfirmation: ");
                String confirmation = Sc.nextLine();
                if (Objects.equals(confirmation, "YES")) {
                    DeleteFileLine(selection);
                    ReplaceReq();
                } else {
                    System.out.println("Deletion cancelled.");
                }
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }


    // Delete Method
    static void DeleteFileLine(int selection) {
        try (BufferedReader rd = new BufferedReader(new FileReader(filePath));
             BufferedWriter wr = new BufferedWriter(new FileWriter(filePath2))) {

            String line;
            int count = 0;

            while ((line = rd.readLine()) != null) {
                if (count != selection) {
                    if (count != 0) {
                        wr.newLine();
                    }
                    wr.write(line);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    // Replace Actual with Carry
    static void ReplaceReq() {
        //Delete old requisition file
        File del = new File(filePath);
        if (del.delete()) {
            System.out.print("\n");
        } else {
            System.out.println("Error.");
        }

        //Rename carry requisition
        String oldFilePath = filePath2;
        String newFilePath = filePath;

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        boolean renamed = oldFile.renameTo(newFile);

        if (renamed) {
            System.out.print("Changes application successful.");
        } else {
            System.out.println("Error.");
        }
    }

    // Purchase Requisition Approval
    public void ReqApproval() {
        // String reqID = null; int reqQuantity = 0; int reqStatus; String reqDate = null; String itemID = null; 
        String userID = null;
        int count = selectiveDisplayReq("Handle Purchase Requisition", 1, "Pending-");
        int selection; int selection2 = 0;

        do {
            System.out.println("Select the purchase requisition you wish to handle.");
            System.out.println("Back: 0");
            try {
                System.out.print("\nSelection: ");
                selection = Sc.nextInt();
            } catch (InputMismatchException e) {
                selection = 999;
            }
            Sc.nextLine();

            if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else if (selection < 1 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            } else {

                //Print header
                System.out.println("\nSelected Purchase Requisition:");
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line; int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(" ");
                        if (data.length >= 4) {
                            reqID = data[0];
                            itemID = data[1];
                            reqQuantity = Integer.parseInt(data[2]);
                            reqStatus = Integer.parseInt(data[3]);
                            reqDate = data[4];
                            userID = data[5];
                            selection2++;
                            if (reqStatus == 1) {
                                currentRow++;
                                if (currentRow == selection) {
                                    String string = "=".repeat(80);
                                    System.out.format("""
                                                            %s
                                                            %-5s %-10s %-10s %-10s %-15s %-12s %-10s
                                                            %s
                                                            """, string,"No.", "Req ID", "Item ID", "Quantity", "Date", "Status", "Raised By", string);
                                                            System.out.format("\n%-5d %-10s %-10s %-10s %-15s %-12s %-10s", selection, reqID, itemID, reqQuantity, reqDate, "Pending", userID);
                                    System.out.println("\n" + string);
                                    break;
                                    }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                }

                //Call edit field method
                int selection3;
                System.out.println("Pending : 1");
                System.out.println("Approved: 2");
                System.out.println("Rejected: 3");
                try {
                    System.out.print("\nSelection: ");
                    selection3 = Sc.nextInt();
                } catch (InputMismatchException e) {
                    selection3 = 999;
                }
                Sc.nextLine();

                PurchaseReq.EditFileLine(selection2, reqID, itemID, reqQuantity, selection3, reqDate, userID);
                ReplaceReq();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }

    // Display Requisition List Method
    static int displayReq(String header) {
        int count = 1;
        try {
            String reqID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null; String itemID; String userID;

            File fSc = new File(filePath);
            //Print header
            try (Scanner fsc = new Scanner(fSc)) {
                //Print header
                String string = "=".repeat(80);
                System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-10s %-15s %-12s %-10s
                                   %s
                                   """, string,"No.", "Req ID", "Item ID", "Quantity", "Date", "Status", "Raised By", string);
                while (fsc.hasNextLine()) {
                    reqID = fsc.next();
                    itemID = fsc.next();
                    reqQuantity = Integer.parseInt(fsc.next());
                    reqStatus = Integer.parseInt(fsc.next());
                    reqDate = fsc.next();
                    userID = fsc.next();
                    switch (reqStatus) {
                        case 1 -> reqStatF = "Pending-";
                        case 2 -> reqStatF = "Approved";
                        case 3 -> reqStatF = "Rejected";
                        case 4 -> reqStatF = "Finalized";
                    }
                    System.out.format("\n%-5d %-10s %-10s %-10s %-15s %-12s %-10s", count, reqID, itemID, reqQuantity, reqDate, reqStatF, userID);                   
                    count++;
                }
                
                System.out.println("\n" + string);
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return count;
    }


    // Selective Display Requisition List Method
    static int selectiveDisplayReq(String header, int selection, String reqStatF) {
        int count = 1;
        try {
            String reqID; int reqQuantity; int reqStatus; String reqDate; String itemID; String userID;
            File fSc = new File(filePath);
            //Print header
            try (Scanner fsc = new Scanner(fSc)) {
                //Print header
                String string = "=".repeat(80);
                System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-10s %-15s %-12s %-10s
                                   %s
                                   """, string,"No.", "Req ID", "Item ID", "Quantity", "Date", "Status", "Raised By", string);
                
                while (fsc.hasNextLine()) {
                    reqID = fsc.next();
                    itemID = fsc.next();
                    reqQuantity = Integer.parseInt(fsc.next());
                    reqStatus = Integer.parseInt(fsc.next());
                    reqDate = fsc.next();
                    userID = fsc.next();
                    if (reqStatus == selection) {
                        System.out.format("\n%-5d %-10s %-10s %-10s %-15s %-12s %-10s", count, reqID, itemID, reqQuantity, reqDate, reqStatF, userID);
                        count++;
                    }
                }
                System.out.println("\n" + string);
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return count;
    }


    // Selected Display Requisition Method
    static void selectedDisplayReq(int selection) {
        String reqID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null; String itemID;
        System.out.println("\nSelected Purchase Requisition:");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; int currentRow = 0;

            while ((line = br.readLine()) != null) {
                currentRow++;

                if (currentRow == selection + 1) {
                    String[] data = line.split(" ");
                    reqID = data[0];
                    itemID = data[1];
                    reqQuantity = Integer.parseInt(data[2]);
                    reqStatus = Integer.parseInt(data[3]);
                    reqDate = data[4];

                    String string = "=".repeat(80);
                    System.out.format("""
                                   %s
                                   %-5s %-10s %-10s %-10s %-15s %-12s %-10s
                                   %s
                                   """, string,"No.", "Req ID", "Item ID", "Quantity", "Date", "Status", "Raised By", string);
                    switch (reqStatus) {
                        case 1 -> reqStatF = "Pending";
                        case 2 -> reqStatF = "Approved";
                        case 3 -> reqStatF = "Rejected";
                        case 4 -> reqStatF = "Finalized";
                    }
                    System.out.format("\n%-5d %-10s %-10s %-10s %-15s %-12s", selection, reqID, itemID, reqQuantity, reqDate, reqStatF);
                    System.out.println("\n" + string);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
