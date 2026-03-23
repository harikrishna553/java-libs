import java.io.*;

/**
 * StdioClient demonstrates how a Java application can spawn and communicate
 * with another process (StdioServer) using stdio transport.
 *
 * <p>This class represents the "client" in a parent-child process model.
 * It uses {@link ProcessBuilder} to start the server process and establishes
 * bidirectional communication using the server's stdin and stdout streams.
 *
 * <h2>How It Works</h2>
 * <ul>
 *   <li>The client starts the server process using {@code ProcessBuilder}</li>
 *   <li>The client writes data to the server's stdin</li>
 *   <li>The server processes the input and writes a response to stdout</li>
 *   <li>The client reads the response from the server's stdout</li>
 * </ul>
 *
 * <h2>Key Concept: Process Wiring</h2>
 * When the server process is started:
 * <pre>
 * Client Process                  Server Process
 * --------------                  --------------
 * process.getOutputStream()  -->  System.in
 * process.getInputStream()   <--  System.out
 * </pre>
 *
 * <p>This means:
 * <ul>
 *   <li>Writing to {@code process.getOutputStream()} sends data to the server's stdin</li>
 *   <li>Reading from {@code process.getInputStream()} receives data from the server's stdout</li>
 * </ul>
 *
 * <h2>Interactive Loop</h2>
 * The client reads user input from the keyboard and continuously:
 * <ol>
 *   <li>Sends input to the server</li>
 *   <li>Waits for the server's response</li>
 *   <li>Prints the response to the terminal</li>
 * </ol>
 *
 * <h2>Why This Matters</h2>
 * This pattern is widely used in:
 * <ul>
 *   <li>AI tools (local model/tool execution)</li>
 *   <li>IDE integrations</li>
 *   <li>CLI-based tooling</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>
 * javac StdioClient.java StdioServer.java
 * java StdioClient
 * </pre>
 *
 * <p>Ensure {@code StdioServer.class} is available in the classpath,
 * as the client will attempt to start it as a separate process.
 */
public class StdioClient {
    public static void main(String[] args) throws Exception {

        // Start the server process (child process)
        Process process = new ProcessBuilder("java", "StdioServer")
                .redirectErrorStream(true) // Merge stderr with stdout
                .start();

        // Read user input from keyboard (client stdin)
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Write to server's stdin
        BufferedWriter serverWriter = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream()));

        // Read from server's stdout
        BufferedReader serverReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String input;

        System.out.println("Type messages (type 'exit' to quit):");

        while ((input = userInput.readLine()) != null) {

            // Send input to server
            serverWriter.write(input);
            serverWriter.newLine();
            serverWriter.flush();

            // Read response from server
            String response = serverReader.readLine();
            System.out.println(response);

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
        }

        process.destroy();
        System.out.println("Client exited.");
    }
}
