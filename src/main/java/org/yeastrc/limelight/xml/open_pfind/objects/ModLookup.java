package org.yeastrc.limelight.xml.open_pfind.objects;

import org.yeastrc.limelight.xml.open_pfind.reader.ModListReader;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class ModLookup {

    private final Map<String, BigDecimal> modsMap;

    /**
     * Get a ModLookup object for this search
     *
     * @param pFindOutputDirectory
     * @throws IOException
     */
    public ModLookup(File pFindOutputDirectory) throws IOException {
        this.modsMap = ModListReader.readModList(pFindOutputDirectory);
    }

    /**
     * Get the mass of the given mod
     *
     * @param name
     * @return
     */
    public BigDecimal getModByName(String name) {

        assert modsMap.containsKey(name) : "Mod wasn't found in pFind mods file";

        return modsMap.get(name);
    }

}
