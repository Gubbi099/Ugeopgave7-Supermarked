import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Register {
    private static final String RECEIPT_HEADER = "--- Kvittering ---";

    private final Map<Item, Integer> basket = new LinkedHashMap<>(); // Stores items in your basket, along with amount of each item.

    private final Store store;


    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public Register(Store store) {
        this.store = store;
    }

    public void scan(long EAN) {
        basket.merge(getItemFromEAN(EAN), 1, Integer::sum);
    }

    public void printReceipt() {
        StringBuilder receipt = new StringBuilder(); // Create StringBuilder for performance when creating receipt.

        receipt.append(RECEIPT_HEADER); // Print receipt header.
        receipt.append("\n"); // Line break

        for (Item s : basket.keySet()) {
            receipt.append(basket.get(s)).append("x ").append(s.getName()).append("      -      ");
            if (this.isOnDiscount(s)) {
                receipt.append("(Rabat -").append(decimalFormat.format(getTotalDiscount(s))).append(") ").append((decimalFormat.format(getTotalFullPrice(s)))).append(" / ");
            }
            receipt.append(decimalFormat.format(getTotal(s))).append(" kr.");
            receipt.append("\n"); // Line break
        }
        receipt.append("\n"); // Line break
        receipt.append("Total: ").append(this.getTotal()).append(" kr.");

        System.out.println(receipt);
        basket.clear(); // clear the basket after printing the customers receipt.
        receipt.setLength(0); // clear the string builder.
    }

    private boolean isOnDiscount(Item item) {
        return store.getDiscountedItem().containsKey(item.getEAN());
    }

    private Item getItemFromEAN(long EAN) {
        return isOnDiscount(store.getAllItems().get(EAN)) ? store.getDiscountedItem().get(EAN) : store.getAllItems().get(EAN);
    }

    private double getDiscount(Item item) {
        if (!isOnDiscount(item)) return 0;
        return store.getAllItems().get(item.getEAN()).getPrice() - item.getPrice();
    }

    private double getTotalDiscount(Item item) {
        return getDiscount(item) * basket.get(item);
    }

    private double getFullPrice(Item item) {
        if (!isOnDiscount(item)) return item.getPrice();
        return store.getAllItems().get(item.getEAN()).getPrice();
    }

    private double getTotalFullPrice(Item item) {
        return getFullPrice(item) * basket.get(item);
    }

    private double getTotal() { // calculate total price of our basket using data stream.
        return basket.keySet().stream().mapToDouble(this::getTotal).sum();
    }

    private double getTotal(Item item) {
        return basket.get(item) * item.getPrice();
    }
}
