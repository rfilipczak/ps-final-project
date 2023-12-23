package edu.p.lodz.pl.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class Server implements Runnable, AutoCloseable {
    private final static Logger logger = LogManager.getLogger(Server.class);

    private final ServerSocket serverSocket;


    public Server(int port, String path) throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Started server");
        loadLicenceData(path);
    }

    public void loadLicenceData(String path) {
        List<LicenceData> licenceDataList = LicenceDataLoader.loadLicenses(path);
        logger.info(licenceDataList);
        LicenceManager.getInstance().restart(licenceDataList);
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                logger.debug("Waiting for client");
                Socket clientSocket = serverSocket.accept();
                logger.debug("Client joined");
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            } catch (SocketException e) {
                logger.debug("Server closed while waiting for client");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void close() throws Exception {
        serverSocket.close();
        LicenceManager.getInstance().close();
        logger.info("Closed server");
    }
}
