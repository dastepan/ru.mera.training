package ru.mera.training.order;

public class AutoIncrementer {
    private int sequenceNumber;

    public AutoIncrementer() {

    }

    public synchronized int getNumber(){
        return sequenceNumber++;
    }
}
