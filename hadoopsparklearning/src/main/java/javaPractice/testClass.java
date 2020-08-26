package javaPractice;

class class1 {
    private int a1=10;
    protected int a2=20;
    public int a3=30;
    int a4=40;
    int c=0;
    void testPrivate(){
        System.out.println("Private base class function");
    }
    void testUndefined(){
        System.out.println("Checking base class undefined and getting private variable"+a1);
    }
}
class class2 extends class1 {
    private int b1=100;
    protected int b2=200;
    public int b3=300;
    int b4=400;
    int c=10;
    void check() {
        System.out.println("Checking child class");
    }
    void testUndefined(){
        System.out.println("child undefined function and checking private variable" + b1);
    }
}
public class testClass {
    public static void main(String args[]){
        class1 obj1 = new class1();
        System.out.println("Base private isnot accessible" + "\n"
        + "base protected:"+ obj1.a2 + "\n base public: " + obj1.a3 +
        "\n base default:"+obj1.a4 + "\n base default common: "+obj1.c);
        obj1.testUndefined();

        class1 obj2= new class2();
        System.out.println("Base private is not accessible" + "\n"
                + "base protected:"+ obj2.a2 + "\n base public: " + obj2.a3 +
                "\n base default:"+obj2.a4 + "\n base default common: "+obj2.c +
                "No variable of child class is accessible");
        obj2.testUndefined();
        obj2.testPrivate();

        // class2 obj3=new class1();
        // compile time incompatible type error

        class2 obj3 = new class2();
        System.out.println("Base private is not accessible" + "\n"
                + "base protected:"+ obj3.a2 + "\n base public: " + obj3.a3 +
                "\n base default:"+obj3.a4 + "\n base and child default common: "+obj3.c +
                "child private not accessible" +
                "\n child protected :" + obj3.b2 +
                "\n child public: "+ obj3.b3 +
                "\n child default: "+ obj3.b4);
        obj3.check();
        obj3.testUndefined();
        obj3.testPrivate();
    }
}

