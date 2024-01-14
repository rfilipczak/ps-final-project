package example;

import edu.p.lodz.pl.client.ClientAPI;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        try (ClientAPI api = ClientAPI.getInstance()) {
            api.start("localhost", 6666);

            System.out.println("1) Robert\n2) Stefan\n3) Invalid name\n4) Invalid key\nany) Close");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> api.setLicence("Robert", "4ffe35db90d94c6041fb8ddf7b44df29");
                case "2" -> api.setLicence("Stefan", "752b048c76633399b04f6e87a8b211ca");
                case "3" -> api.setLicence("Invalid", "4ffe35db90d94c6041fb8ddf7b44df29");
                case "4" -> api.setLicence("Robert", "invalid");
                default -> {
                    return;
                }
            }
            String prompt = "Type anything to request licence\nType stop to close";
            System.out.println(prompt);
            while (!scanner.nextLine().equals("stop")) {
                String rsp = api.getLicenceToken();
                System.out.println(rsp);
                System.out.println(prompt);
            }
        }
    }
}
