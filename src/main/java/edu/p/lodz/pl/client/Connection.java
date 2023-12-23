package edu.p.lodz.pl.client;

import java.io.IOException;

public interface Connection {
    void send(String payload);
    String recv() throws IOException;
}
