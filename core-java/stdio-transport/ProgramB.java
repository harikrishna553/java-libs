import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * ProgramB acts as a consumer in a stdio transport setup.
 *
 * <p>This program reads data from its standard input (stdin).
 * By default, stdin is connected to the keyboard. However,
 * when another program's stdout is piped into this program,
 * stdin receives that data instead.
 *
 * <p>In this example, ProgramB continuously reads lines from
 * stdin, processes them (capitalizes the text), and prints
 * the result to stdout.
 *
 * <p>Key Concepts Demonstrated:
 * <ul>
 *   <li>Reading from stdin using System.in</li>
 *   <li>Line-by-line processing of streamed data</li>
 *   <li>Transforming input and writing to stdout</li>
 * </ul>
 *
 * <p>Usage (standalone):
 * <pre>
 *   java ProgramB
 * </pre>
 * Then type input manually and press Enter.
 *
 * <p>Piped Usage (with ProgramA):
 * <pre>
 *   java ProgramA | java ProgramB
 * </pre>
 *
 * <p>In piped mode, ProgramB reads output produced by ProgramA.
 */
public class ProgramB {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        String line;

        // Continuously read from stdin
        while ((line = reader.readLine()) != null) {

            // Process input (capitalize)
            String processed = line.toUpperCase();

            // Output result
            System.out.println("Processed: " + processed);
        }
    }
}
