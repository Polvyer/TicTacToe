import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    int port;
    GameInfo three; // Used to update top 3

    Server(Consumer<Serializable> callback, int portNumber) {

        this.callback = callback;
        this.port = portNumber;
        server = new TheServer();
        server.start();
        three = new GameInfo();
        three.winCondition = 2; // Used to update top 3
    }

    public class TheServer extends Thread {

        public void run() {

            try (ServerSocket mysocket = new ServerSocket(5555);) {

                while (true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("Client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();
                    count++;
                }

            }
            catch (Exception e) {
                callback.accept("Server socket did not launch");
            }
        }
    }

    class ClientThread extends Thread {

        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;
        GameInfo clientInfo;
        FindNextMove find;
        ArrayList<Node> movesList;

        ClientThread(Socket s, int count) {
            this.connection = s;
            this.count = count;
            this.clientInfo = new GameInfo();
            this.clientInfo.player = count;
            this.find = new FindNextMove();
        }

        public void updateClients() {
            for (int i = 0; i < clients.size(); i++) {
                ClientThread c = clients.get(i);
                try {
                    c.out.writeObject(three);
                }
                catch (Exception e) {
                }
            }
        }

        public void updateClient() {
            try {
                out.writeObject(clientInfo);
                out.reset();
            } catch(Exception e) {
            }
        }

        class FindNextMove {

            public void getMovesList() {
                AI_MinMax startThis = new AI_MinMax(clientInfo.input);
                movesList = startThis.movesList;
            }
        }

        public void run() {

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            } catch (Exception e) {
                System.out.println("Streams not open");
            }

            while (true) {
                try {
                    GameInfo data = (GameInfo) in.readObject();

                    this.clientInfo.difficulty = data.difficulty; // Save difficulty choice
                    this.clientInfo.buttonClickedClient = data.buttonClickedClient; // Save client choice
                    this.clientInfo.updateBoardClient(); // Update client choice

                    // Check if client won
                    boolean clientW = this.clientInfo.checkWin("O");
                    if (clientW) {
                        callback.accept("Player " + this.count + " PWNED The Server");
                        this.clientInfo.wins++;
                        callback.accept("Player " + this.count + " now has " + this.clientInfo.wins + " win(s)");
                        this.clientInfo.winCondition = 1; // Client Wins
                        updateClient();

                        // If true, update all clients with new top3
                        if (updateTop3(this.clientInfo.wins, this.clientInfo.player)) {
                            updateClients();
                        }

                        this.clientInfo.reset();
                        continue;
                    }
                    // Check if board is full (tie)
                    else if (this.clientInfo.boardFull()) {
                        callback.accept("Player " + this.count + " and The Server TIED");
                        this.clientInfo.winCondition = 1; // Tie
                        updateClient();
                        this.clientInfo.reset();
                        continue;
                    }

                    this.clientInfo.updateInput(); // Update input for getMovesList();
                    callback.accept("Player " + this.count + " chose " + this.clientInfo.buttonClickedClient + ": " + this.clientInfo.input);
                    find.getMovesList();

                    int bestMove = -1;
                    int bestMoveMinMax;

                    // Expert
                    if (this.clientInfo.difficulty.equals("Expert")) {
                        bestMoveMinMax = -100;
                        try {
                            for (int x = 0; x < movesList.size(); x++) {
                                Node temp = movesList.get(x);

                                if (temp.getMinMax() > bestMoveMinMax) {
                                    bestMoveMinMax = temp.getMinMax();
                                    bestMove = temp.getMovedTo();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    // Easy
                    else if (this.clientInfo.difficulty.equals("Easy")) {
                        bestMoveMinMax = 100;
                        try {
                            for (int x = 0; x < movesList.size(); x++) {
                                Node temp = movesList.get(x);

                                if (temp.getMinMax() < bestMoveMinMax) {
                                    bestMoveMinMax = temp.getMinMax();
                                    bestMove = temp.getMovedTo();
                                }

                            }
                        } catch (Exception e) {
                        }
                    }

                    // Medium
                    else { // "Medium"
                        bestMoveMinMax = 100;
                        try {
                            for (int x = 0; x < movesList.size(); x++) {
                                Node temp = movesList.get(x);

                                if (temp.getMinMax() == 0) { // If we find any 0's, just make that the best move
                                    bestMoveMinMax = temp.getMinMax();
                                    bestMove = temp.getMovedTo();
                                    break;
                                }
                                if (temp.getMinMax() < bestMoveMinMax) { // Else, just get the worst move
                                    bestMoveMinMax = temp.getMinMax();
                                    bestMove = temp.getMovedTo();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    this.clientInfo.buttonClickedServer = bestMove; // Save server choice
                    this.clientInfo.updateBoardServer(); // Update server choice

                    this.clientInfo.resetInput(); // Reset input for callback
                    this.clientInfo.updateInput(); // Update input for callback
                    callback.accept("Server chose " + this.clientInfo.buttonClickedServer + " against Player " + this.count + ": " + this.clientInfo.input);

                    this.clientInfo.resetInput(); // Reset input for next iteration

                    boolean serverW = this.clientInfo.checkWin("X");
                    if (serverW) {
                        callback.accept("The Server PWNED Player " + this.count);
                        updateClient();
                        this.clientInfo.winCondition = 1; // Server Wins
                        updateClient();
                        this.clientInfo.reset();
                        continue;
                    }

                    updateClient();

                } catch (Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    clients.remove(this);
                    break;
                }
            }
        }

    }

    boolean updateTop3(int wins, int player) {
        if (three.top3[1] < wins) {
            three.top3[3] = three.top3[2];
            three.top3Players[3] = three.top3Players[2];
            three.top3[2] = three.top3[1];
            three.top3Players[2] = three.top3Players[1];
            three.top3[1] = wins;
            three.top3Players[1] = player;
            return true;
        }
        else if (three.top3[2] < wins) {
            three.top3[3] = three.top3[2];
            three.top3Players[3] = three.top3Players[2];
            three.top3[2] = wins;
            three.top3Players[2] = player;
            return true;
        }
        else if (three.top3[3] < wins) {
            three.top3[3] = wins;
            three.top3Players[3] = player;
            return true;
        }
        return false;
    }

}