package alia.nazeel.enums;

public enum Vouchers {
    /*
    Receipts :: For Receipts Vouchers Generated On Reservations
     */
    Receipt,
    /*
    SD :: For Security Deposite Vouchers Generated On Reservations
     */
    SD,
    /*
    Refund :: For Refunds From Vouchers Generated from Reservations
     */
    Refund,
    /*
    SDRefund :: For Security Deposit Refunds From Vouchers Generated from Reservations
     */
    SDRefund,
    /*
    promissory :: For Drafts Generated on a reservation from Reservation Page or Linked to Reservation
     */
    promissory,
    /*
    Expenses :: For Expenses Vouchers Generated From The Cash Drawer Balance Page and Payment Vouchers Page
     */
    Expenses,
    /*
    SAPromissory :: For StandAlone Drafts Generated From Draft Vouchers Page
     */
    SAPromissory,
    /*
    SAReceipt :: For StandAlone Receipts Generated From Receipt Vouchers page
     */
    SAReceipt,
    /*
    DopCash :: For Drop CAsh Receipt From the CashDrawer Drop Cash ACtion
     */
    DropCash,
    /*
    GenReceipt ::For Generated Receipts from Digital Payments
     */
    GenReceipt
}

