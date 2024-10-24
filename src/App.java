
import org.glassfish.tyrus.server.Server;
import Server.WebSocketServer;

public class App {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080, "/websocket", null, WebSocketServer.class);
        try {
            server.start();
            System.out.println("WebSocket server started on ws://localhost:8080/websocket/editor");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}