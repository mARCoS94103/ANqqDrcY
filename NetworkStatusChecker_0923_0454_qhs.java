// 代码生成时间: 2025-09-23 04:54:01
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.F.Promise;
import java.net.Socket;
import java.net.InetSocketAddress;

import static play.libs.F.Promise.pure;
import static play.libs.F.Promise.promise;

/**
 * NetworkStatusChecker is a controller that provides functionality to
 * check the network connection status of a given host and port.
 */
public class NetworkStatusChecker extends Controller {

    private static final int TIMEOUT = 5000; // Timeout for socket connection in milliseconds

    /**
     * Check if a given host is reachable on a specified port.
     *
     * @param host The hostname or IP address to check.
     * @param port The port number to check.
     * @return A Promise of the connection status as a Result.
     */
    public static Promise<Result> checkHost(String host, int port) {
        return promise(() -> {
            try {
                // Create a socket and connect to the specified host and port
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), TIMEOUT);
                socket.close();
                // If connection is successful, return a 200 OK status
                return ok("Connection to " + host + " on port " + port + " is established.");
            } catch (Exception e) {
                // If there is an exception, return a 503 Service Unavailable status
                return status(503, "Connection to " + host + " on port " + port + " failed: " + e.getMessage());
            }
        });
    }

    /**
     * Action method to handle HTTP GET requests for checking network status.
     * It expects the host and port as query parameters.
     *
     * @return A Promise of the connection status as a Result.
     */
    public static Promise<Result> checkNetworkStatus() {
        String host = request().getQueryString("host");
        int port = request().getQueryString("port") != null ? Integer.parseInt(request().getQueryString("port")) : 80;

        if (host == null) {
            return promise(() -> badRequest("Host parameter is missing."));
        }

        return checkHost(host, port);
    }
}