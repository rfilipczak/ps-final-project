package edu.p.lodz.pl.server;

import org.apache.commons.codec.digest.DigestUtils;

public class LicenceKeyGenerator {
    private LicenceKeyGenerator() {}

    public static String genKey(String licenceUserName) {
        return DigestUtils.md5Hex(licenceUserName);
    }
}
