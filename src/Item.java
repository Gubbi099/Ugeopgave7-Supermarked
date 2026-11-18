public class Item {
    private long EAN;
    private String name;
    private String lastPriceChange;
    private double price;
    private double amount;
    private String unit;

    public Item(long EAN, String name, String lastPriceChange, double price, double amount, String unit) {
        this.EAN = EAN;
        this.name = name;
        this.lastPriceChange = lastPriceChange;
        this.price = price;
        this.amount = amount;
        this.unit = unit;
    }

    public long getEAN() {
        return EAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item other)) return false;
        return EAN == other.EAN; // Items are the same product if their EAN matches.
    }

    @Override
    public int hashCode() {
        return Long.hashCode(EAN);
    }

}
