import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * ProgramA acts as a producer in a stdio transport setup.
 *
 * <p>This program writes data to its standard output (stdout).
 * By default, stdout is connected to the terminal. However,
 * when this program is executed with a pipe (|), its stdout
 * is redirected to another program's stdin.
 *
 * <p>In this example, ProgramA sends a few sample messages
 * to stdout, simulating how a client or upstream process
 * would send data.
 *
 * <p>Key Concepts Demonstrated:
 * <ul>
 *   <li>Writing to stdout using System.out</li>
 *   <li>Buffered writing for efficient output</li>
 *   <li>How data leaves a process via stdout</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *   java ProgramA
 * </pre>
 *
 * <p>Piped Usage (connected to ProgramB):
 * <pre>
 *   java ProgramA | java ProgramB
 * </pre>
 *
 * <p>In piped mode, anything written by ProgramA will be
 * received by ProgramB via stdin.
 */
public class ProgramA {

    public static void main(String[] args) throws Exception {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(System.out));

        // Simulate sending multiple messages
        writer.write("hello");
        writer.newLine();

        writer.write("stdio transport");
        writer.newLine();

        writer.write("java example");
        writer.newLine();

        writer.flush(); // Ensure all data is sent out
    }
}
