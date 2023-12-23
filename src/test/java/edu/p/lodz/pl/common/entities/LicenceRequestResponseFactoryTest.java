package edu.p.lodz.pl.common.entities;

import edu.p.lodz.pl.common.utils.time.TimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class LicenceRequestResponseFactoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createSuccessResponse() {
        long time = 600;
        String expirationDate = "2023-12-22T22:59:28.9287148";
        String licenceUserName = "Radek";
        LicenceRequestResponse expected = new LicenceRequestResponse(licenceUserName, true, "Licence token granted", expirationDate);

        try (MockedStatic<TimeUtils> timeUtilsMock = Mockito.mockStatic(TimeUtils.class)) {
            timeUtilsMock.when(() -> TimeUtils.getNextExpirationDate(time)).thenReturn(expirationDate);

            var response = LicenceRequestResponseFactory.createSuccessResponse(licenceUserName, time);
            Assertions.assertEquals(expected, response);
            Assertions.assertEquals(expected.toJson(), response.toJson());
        }
    }

    @Test
    void createFailureResponse() {
        String licenceUserName = "Radek";
        String description = "Test failure response";
        LicenceRequestResponse expected = new LicenceRequestResponse(licenceUserName, false, description, null);
        var response = LicenceRequestResponseFactory.createFailureResponse(licenceUserName, description);
        Assertions.assertEquals(expected, response);
        Assertions.assertEquals(expected.toJson(), response.toJson());
    }
}