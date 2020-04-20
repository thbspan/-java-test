package com.test.bytecode;

public class DispatchTest {
    static class BMW {}
    static class Audi {}

    public static class Father{
        public void hardChoice(BMW e){
            System.out.println("father choose BMW");
        }
        public void hardChoice(Audi e){
            System.out.println("father choose Audi");
        }
    }
    public static class Son extends Father{
        @Override
        public void hardChoice(BMW arg){
            System.out.println("son choose qq");
        }
        @Override
        public void hardChoice(Audi arg){
            System.out.println("son choose 360");
        }
    }
    public static void main(String[]args){
        Father father=new Father();
        Father son=new Son();

        // 根据上面的定义，变量father、son同为Father类型

        father.hardChoice(new BMW());
        son.hardChoice(new Audi());
    }
}
