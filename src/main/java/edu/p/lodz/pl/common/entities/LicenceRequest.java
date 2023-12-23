package edu.p.lodz.pl.common.entities;

import java.util.Objects;

public class LicenceRequest extends Serializable {
    private final String LicenceUserName;
    private final String LicenceKey;

    public LicenceRequest(String licenceUserName, String licenceKey) {
        LicenceUserName = licenceUserName;
        LicenceKey = licenceKey;
    }

    public String getLicenceUserName() {
        return LicenceUserName;
    }

    public String getLicenceKey() {
        return LicenceKey;
    }

    @Override
    public String toString() {
        return "LicenceRequest{" +
                "LicenceUserName='" + LicenceUserName + '\'' +
                ", LicenceKey='" + LicenceKey + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LicenceRequest that = (LicenceRequest) o;
        return Objects.equals(LicenceUserName, that.LicenceUserName) && Objects.equals(LicenceKey, that.LicenceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LicenceUserName, LicenceKey);
    }
}
