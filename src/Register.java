import java.util.LinkedHashMap;
import java.util.Map;

public class Register {
    private static final String RECEIPT_HEADER = "--- Kvittering ---";

    // Receipt column formats. All three have the same width, so the prices line up.
    private static final String LINE_FORMAT     = "%3dx %-55.55s %10.2f kr.%n";
    private static final String DISCOUNT_FORMAT = "     %-55s %10.2f kr.%n";
    private static final String TOTAL_FORMAT    = "%-60s %10.2f kr.%n";

    private final Map<Item, Integer> basket = new LinkedHashMap<>(); // Stores items in your basket, along with amount of each item.

    private final Store store;

    public Register(Store store) {
        this.store = store;
    }

    public void scan(long EAN) {
        basket.merge(getItemFromEAN(EAN), 1, Integer::sum);
    }

    public void printReceipt() {
        StringBuilder receipt = new StringBuilder(); // Create StringBuilder for performance when creating receipt.

        receipt.append(RECEIPT_HEADER).append("\n");

        for (Map.Entry<Item, Integer> entry : basket.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();

            receipt.append(String.format(LINE_FORMAT, quantity, item.getName(), getTotalFullPrice(item)));
            if (isOnDiscount(item)) {
                receipt.append(String.format(DISCOUNT_FORMAT, "Rabat", -getTotalDiscount(item)));
            }
        }
        receipt.append("\n");
        receipt.append(String.format(TOTAL_FORMAT, "Total", getTotal()));

        System.out.println(receipt);
        basket.clear(); // clear the basket after printing the customers receipt.
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
