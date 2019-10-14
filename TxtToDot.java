/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
digraph G {
  "7" -> "3"
  "To" -> "Web"
  "To" -> "GraphViz!"
}

 */
public class TxtToDot {
    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder("digraph G {\n");
        Scanner scanner = new Scanner(new File(args[0]));
        scanner.nextInt();
        scanner.nextInt();
        while (scanner.hasNext()) {
            sb.append(String.format("\"%s\" -> \"%s\"\n", scanner.nextInt(), scanner.nextInt()));
        }
        sb.append("}\n");
        System.out.println(sb.toString());
    }
}
