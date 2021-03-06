package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Svirinstel on 19.03.17.
 */
public class Sender implements Runnable {

    private Socket socket;
    private PrintWriter outStream;

    private Scanner cmdScaner;

    public Sender(Socket socket) {
        this.socket = socket;

        try {
            outStream = new PrintWriter(socket.getOutputStream());

            System.out.println("You are connected to server.");

            cmdScaner = new Scanner(System.in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (cmdScaner.hasNext()){
                String clientMessage = cmdScaner.nextLine();
                outStream.println(clientMessage);
                outStream.flush();
                if (clientMessage.equalsIgnoreCase("END_SESSION")) break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outStream.close();

    }
}
