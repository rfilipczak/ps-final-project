package edu.p.lodz.pl.common.entities;

import java.util.Objects;

public class LicenceRequestResponse extends Serializable {
    private final String LicenceUserName;
    private final Boolean Valid;
    private final String Description;
    private final String ExpirationDate;

    public LicenceRequestResponse(String licenceUserName, Boolean valid, String description, String expirationDate) {
        LicenceUserName = licenceUserName;
        Valid = valid;
        Description = description;
        ExpirationDate = expirationDate;
    }


    public String getLicenceUserName() {
        return LicenceUserName;
    }

    public Boolean getValid() {
        return Valid;
    }

    public String getDescription() {
        return Description;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    @Override
    public String toString() {
        return "LicenceRequestResponse{" +
                "LicenceUserName='" + LicenceUserName + '\'' +
                ", Valid=" + Valid +
                ", Description='" + Description + '\'' +
                ", ExpirationDate='" + ExpirationDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LicenceRequestResponse that = (LicenceRequestResponse) o;
        return Objects.equals(LicenceUserName, that.LicenceUserName) && Objects.equals(Valid, that.Valid) && Objects.equals(Description, that.Description) && Objects.equals(ExpirationDate, that.ExpirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LicenceUserName, Valid, Description, ExpirationDate);
    }
}
