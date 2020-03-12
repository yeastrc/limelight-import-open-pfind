package org.yeastrc.limelight.xml.open_pfind.reader;

import org.yeastrc.limelight.xml.open_pfind.utils.PFindUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ModListReader {

    public static Map<String, BigDecimal> readModList(File pFindOutputDirectory) throws IOException {

        Map<String, BigDecimal> modsMap = new HashMap<>();

        File modsFile = PFindUtils.getModsFile(pFindOutputDirectory);

        try (BufferedReader br = new BufferedReader(new FileReader(modsFile))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {

                if (line.startsWith("label_name"))
                    continue;

                if (line.startsWith("@"))
                    continue;

                if (line.startsWith("name"))
                    continue;

                // we're on a line we care about, parse it

                String[] fields = line.split("#");
                assert fields.length == 2;

                String modName = fields[0];

                fields = fields[1].split("\\s+");
                assert fields.length == 5;

                BigDecimal mass = new BigDecimal(fields[2]);

                modsMap.put(modName, mass);
            }
        }

        return modsMap;
    }
}
