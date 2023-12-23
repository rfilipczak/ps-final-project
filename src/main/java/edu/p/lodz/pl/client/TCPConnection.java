package edu.p.lodz.pl.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class TCPConnection implements Connection, AutoCloseable {
    private static final Logger logger = LogManager.getLogger(TCPConnection.class);

    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;

    public TCPConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        logger.info("Started connection");
    }

    @Override
    public void send(String payload) {
        try {
            out.write(payload);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String recv() throws IOException {
        return in.readLine();
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Closed connection");
    }
}
