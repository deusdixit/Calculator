package id.gasper;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );
        Calculator c = new Calculator();
        System.out.print("> ");
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if ( line.equals( "exit" ) ) {
                break;
            }
            System.out.println(c.execute( line ));
            System.out.print("> ");
        }
    }
}
