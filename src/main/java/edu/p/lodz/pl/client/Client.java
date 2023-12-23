package edu.p.lodz.pl.client;

import com.google.gson.Gson;
import edu.p.lodz.pl.common.entities.LicenceRequest;
import edu.p.lodz.pl.common.entities.LicenceRequestResponse;
import edu.p.lodz.pl.common.entities.LicenceRequestResponseFactory;
import edu.p.lodz.pl.common.utils.time.TimeUtils;
import edu.p.lodz.pl.common.utils.timer.TimerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;

public class Client {
    private final static Logger logger = LogManager.getLogger(Client.class);

    private final static long EXTRA_DELAY_TIME = 1;

    private final String ip;
    private final int port;

    private final TimerManager timerManager;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.timerManager = new TimerManager();
    }

    public synchronized LicenceRequestResponse getLicenceToken(String LicenceUserName, String LicenceKey) {
        logger.debug("Getting licence");
        LicenceRequestResponse response;
        try (TCPConnection connection = new TCPConnection(ip, port)) {
            LicenceRequest request = new LicenceRequest(LicenceUserName, LicenceKey);
            logger.debug("Sending request to server: " + request);
            connection.send(request.toJson());
            String payload = connection.recv();
            Gson gson = new Gson();
            response = gson.fromJson(payload, LicenceRequestResponse.class);
            logger.debug("Received response from server: " + response);
            if (response.getValid()) {
               int timeoutId = timerManager.requestNewTimeout(this::handleTimeout, TimeUtils.getTimeoutInSeconds(response.getExpirationDate()) + EXTRA_DELAY_TIME);
            }
        } catch (ConnectException e) {
            logger.debug("Connection error");
            response = LicenceRequestResponseFactory.createFailureResponse(LicenceUserName, "Connection error");
        } catch (SocketException e) {
            logger.debug("Socket exception");
            response = LicenceRequestResponseFactory.createFailureResponse(LicenceUserName, "Server closed without sending response");
        } catch (IOException e) {
            logger.debug("Other error: " + e.getMessage());
            response = LicenceRequestResponseFactory.createFailureResponse(LicenceUserName, "Other error");
        }
        return response;
    }

    public synchronized void handleTimeout() {
        logger.debug("Handling timeout");
        ClientAPI.getInstance().getLicenceToken();
    }

    public synchronized void close() {
        logger.debug("Closing client");
        timerManager.close();
    }
}
