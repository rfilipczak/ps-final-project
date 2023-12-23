import edu.p.lodz.pl.server.Server;

import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(6666, "src/test/data/licences.json");
        Thread t = new Thread(server);
        t.start();
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        while (!choice.equals("stop")) {
            if (choice.equals("reload")) {
                server.loadLicenceData("src/test/data/licences.json");
            }
            choice = scanner.nextLine();
        }
        server.close();
    }
}
