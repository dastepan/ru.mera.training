package org.pizza.service.utilities;

public class AutoIncrementer {
    private int sequenceNumber;

    public AutoIncrementer() {
    }

    public synchronized int getNumber(){
        return sequenceNumber++;
    }
}
