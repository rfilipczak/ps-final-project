package edu.p.lodz.pl.server;

import edu.p.lodz.pl.common.entities.LicenceRequest;
import edu.p.lodz.pl.common.entities.LicenceRequestResponse;
import edu.p.lodz.pl.common.entities.LicenceRequestResponseFactory;
import edu.p.lodz.pl.common.utils.timer.TimerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LicenceManager {
    private final static Logger logger = LogManager.getLogger(LicenceManager.class);

    private static class LicenceManagerHolder {
        public final static LicenceManager instance = new LicenceManager();
    }

    private LicenceManager() {}
    public static LicenceManager getInstance() { return LicenceManagerHolder.instance; }


    private List<LicenceData> freeLicences = null;
    private final List<LicenceData> takenLicences = new ArrayList<>();

    private final TimerManager timerManager = new TimerManager();

    public void setFreeLicences(List<LicenceData> licenceDataList) { this.freeLicences = licenceDataList; }

    public LicenceRequestResponse handleLicenceRequest(LicenceRequest request) {
        logger.debug("Checking if licence is already taken");
        if (licenceAlreadyTaken(request))
        {
            return LicenceRequestResponseFactory.createFailureResponse(request.getLicenceUserName(), "Licence already taken");
        }

        logger.debug("Checking if licence key is valid");
        if (!request.getLicenceKey().equals(LicenceKeyGenerator.genKey(request.getLicenceUserName()))) {
            return LicenceRequestResponseFactory.createFailureResponse(request.getLicenceUserName(), "Invalid licence user name and/or licence key");
        }

        logger.debug("Checking if licence is free and exists");
        Optional<LicenceData> licenceData = freeLicences.stream().filter(data -> data.LicenceUserName().equals(request.getLicenceUserName())).findFirst();
        if (licenceData.isEmpty()) {
            return LicenceRequestResponseFactory.createFailureResponse(request.getLicenceUserName(), "Invalid licence user name and/or licence key");
        }

        grantLicence(licenceData.get());
        return LicenceRequestResponseFactory.createSuccessResponse(licenceData.get().LicenceUserName(), licenceData.get().ValidationTime());
    }

    public void restart(List<LicenceData> newLicenceData) {
        logger.debug("Restarting licence manager with new data");
        timerManager.cancelAllTimeouts();
        freeLicences = newLicenceData;
        takenLicences.clear();
    }

    private static class TimeoutHandler implements Runnable {
        private final LicenceData data;

        public TimeoutHandler(LicenceData data) {
            this.data = data;
        }

        @Override
        public void run() {
            logger.debug("Handling timeout");

            List<LicenceData> takenLicences = LicenceManager.getInstance().takenLicences;
            List<LicenceData> freeLicences = LicenceManager.getInstance().freeLicences;

            logger.debug("-- Before --");
            logger.debug("free: " + freeLicences);
            logger.debug("taken: " + takenLicences);

            takenLicences.remove(data);
            freeLicences.add(data);

            logger.debug("-- After --");
            logger.debug("free: " + freeLicences);
            logger.debug("taken: " + takenLicences);
        }
    }


    private void grantLicence(LicenceData licenceData) {
        logger.debug("-- Before --");
        logger.debug("free: " + freeLicences);
        logger.debug("taken: " + takenLicences);

        freeLicences.remove(licenceData);
        takenLicences.add(licenceData);

        logger.debug("-- After --");
        logger.debug("free: " + freeLicences);
        logger.debug("taken: " + takenLicences);

        TimeoutHandler timeoutHandler = new TimeoutHandler(licenceData);
        timerManager.requestNewTimeout(timeoutHandler, licenceData.ValidationTime());
    }

    private boolean licenceAlreadyTaken(LicenceRequest request) {
        return takenLicences
                .stream()
                .anyMatch(licenceData -> licenceData.LicenceUserName().equals(request.getLicenceUserName()));
    }

    public void close() {
        logger.debug("Closing licence manager");
        timerManager.close();
    }

}
