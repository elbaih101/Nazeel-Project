package org.example;

import org.example.pojos.Tax;

import java.util.List;

import static org.example.Utils.round;

public class Nazeel_Calculations {


    public static Double reservationRentTaxes(double rentAmount, double discountValue, String discountType, List<Tax> appliedTaxes) {
        double taxValue = 0.0;
        double taxPercentage = 0.0;
        Double discountAmount = getDiscountAmount(rentAmount, discountValue, discountType);
        for (Tax tax : appliedTaxes) {
            if (!tax.getAppliedfor().equalsIgnoreCase("Penalties") && !tax.getAppliedfor().equalsIgnoreCase("extras")) {
                if (tax.getMethod().contains("amount")) {
                    taxValue = taxValue + tax.getValue();
                } else {
                    if (tax.getAppliedfor().contains("Lodging")) {
                        taxPercentage = taxPercentage + ((appliedTaxes.stream().filter(at -> at.getName().contains("Lodging")).findAny().orElse(null).getValue() * 0.01) * (tax.getValue() * 0.01));
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

    public static Double outletOrderTaxes(double orderAmount, double discountValue, String discountType, boolean inclusive) {
        double taxValue = 0.0;
        double taxPercentage = 0.15;
        Double discountAmount = getDiscountAmount(orderAmount, discountValue, discountType);

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
