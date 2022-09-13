package id.gasper;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );
        Calculator c = new Calculator();
        while(sc.hasNextLine()) {
            System.out.println(c.execute( sc.nextLine() ));
        }
    }
}
