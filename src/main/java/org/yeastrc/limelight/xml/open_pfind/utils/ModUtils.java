package org.yeastrc.limelight.xml.open_pfind.utils;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.yeastrc.limelight.xml.open_pfind.objects.ModLookup;
import org.yeastrc.limelight.xml.open_pfind.reader.PFindParamsFileReader;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModUtils {

    // pattern for finding the residue described by a mod definition
    private static Pattern modStringPattern = Pattern.compile( "^.+\\[([A-Z])\\]$" );

    /**
     * Get the static mods as a map of residue => mass
     *
     * @param pFindOutputDirectory
     * @param modLookup
     * @return
     * @throws IOException
     * @throws ConfigurationException
     */
    public static Map<String, BigDecimal> getStaticMods(File pFindOutputDirectory, ModLookup modLookup) throws Exception {

        Map<String, BigDecimal> staticMods = new HashMap<>();
        Collection<String> modStrings = PFindParamsFileReader.getStaticModStrings(pFindOutputDirectory);

        for( String modString : modStrings ) {
            BigDecimal modMass = modLookup.getModByName(modString);
            String residue = ModUtils.getResidueFromModString(modString);

            staticMods.put(residue, modMass);
        }

        return staticMods;
    }

    /**
     * Parse the modString for the residue
     *
      * @param modString
     * @return
     * @throws Exception
     */
    private static String getResidueFromModString(String modString) throws Exception {

        // these are in the form of "Carbamidomethyl[C]"
        Matcher m = modStringPattern.matcher(modString);
        if( !m.matches() ) {
            throw new Exception("Invalid syntax for mod, couldn't find residue: " + modString );
        }

        return m.group(1);
    }

}
