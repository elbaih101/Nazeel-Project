package alia.nazeel.pojos;

public class Outlet {
   private String name;
   private String code;
   private String opState;
   private String desc;

   public Outlet(String name, String code, String opState, String desc, String state) {
      this.name = name;
      this.code = code;
      this.opState = opState;
      this.desc = desc;
      this.state = state;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getDesc() {
      return desc;
   }

   public void setDesc(String desc) {
      this.desc = desc;
   }

   public String getOpState() {
      return opState;
   }

   public void setOpState(String opState) {
      this.opState = opState;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   private String state;

}
