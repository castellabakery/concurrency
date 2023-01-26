package kr.co.concurrency.concurrencytest.enumerate;

public enum TestEnum {
    INCREASE(0),
    DECREASE(1);

    private int type;
    TestEnum(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
}
