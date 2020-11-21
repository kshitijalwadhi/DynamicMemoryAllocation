import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Driver_List {
    public static void main(String args[]) throws IOException {
        File myObj = new File("./test_list_final.txt");
        FileWriter fw = new FileWriter("./list_out.txt");
        Scanner sc = new Scanner(myObj);
        int numTestCases;
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            Dictionary obj = new A1List();
            int numCommands = sc.nextInt();
            int count = 0;
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument1, argument2, argument3;
                Dictionary temp;
                int result = -5;
                switch (command) {
                    case "Insert":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        argument3 = sc.nextInt();
                        temp = obj.Insert(argument1, argument2, argument3);
                        result = temp.key;
                        break;
                    case "Insert_Move":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        argument3 = sc.nextInt();
                        obj = obj.Insert(argument1, argument2, argument3);
                        result = obj.key;
                        break;
                    case "Delete":
                        argument1 = sc.nextInt();
                        temp = obj.Find(argument1, true);
                        result = obj.Delete(temp) ? 1 : 0;
                        break;
                    case "Find":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        temp = obj.Find(argument1, (argument2 == 1));
                        result = (temp == null) ? -100 : temp.address;
                        break;
                    default:
                        break;
                }
                count++;
                System.out.println(result);
                // fw.write(result);
                // fw.write("\n");
                boolean sane = obj.sanity();
                // boolean sane = true;
                if (!sane) {
                    System.out.println("Test Case: " + (numTestCases) + " Command: " + (numCommands));
                    System.out.println("Sanity Broken....Closing the testcase");
                    break;
                }
            }

        }
        fw.close();
        sc.close();
    }
}
