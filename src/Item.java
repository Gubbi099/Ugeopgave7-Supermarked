public class Vare {
    private long EAN;
    private String name;
    private String lastPriceChange;
    private double price;
    private double amount;
    private String unit;

    public Vare(long EAN, String name, String lastPriceChange, double price, double amount, String unit) {
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

    public void setEAN(long EAN) {
        this.EAN = EAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastPriceChange() {
        return lastPriceChange;
    }

    public void setLastPriceChange(String lastPriceChange) {
        this.lastPriceChange = lastPriceChange;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
