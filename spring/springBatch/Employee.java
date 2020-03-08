package com.bkash.template.webapp.domain;

public class Employee {

    String ID;
    String TransactionID;
    String RefundAmount;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getRefundAmount() {
        return RefundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        RefundAmount = refundAmount;
    }


    public String toString() {

        return "ID=" + ID + ",RefundAmount=" + this.RefundAmount + ",TransactionID=" + this.TransactionID;
    }


//public setter and getter methods
}
