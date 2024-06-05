package alia.nazeel.pojos;

public class Item {
    String outlet;
    String type;
    String category;
    String name;
    String desc;
    String state;
    String Price;
    String tax;

    public Item(String outlet, String type, String category, String name, String desc, String state, String price, String tax) {
        this.outlet = outlet;
        this.type = type;
        this.category = category;
        this.name = name;
        this.desc = desc;
        this.state = state;
        Price = price;
        this.tax = tax;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
