package alia.nazeel.pojos;

import org.joda.time.DateTime;

public class Tax {

    private String name;
    private double amount;
    private double value;
    private String method;

    private boolean inclusive;
    private String appliedfor;
    private DateTime startDate;
    private DateTime endDate;
    private boolean applied;

    public Tax() {
    }

    public Tax(String name, double amount, double value,String method , boolean inclusive, String appliedfor, DateTime startDate, DateTime endDate, boolean applied) {
        setName(name);
        setAmount(amount);
        setValue(value);
        setMethod(method);
        setInclusive(inclusive);
        setAppliedfor(appliedfor);
        setStartDate(startDate);
        setEndDate(endDate);
        setApplied(applied);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isInclusive() {
        return inclusive;
    }

    public void setInclusive(boolean inclusive) {
        this.inclusive = inclusive;
    }

    public String getAppliedfor() {
        return appliedfor;
    }

    public void setAppliedfor(String appliedfor) {
        this.appliedfor = appliedfor;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
