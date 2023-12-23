import edu.p.lodz.pl.server.Server;

import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(6666);
        Thread t = new Thread(server);
        t.start();
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equals("stop")) {

        }
        server.close();
    }
}
