import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    // id;name;priceDate;price;isBudgetBrand;quantity;unit;isWeighted;isBio;isAvailable

    private static final String REGULAR_ITEMS_PATH = "data/varer.csv";
    private static final String DISCOUNTED_ITEMS_PATH = "data/tilbud.csv";

    static void main(String[] args) {

        Store store = new Store("Netto");
        store.loadItems(REGULAR_ITEMS_PATH, DISCOUNTED_ITEMS_PATH);
        store.start();


    }
}
