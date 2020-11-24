import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class A2Driver {
    public static void main(String args[]) throws IOException {
        File myObj = new File("./test51.txt");
        FileWriter fw = new FileWriter("./newoutput1.txt");
        Scanner sc = new Scanner(myObj);
        int numTestCases;
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
                        argument = sc.nextInt();
                        obj.Defragment();
                        break;
                    default:
                        break;
                }
                String str = String.valueOf(result);
                fw.write(str);
                fw.write("\n");
            }

        }
        fw.close();
        sc.close();
    }
}