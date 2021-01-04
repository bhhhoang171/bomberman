package uet.oop.bomberman.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerProgram {
    static ArrayList<ServiceThread> clients = new ArrayList<>();

    public static void main(String args[]) throws IOException {


        ServerSocket listener = null;

        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        // Mở một ServerSocket tại cổng 7777.
        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        try {
            listener = new ServerSocket(7778);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true) {

                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.

                Socket socketOfServer = listener.accept();
                clients.add(new ServiceThread(socketOfServer, clientNumber++));
                for (ServiceThread client : clients) {
                    client.start();
                }
            }
        } finally {
            listener.close();
        }

    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static class ServiceThread extends Thread {

        private int clientNumber;
        private Socket socketOfServer;
        private String message;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;

            // Log
            log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
        }

        @Override
        public void run() {

            try {

                // Mở luồng vào ra trên Socket tại Server.
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {
                    // Đọc dữ liệu tới server (Do client gửi tới).
                    if (clients.size() == 1) {
                        os.write("1");
                        os.newLine();
                        os.flush();
                        continue;
                    }
                    message = is.readLine();

                    // Ghi vào luồng đầu ra của Socket tại Server.
                    // (Nghĩa là gửi tới Client).
                    for (ServiceThread client : clients) {
                        if (client.clientNumber != this.clientNumber) {
                            os.write(client.message);
                            // Kết thúc dòng
                            os.newLine();
                            // Đẩy dữ liệu đi
                            os.flush();
                        }
                    }
                    Thread.sleep(10);
                }

            } catch (IOException | InterruptedException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}