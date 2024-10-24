package Server;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

@ServerEndpoint("/editor")
public class WebSocketServer {

    private static final String FILE_PATH = "/home/dharshan/Downloads"; // Update this path

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String[] parts = message.split(":", 2);
        String command = parts[0];
        String content = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "load":
                loadFile(content, session);
                break;
            case "save":
                saveFile(content);
                break;
            default:
                session.getOpenSessions().forEach(s -> {
                    try {
                        s.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    private void loadFile(String filename, Session session) {
        File file = new File(FILE_PATH + filename);
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            session.getBasicRemote().sendText("fileContent:" + content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(String data) {
        String[] parts = data.split(":", 2);
        String filename = parts[0];
        String content = parts[1];

        try (FileWriter writer = new FileWriter(FILE_PATH + filename)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket closed: " + session.getId());
    }
}
