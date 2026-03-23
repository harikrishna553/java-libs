import java.io.*;

/**
 * StdioServer represents a simple server process that communicates
 * using standard input (stdin) and standard output (stdout).
 *
 * <p>This class is designed to be started by another Java process
 * (such as {@link StdioClient}) and demonstrates how a program can
 * act as a service without using HTTP or sockets.
 *
 * <h2>How It Works</h2>
 * <ul>
 *   <li>The server continuously reads input from {@code System.in}</li>
 *   <li>Each line is processed (converted to uppercase)</li>
 *   <li>The result is written to {@code System.out}</li>
 * </ul>
 *
 * <h2>Communication Model</h2>
 * <pre>
 * Client writes  -->  Server reads (System.in)
 * Server writes  -->  Client reads (System.out)
 * </pre>
 *
 * <p>This creates a full-duplex communication channel using stdio streams.
 *
 * <h2>Exit Condition</h2>
 * If the server receives the input "exit":
 * <ul>
 *   <li>It sends a termination message ("Goodbye!")</li>
 *   <li>Breaks the loop</li>
 *   <li>Shuts down gracefully</li>
 * </ul>
 *
 * <h2>Why This Matters</h2>
 * This pattern is commonly used in:
 * <ul>
 *   <li>Model Context Protocol (MCP) servers</li>
 *   <li>Local tool execution frameworks</li>
 *   <li>Command-line utilities</li>
 * </ul>
 *
 * <h2>Standalone Usage</h2>
 * <pre>
 * java StdioServer
 * </pre>
 * Then type input manually.
 *
 * <h2>Parent-Process Usage</h2>
 * Typically started via {@link ProcessBuilder} from another program.
 */
public class StdioServer {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String line;

        while ((line = reader.readLine()) != null) {

            if ("exit".equalsIgnoreCase(line)) {
                writer.write("Goodbye!");
                writer.newLine();
                writer.flush();
                break;
            }

            // Process input (business logic)
            String response = "Server: " + line.toUpperCase();

            writer.write(response);
            writer.newLine();
            writer.flush();
        }
    }
}
