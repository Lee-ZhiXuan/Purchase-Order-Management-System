import java.util.*;
import java.io.*;

public class PurchaseReq extends Item {
    private String reqID;
    private int reqQuantity;
    private int reqStatus;
    private String reqDate;

    // Constructor
    public PurchaseReq(String ReqID, String itemID, int ReqQuantity, int ReqStatus, String ReqDate) {
        super(itemID);
        this.reqID = ReqID;
        this.reqQuantity = ReqQuantity;
        this.reqStatus = ReqStatus;
        this.reqDate = ReqDate;
    }

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


    // View Purchase Requisition Method
    static void ViewPurchaseReq() {
        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            Scanner Sc = new Scanner(System.in);
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqDate; String reqStatF = null;

            //Print header
            System.out.println("=============================================================================");
            System.out.println("View Purchase Requisition");
            System.out.println("=============================================================================");
            System.out.println("Req ID\t\t\tItem ID\t\t\tQuantity\t\tDate\t\t\t\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = fsc.next();
                reqDate = fsc.next();
                switch (reqStatus) {
                    case "1" -> reqStatF = "Pending";
                    case "2" -> reqStatF = "Approved";
                    case "3" -> reqStatF = "Rejected";
                }
                System.out.println(reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t\t" + reqDate + "\t\t\t" + reqStatF);
            }
            fsc.close();

            System.out.println("=============================================================================");
            System.out.print("\nPress Enter to return...");
            Sc.nextLine();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println();
    }


    // Create Purchase Requisition Method
    static void AddPurchaseReq() {
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
                String inputReqID = Sc.nextLine();
                System.out.print("Enter Item ID: ");
                String inputItemID = Sc.nextLine();
                System.out.print("Enter Requisition Quantity: ");
                int inputReqQuantity = Sc.nextInt();
                Sc.nextLine();
                System.out.print("Enter Requisition Date (DD-MM-YYYY): ");
                String inputReqDate = Sc.nextLine();

                PurchaseReq req = new PurchaseReq(inputReqID, inputItemID, inputReqQuantity, 1, inputReqDate);

                try {
                    FileWriter tfw = new FileWriter("requisition.txt", true);
                    tfw.write("\n" + req.getReqID() + " " + req.getItemID() + " " + req.getReqQuantity() + " " + req.getReqStatus() + " " + req.getReqDate());
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
    static void EditPurchaseReq() {
        int count = 1;

        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqDate; String reqStatF = null;

            //Print header
            System.out.println("=============================================================================");
            System.out.println("Edit Purchase Requisition");
            System.out.println("=============================================================================");
            System.out.println("No.\tReq ID\t\t\tItem ID\t\t\tQuantity\tDate\t\t\t\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = fsc.next();
                reqDate = fsc.next();
                switch (reqStatus) {
                    case "1" -> reqStatF = "Pending";
                    case "2" -> reqStatF = "Approved";
                    case "3" -> reqStatF = "Rejected";
                }
                System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t" + reqDate+ "\t\t\t" + reqStatF);
                count++;
            }
            fsc.close();
            System.out.println("=============================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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
                String reqID; String itemID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null;

                //Print header
                System.out.println("\nSelected Purchase Requisition:");
                try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                    String line;
                    int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        currentRow++;

                        if (currentRow == selection + 1) {
                            String[] data = line.split(" ");
                            reqID = data[0];
                            itemID = data[1];
                            reqQuantity = Integer.parseInt(data[2]);
                            reqStatus = Integer.parseInt(data[3]);
                            reqDate = data[4];

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

                PurchaseReq.EditFileLine(selection, newReqID, newItemID, newReqQuantity, newReqStatus, newReqDate);
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
    static void EditFileLine(int selection, String reqID, String itemID, int reqQuantity, int reqStatus, String reqDate) {

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
                    wr.write(reqID + " " + itemID + " " + reqQuantity + " " + reqStatus + " " + reqDate);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    // Delete Purchase Requisition Method
    static void RemovePurchaseReq() {
        int count = 1;
        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            String reqID; String itemID; int reqQuantity; String reqStatus; String reqDate; String reqStatF = null;

            //Print header
            System.out.println("=============================================================================");
            System.out.println("Remove Purchase Requisition");
            System.out.println("=============================================================================");
            System.out.println("No.\tReq ID\t\t\tItem ID\t\t\tQuantity\t\tDate\t\t\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = fsc.next();
                reqDate = fsc.next();
                switch (reqStatus) {
                    case "1" -> reqStatF = "Pending";
                    case "2" -> reqStatF = "Approved";
                    case "3" -> reqStatF = "Rejected";
                }
                System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity+ "\t\t\t\t" + reqDate+ "\t\t" + reqStatF);
                count++;
            }
            fsc.close();
            System.out.println("=============================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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

            if (selection < 0 || selection >= count) {
                System.out.println("Invalid selection, please try again.");
            }
            else {
                String reqID; String itemID; int reqQuantity; int reqStatus; String reqDate; String reqStatF = null;

                //Print header
                System.out.println("\nSelected Purchase Requisition:");
                try (BufferedReader br = new BufferedReader(new FileReader("requisition.txt"))) {
                    String line;
                    int currentRow = 0;

                    while ((line = br.readLine()) != null) {
                        currentRow++;

                        if (currentRow == selection + 1) {
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


    // Delete method
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


    // Replace actual with carry
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


    // Purchase requisition approval
    static void ReqApproval() {
        int count = 1;
        try {
            File fSc = new File("requisition.txt");
            Scanner fsc = new Scanner(fSc);
            String reqID; String itemID; int reqQuantity; int reqStatus; String reqDate;

            //Print header
            System.out.println("=============================================================================");
            System.out.println("Requisition Approval");
            System.out.println("=============================================================================");
            System.out.println("No.\tReq ID\t\t\tItem ID\t\t\tQuantity\tDate\t\t\t\tStatus\n");

            while (fsc.hasNextLine()) {
                reqID = fsc.next();
                itemID = fsc.next();
                reqQuantity = Integer.parseInt(fsc.next());
                reqStatus = Integer.parseInt(fsc.next());
                reqDate = fsc.next();
                if (reqStatus == 1) {
                    System.out.println(count + "\t" + reqID + "\t\t" + itemID + "\t\t" + reqQuantity + "\t\t\t" + reqDate + "\t\t\tPending");
                    count++;
                }
            }
            fsc.close();
            System.out.println("=============================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Scanner Sc = new Scanner(System.in);
        int selection;

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
                String reqID = null; String itemID = null; int reqQuantity = 0; int reqStatus; String reqDate = null;

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

                PurchaseReq.EditFileLine(selection, reqID, itemID, reqQuantity, selection3, reqDate);
                ReplaceReq();
                System.out.print("\nPress Enter to continue...");
                Sc.nextLine();
                System.out.println();
                return;
            }
        } while (selection != 0);

        System.out.println();
    }
}
