package alia.nazeel.pojos;

public class Category {

    private String name;
    private String desc;
    private String state;
    private String outlet;
    private String ntmpCateg;


    public Category(String name, String desc, String state, String outlet, String ntmpCateg) {
        this.name = name;
        this.desc = desc;
        this.state = state;
        this.outlet = outlet;
        this.ntmpCateg = ntmpCateg;
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

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getNtmpCateg() {
        return ntmpCateg;
    }

    public void setNtmpCateg(String ntmpCateg) {
        this.ntmpCateg = ntmpCateg;
    }
}
