package edu.p.lodz.pl.common.entities;

import edu.p.lodz.pl.common.utils.time.TimeUtils;

public class LicenceRequestResponseFactory {
    private LicenceRequestResponseFactory() {}

    public static LicenceRequestResponse createSuccessResponse(String LicenceUserName, long ValidationTime) {
        return new LicenceRequestResponse(LicenceUserName, true, "Licence token granted", TimeUtils.getNextExpirationDate(ValidationTime));
    }

    public static LicenceRequestResponse createFailureResponse(String LicenceUserName, String Description) {
        return new LicenceRequestResponse(LicenceUserName, false, Description, null);
    }
}
