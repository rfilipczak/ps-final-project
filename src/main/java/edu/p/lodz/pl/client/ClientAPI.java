package edu.p.lodz.pl.client;

import edu.p.lodz.pl.common.entities.LicenceRequestResponse;
import edu.p.lodz.pl.common.entities.LicenceToken;
import edu.p.lodz.pl.common.utils.time.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientAPI {
    private final static Logger logger = LogManager.getLogger(ClientAPI.class);

    private static class ClientAPIHolder {
        public final static ClientAPI instance = new ClientAPI();
    }

    private ClientAPI() {}

    public static ClientAPI getInstance() {
        return ClientAPIHolder.instance;
    }

    private Client client;

    // Licence data
    private String LicenceUserName;
    private String LicenceKey;

    LicenceRequestResponse latestResponse;

    public synchronized void start(String ip, int port) {
        client = new Client(ip, port);
    }

    public synchronized void setLicence(String LicenceUserName, String LicenceKey) {
        this.LicenceUserName = LicenceUserName;
        this.LicenceKey = LicenceKey;
    }

    public synchronized String getLicenceToken() {
        if (licenceIsValid(latestResponse)) {
            return latestResponse.toJson();
        }
        LicenceRequestResponse response = client.getLicenceToken(LicenceUserName, LicenceKey);
        logger.debug("Received response from server: " + response);
        return response.toJson();
    }

    private boolean licenceIsValid(LicenceRequestResponse latestResponse) {
        return latestResponse != null && latestResponse.getValid() && !TimeUtils.isExpired(latestResponse.getExpirationDate());
    }
}
