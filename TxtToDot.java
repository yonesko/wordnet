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
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            for (int i = 1; i < split.length; i++) {
                sb.append(String.format("\"%s\" -> \"%s\"\n", Integer.parseInt(split[0]),
                                        Integer.parseInt(split[i])));
            }
        }
        sb.append("}\n");
        System.out.println(sb.toString());
    }
}
