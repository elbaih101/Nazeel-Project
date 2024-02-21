package org.example.enums;

public enum PaymentMethods {
    Digital,
    Cash,
    Mada,
    PayTabs,
    Cheque,
    Visa,
    Master_Card{
        @Override
        public String toString(){
            return "Master Card";
        }
    },
    American_Express{
        @Override
        public String toString(){
                return "American Express";
    }
    },Bank_Transfer{
        @Override
        public String toString(){
                return "Bank Transfer";
    }
    },

}
