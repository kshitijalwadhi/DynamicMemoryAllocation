import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class A2Driver {
    public static void main(String args[]) throws IOException {
        int numTestCases;
        File myObj = new File("./new_test.txt");
        FileWriter fw = new FileWriter("./newoutput2.txt");
        Scanner sc = new Scanner(myObj);
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size, 2);
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument;
                int result = -5;
                switch (command) {
                    case "Allocate":
                        argument = sc.nextInt();
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        argument = sc.nextInt();
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        obj.Defragment();
                        result = -10;
                        break;
                    default:
                        break;
                }
                String str = String.valueOf(result);
                if (result != -10) {
                    System.out.println(str);
                    fw.write(str);
                    fw.write("\n");
                } else {
                    System.out.println("Defragmented");
                    fw.write("Defragmented");
                    fw.write("\n");
                }
            }

        }
    }
}