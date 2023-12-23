package edu.p.lodz.pl.common.network;

import java.io.IOException;

public interface Connection {
    void send(String payload);
    String recv() throws IOException;
}
