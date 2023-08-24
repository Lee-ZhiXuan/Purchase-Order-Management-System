import java.util.*;
import java.io.*;

public class PurchaseReq {
    private String reqID;
    private int reqQuantity;
    private int reqStatus;
    private String reqDate;
    private String itemID;

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


    // View Purchase Requisition Method
    public void View() {
        Scanner Sc = new Scanner(System.in);
        displayReq("View Requisition List");
        System.out.print("\nPress Enter to return...");
        Sc.nextLine();
        System.out.println();
    }


    // Create Purchase Requisition Method
    public void Add(String userID) {
        String reqID; int reqQuantity; String reqDate; String itemID;
        Scanner Sc = new Scanner(System.in);
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

            if (selection == 1) {
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
                    FileWriter tfw = new FileWriter("requisition.txt", true);
                    tfw.write("\n" + req.getReqID() + " " + req.getItemID() + " " + req.getReqQuantity() + " " + req.getReqStatus() + " " + req.getReqDate() + " " + userID);
                    tfw.close();
                } catch (IOException e) {
                    System.out.println("\nAn error occurred.");
                    e.printStackTrace();
                }

                System.out.println("\nPurchase requisition successfully created.");
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();

            } else if (selection == 0) {
                System.out.println("Back to previous menu.\n");
            } else {
                System.out.println("Invalid Selection, please try again.");
            }
        } while (selection != 0);
    }


    // Edit Purchase Requisition Method
    public void Edit() {
        String reqID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null; String itemID; String userID = null;

        int count = displayReq("Edit Purchase Requisitions");

        Scanner Sc = new Scanner(System.in);
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
                //Print header
                System.out.println("\nSelected Purchase Requisition:");
                try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                    String line;
                    int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        currentRow++;

                        if (currentRow == selection + 2) {
                            String[] data = line.split(" ");
                            reqID = data[0];
                            itemID = data[1];
                            reqQuantity = Integer.parseInt(data[2]);
                            reqStatus = Integer.parseInt(data[3]);
                            reqDate = data[4];
                            userID = data[5];

                            System.out.println("=============================================================================");
                            System.out.println("No.\tReq ID\t\t\tItem ID\t\t\tQuantity\tDate\t\t\t\tStatus\n");
                            switch (reqStatus) {
                                case 1 -> reqStatF = "Pending";
                                case 2 -> reqStatF = "Approved";
                                case 3 -> reqStatF = "Rejected";
                            }
                            System.out.println(selection + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t" + reqDate + "\t\t\t" + reqStatF);
                            System.out.println("=============================================================================");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

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

                int selection2 = selection + 1;
                PurchaseReq.EditFileLine(selection2, newReqID, newItemID, newReqQuantity, newReqStatus, newReqDate, userID);
                ReplaceReq();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }


    // Edit method
    static void EditFileLine(int selection, String reqID, String itemID, int reqQuantity, int reqStatus, String reqDate, String userID) {

        try (BufferedReader rd = new BufferedReader(new FileReader("requisition.txt"));
             BufferedWriter wr = new BufferedWriter(new FileWriter("requisitionCarry.txt"))) {

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
            e.printStackTrace();
        }
    }


    // Delete Purchase Requisition Method
    public void Delete() {
        String reqID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null; String itemID;

        int count = displayReq("Remove Purchase Requisition");

        Scanner Sc = new Scanner(System.in);
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
                //Print header
                System.out.println("\nSelected Purchase Requisition:");
                try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                    String line;
                    int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        currentRow++;

                        if (currentRow == selection + 2) {
                            String[] data = line.split(" ");
                            reqID = data[0];
                            itemID = data[1];
                            reqQuantity = Integer.parseInt(data[2]);
                            reqStatus = Integer.parseInt(data[3]);
                            reqDate = data[4];

                            System.out.println("=============================================================================");
                            System.out.println("No.\tReq ID\t\t\tItem ID\t\t\tQuantity\t\tDate\t\tStatus\n");
                            switch (reqStatus) {
                                case 1 -> reqStatF = "Pending";
                                case 2 -> reqStatF = "Approved";
                                case 3 -> reqStatF = "Rejected";
                            }
                            System.out.println(selection + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t\t" + reqDate+ "\t\t" +reqStatF);
                            System.out.println("=============================================================================");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                //Call delete field method
                System.out.println("Do you wish to delete the selected purchase requisition?");
                System.out.println("Type out YES to confirm.");
                System.out.print("\nConfirmation: ");
                String confirmation = Sc.nextLine();
                if (Objects.equals(confirmation, "YES")) {
                    int selection2 = selection + 1;
                    DeleteFileLine(selection2);
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

        try (BufferedReader rd = new BufferedReader(new FileReader("requisition.txt"));
             BufferedWriter wr = new BufferedWriter(new FileWriter("requisitionCarry.txt"))) {

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
            e.printStackTrace();
        }
    }


    // Replace Actual with Carry
    static void ReplaceReq() {

        //Delete old requisition file
        File del = new File("requisition.txt");
        if (del.delete()) {
            System.out.print("\n");
        } else {
            System.out.println("Error.");
        }

        //Rename carry requisition
        String oldFilePath = "requisitionCarry.txt";
        String newFilePath = "requisition.txt";

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
        String reqID = null; int reqQuantity = 0; int reqStatus; String reqDate = null; String itemID = null; String userID = null;

        int count = selectiveDisplayReq("Handle Purchase Requisition", 1, "Pending-");

        Scanner Sc = new Scanner(System.in);
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
                try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                    String line;
                    int currentRow = 0;

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
                                    System.out.println("=============================================================================");
                                    System.out.println("No.\tReq ID\t\t\tItem ID\t\t\tQuantity\tDate\t\t\t\tStatus\n");
                                    System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t" + reqDate + "\t\t\tPending");
                                    System.out.println("=============================================================================");
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
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
        int count = 0;
        try {
            String reqID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null; String itemID; String userID;

            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);

            //Print header
            System.out.println("=============================================================================================");
            System.out.println(header);
            System.out.println("=============================================================================================");
            System.out.println("#\tReq ID\t\t\tItem ID\t\t\tQuantity\t\tDate\t\t\t\tStatus\t\tRaised by\n");

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
                }
                System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t\t" + reqDate + "\t\t\t" + reqStatF + "\t\t" + userID);
                count++;
            }
            fsc.close();

            System.out.println("=============================================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return count;
    }


    // Selective Display Requisition List Method
    static int selectiveDisplayReq(String header, int selection, String reqStatF) {
        int count = 1;
        try {
            String reqID; int reqQuantity; int reqStatus; String reqDate; String itemID; String userID;
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);

            //Print header
            System.out.println("=============================================================================================");
            System.out.println(header);
            System.out.println("=============================================================================================");
            System.out.println("#\tReq ID\t\t\tItem ID\t\t\tQuantity\t\tDate\t\t\t\tStatus\t\tRaised by\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = Integer.parseInt(fsc.next());
                reqDate = fsc.next();
                userID = fsc.next();
                if (reqStatus == selection) {
                    System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t\t" + reqDate + "\t\t\t" + reqStatF + "\t\t" + userID);
                    count++;
                }
            }
            fsc.close();
            System.out.println("=============================================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return count;
    }
}
