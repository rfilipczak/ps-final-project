import edu.p.lodz.pl.client.ClientAPI;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        ClientAPI api = ClientAPI.getInstance();
        api.start("localhost", 6666);
        api.setLicence("Robert", "4ffe35db90d94c6041fb8ddf7b44df29");
        Scanner scanner = new Scanner(System.in);
        do {
            String rsp = api.getLicenceToken();
        } while (!scanner.nextLine().equals("stop"));
    }
}
