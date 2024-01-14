package edu.p.lodz.pl.server.utils;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LicenceDataLoader {
    private static final Logger logger = LogManager.getLogger(LicenceDataLoader.class);

    private LicenceDataLoader() {}

    public static List<LicenceData> loadLicenses(String filepath) {
        logger.info("Loading licences");
        List<LicenceData> licenses;
        Gson gson = new Gson();
        File input = new File(filepath);
        if (!input.exists()) {
            logger.error("Invalid filepath: " + filepath);
            return null;
        }
        try {
            licenses = gson.fromJson(new FileReader(input), LicenceData.listOf);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
        return licenses;
    }
}
