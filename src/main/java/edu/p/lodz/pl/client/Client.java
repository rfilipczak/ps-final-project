package edu.p.lodz.pl.client;

import com.google.gson.Gson;
import edu.p.lodz.pl.common.entities.LicenceRequest;
import edu.p.lodz.pl.common.entities.LicenceRequestResponse;
import edu.p.lodz.pl.common.entities.LicenceRequestResponseFactory;
import edu.p.lodz.pl.common.network.TCPConnection;

import java.io.IOException;
import java.net.ConnectException;

public class Client {
    private final String ip;
    private final int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public synchronized LicenceRequestResponse getLicenceToken(String LicenceUserName, String LicenceKey) {
        LicenceRequestResponse response;
        try (TCPConnection connection = new TCPConnection(ip, port)) {
            LicenceRequest request = new LicenceRequest(LicenceUserName, LicenceKey);
            connection.send(request.toJson());
            String payload = connection.recv();
            Gson gson = new Gson();
            response = gson.fromJson(payload, LicenceRequestResponse.class);
        } catch (ConnectException e) {
            response = LicenceRequestResponseFactory.createFailureResponse(LicenceUserName, "Connection error");
        } catch (IOException e) {
            e.printStackTrace();
            response = LicenceRequestResponseFactory.createFailureResponse(LicenceUserName, e.getMessage());
        }
        return response;
    }
}
