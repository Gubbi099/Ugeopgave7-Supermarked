import java.util.*;


public class Store {
    private final String STORE_NAME;
    private HashMap<Long, Item> allItems;
    private HashMap<Long, Item> discountedItem;

    private final Register register;

    public Store(String name) {
        this.STORE_NAME = name;
        this.register = new Register(this);
    }

    public boolean start() {
        if (allItems == null || discountedItem == null) {
            System.out.println("Could not start store. \nPlease load item data!");
            return false;
        }

        /*
            START STORE SIMULATION HERE
         */

        Robot robot = new Robot();
        Collection<Item> kurv = robot.fyldIKurv(allItems.values());

        for (Item item : kurv) { // Scan each item's barcode, to take account for a potential discounted item.
            register.scan(item.getEAN());
        }

        register.printReceipt();


        return true;
    }

    public void loadItems(String regularPath, String discountPath) {
        this.allItems = parseItemData(regularPath);
        this.discountedItem = parseItemData(discountPath);
    }

    private HashMap<Long, Item> parseItemData(String path) {
        ArrayList<String> itemData = FileIO.readData(path, true);
        HashMap<Long, Item> parsedItemData = new HashMap<>();
        for (String s : itemData) {
            String[] splitData = s.split(";");
            try {
                long EAN = Long.parseLong(splitData[0].trim());
                double price = Double.parseDouble(splitData[3].trim());
                double amount = Double.parseDouble(splitData[5].trim());

                parsedItemData.put(EAN, new Item(EAN, splitData[1].trim(), splitData[2].trim(), price, amount, splitData[6].trim()));
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
        return parsedItemData;
    }

    public String getName() {
        return STORE_NAME;
    }

    public HashMap<Long, Item> getDiscountedItem() {
        return discountedItem;
    }

    public HashMap<Long, Item> getAllItems() {
        return allItems;
    }
}
