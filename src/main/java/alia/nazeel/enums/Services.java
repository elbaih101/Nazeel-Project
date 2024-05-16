package alia.nazeel.enums;

public enum Services {
    Nazeel,
    Shomoos,
    NTMP,
    Smart_Lock{
        public String toString(){
            return "Smart Lock";
        }
    },
    RateGain,
    Odoo,
    PayTabs,
    Website,

    Fatoora_ZATCA{
        public String toString(){
            return "Fatoora/ZATCA";
        }
    },
    SaudiBeds
}
