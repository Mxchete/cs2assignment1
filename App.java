import java.util.*;
public class App {
    public static void main(String[] args) throws Exception {
        TwoFourTree tft = new TwoFourTree(); 
        System.out.println("#### CURRENT TREE ####");
        tft.addValue(1);
        tft.printInOrder();
        System.out.println("#### CURRENT TREE ####");
        tft.addValue(100);
        tft.printInOrder();
        System.out.println("#### CURRENT TREE ####");
        tft.addValue(7);
        tft.printInOrder();
        System.out.println("#### CURRENT TREE ####");
        tft.addValue(3);
        tft.printInOrder();
        System.out.println("#### CURRENT TREE ####");
        tft.addValue(8);
        tft.printInOrder();
        System.out.println("#### CURRENT TREE ####");
        tft.addValue(8);
        tft.printInOrder();
        for (int i = 0; i<20; i++) {
        System.out.println("#### CURRENT TREE ####");
        tft.addValue((int)Math.floor(Math.random() * (100 - 1 + 1) + 1));
        tft.printInOrder(); }
        boolean does = tft.hasValue(1);
        System.out.println(does);
    }
}
