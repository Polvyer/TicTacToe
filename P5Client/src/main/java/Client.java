import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread{

    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;
    String ip;
    int port;
    private Consumer<Serializable> callback;
    GameInfo clientsInfo;

    Client(Consumer<Serializable> callback, String ip, int port) {

        this.ip = ip;
        this.port = port;
        this.callback = callback;
        clientsInfo = new GameInfo();
    }

    public void run() {

        try {
            //socketClient= new Socket(ip, port);
            socketClient= new Socket("127.0.0.1", 5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        } catch(Exception e) {}

        while(true) {

            try {
                GameInfo data = (GameInfo)in.readObject();
                if (data.winCondition != -1) { // Game over
                    if (data.winCondition == 1) { // Game ended
                        callback.accept("#");
                    }
                    else if (data.winCondition == 2) { // Update top3
                        callback.accept("&"); // Reset leaderboard
                        callback.accept("Player " + data.top3Players[1] + " wins: " + data.top3[1]);
                        callback.accept("Player " + data.top3Players[2] + " wins: " + data.top3[2]);
                        callback.accept("Player " + data.top3Players[3] + " wins: " + data.top3[3]);
                    }
                }
                else { // Game is ongoing
                    String buttonClicked = Integer.toString(data.buttonClickedServer);
                    callback.accept(buttonClicked);
                }

            } catch(Exception e) {
            }
        }
    }

    public void send() {
        try {
            out.writeObject(clientsInfo);
            out.reset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getDifficulty(String difficulty) {
        this.clientsInfo.difficulty = difficulty;
    }

    public void getButtonClicked(int buttonClicked) {
        this.clientsInfo.buttonClickedClient = buttonClicked;
        send();
    }

}