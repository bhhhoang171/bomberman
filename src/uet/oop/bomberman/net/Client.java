package uet.oop.bomberman.net;

import uet.oop.bomberman.scenes.MainGame;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final String serverHost = "localhost";

    private Socket socketOfClient = null;
    private BufferedWriter os = null;
    private BufferedReader is = null;

    public Client() {
        init();
        start();
    }

    public void init() {
        try {
            // Gửi yêu cầu kết nối tới Server đang lắng nghe
            // trên máy 'localhost' cổng 7777.
            socketOfClient = new Socket(serverHost, 7778);

            // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

            // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
        }
    }

    public void start() {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // Ghi dữ liệu vào luồng đầu ra của Socket tại Client.
                        if (MainGame.clientID != 99) {
                            String message = "";
                            if (MainGame.bombers.get(MainGame.clientID).up) {
                                message += "T";
                            } else message += "F";
                            if (MainGame.bombers.get(MainGame.clientID).left) {
                                message += "T";
                            } else message += "F";
                            if (MainGame.bombers.get(MainGame.clientID).down) {
                                message += "T";
                            } else message += "F";
                            if (MainGame.bombers.get(MainGame.clientID).right) {
                                message += "T";
                            } else message += "F";
                            os.write(message);
                            os.newLine(); // kết thúc dòng
                            os.flush();  // đẩy dữ liệu đi.
                        }

                        // Đọc dữ liệu trả lời từ phía server
                        // Bằng cách đọc luồng đầu vào của Socket tại Client.
                        String responseLine = is.readLine();
                        if (responseLine.equals("1")) {
                            MainGame.clientID = 0;
                        } else if (MainGame.clientID == 99) {
                            MainGame.clientID = 1;
                        } else {
                            MainGame.bombers.get((MainGame.clientID + 1) % 2).up = responseLine.charAt(0) == 'T';
                            MainGame.bombers.get((MainGame.clientID + 1) % 2).left = responseLine.charAt(1) == 'T';
                            MainGame.bombers.get((MainGame.clientID + 1) % 2).down = responseLine.charAt(2) == 'T';
                            MainGame.bombers.get((MainGame.clientID + 1) % 2).right = responseLine.charAt(3) == 'T';
                        }
                    }
                } catch (UnknownHostException e) {
                    System.err.println("Trying to connect to unknown host: " + e);
                } catch (IOException e) {
                    System.err.println("IOException:  " + e);
                }
            }
        });
        client.start();
    }

    public void close() {

    }

}
