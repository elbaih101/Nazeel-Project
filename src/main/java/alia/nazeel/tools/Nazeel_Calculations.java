package alia.nazeel.tools;

import alia.nazeel.pojos.Tax;

import java.util.List;

import static alia.nazeel.tools.Utils.round;

public class Nazeel_Calculations {

    /**
     * this function calculate rent taxes Provided with the rent amount and the discount amount to be put in consideration
     * and the discount type wether its percentage or amount and a list of applied taxes
     * @param rentAmount  double Rent amount
     * @param discountAmount double Discount amount
     * @param discountType String Discount type
     * @param appliedTaxes List<Tax>Pojos of applied taxes and its values </>
     * @return double of the tax value applied on the rent amount
     */
    public static Double reservationRentTaxes(double rentAmount, double discountAmount, String discountType, List<Tax> appliedTaxes) {
        double taxValue = 0.0;
        double taxPercentage = 0.0;
//        Double discountAmount = getDiscountAmount(rentAmount, discountValue, discountType);
        for (Tax tax : appliedTaxes) {
            if (!tax.getAppliedfor().equalsIgnoreCase("Penalties") && !tax.getAppliedfor().equalsIgnoreCase("extras")) {
                if (tax.getMethod().contains("amount")) {
                    taxValue = taxValue + tax.getValue();
                } else {
                    if (tax.getAppliedfor().contains("Lodging")) {
                        taxPercentage = taxPercentage + ((appliedTaxes.stream().filter(at -> at.getName().contains("Lodging")).findAny().orElseThrow().getValue() * 0.01) * (tax.getValue() * 0.01));
                    } else {
                        taxPercentage = taxPercentage + (tax.getValue() * 0.01);
                    }
                }
            }
        }
        if (appliedTaxes.stream().anyMatch(Tax::isInclusive)) {
            //inclusive
            if (discountType.contains("From Balance"))
                taxValue = (rentAmount * taxPercentage) / (1 + taxPercentage);
            else
                taxValue = ((rentAmount - discountAmount) * taxPercentage) / (1 + taxPercentage);
        } else {
            //exclusive
            taxValue = taxValue + (rentAmount * taxPercentage);
        }
        taxValue = round(taxValue, 4);
        return taxValue;
    }

    /**
     * calculates the outlet orders axes
     * @param orderAmount double Order Amount
     * @param discountAmount double Discount amount
     * @param inclusive boolean wether the taxes are inclusive or exclusive
     * @return double the tax value
     */
    public static Double outletOrderTaxes(double orderAmount, double discountAmount, boolean inclusive) {
        double taxValue = 0.0;
        double taxPercentage = 0.15;
       // Double discountAmount = getDiscountAmount(orderAmount, discountValue, discountType);

        if (inclusive) {
            //inclusive

            taxValue = ((orderAmount - discountAmount) * taxPercentage) / (1 + taxPercentage);
        } else {
            //exclusive
            taxValue = taxValue + (orderAmount * taxPercentage);
        }
        taxValue = round(taxValue, 4);
        return taxValue;
    }

    public static Double getDiscountAmount(double Amount, double discountValue, String discountType) {
        double discountAmount = 0.0;
        if (discountType.toLowerCase().contains("percentage") || discountType.toLowerCase().contains("%")) {
            discountAmount = discountAmount + (Amount * discountValue) / 100;
        } else if (discountType.toLowerCase().contains("amount") || discountType.toLowerCase().contains("$")) {
            discountAmount = discountAmount + discountValue;
        }
        return discountAmount;
    }


}
