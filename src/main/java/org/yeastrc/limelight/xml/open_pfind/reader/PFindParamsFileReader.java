package org.yeastrc.limelight.xml.open_pfind.reader;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.yeastrc.limelight.xml.open_pfind.utils.INIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class PFindParamsFileReader {

    /**
     * Get the params file for an open pfind search
     *
     * @param pFindOutputDirectory
     * @return
     * @throws FileNotFoundException
     */
    public static File getParamsFile(File pFindOutputDirectory) throws FileNotFoundException {

        if(!pFindOutputDirectory.exists()) {
            throw new FileNotFoundException("Could not find directory: " + pFindOutputDirectory.getAbsolutePath());
        }

        File paramsDirectory = new File(pFindOutputDirectory, "param");
        if(!paramsDirectory.exists()) {
            throw new FileNotFoundException("Could not find directory: " + paramsDirectory.getAbsolutePath());
        }

        File paramsFile = new File(paramsDirectory, "pFind.cfg");
        if( !paramsFile.exists()) {
            throw new FileNotFoundException("Could not find file: " + paramsFile.getAbsolutePath());
        }

        return paramsFile;
    }


    /**
     * Get the version of pFind used in this search
     *
     * @param pFindOutputDirectory
     * @return
     * @throws IOException
     * @throws ConfigurationException
     */
    public static String getVersion(File pFindOutputDirectory) throws IOException, ConfigurationException {

        INIConfiguration config = new INIConfiguration();
        config.setExpressionEngine( INIUtils.EXPRESSION_ENGINE );
        config.read( new FileReader( getParamsFile(pFindOutputDirectory) ) );

        return config.getString( "Version/pFind_Version" );
    }

    public static Collection<String> getDynamicModStrings(File pFindOutputDirectory) throws IOException, ConfigurationException {

        Collection<String> modStrings = new HashSet<>();

        INIConfiguration config = new INIConfiguration();
        config.setExpressionEngine( INIUtils.EXPRESSION_ENGINE );
        config.read( new FileReader( getParamsFile(pFindOutputDirectory) ) );

        String fullModString =  config.getString( "param/selectmod" );
        if(fullModString != null) {
            if(fullModString.endsWith(";")) {
                // trim off the trailing semi colon, because, why
                fullModString = StringUtils.chop(fullModString);
            }

            if(fullModString.length() > 0) {
                modStrings.addAll(Arrays.asList(fullModString.split(";") ) );
            }
        }

        return modStrings;
    }

    public static Collection<String> getStaticModStrings(File pFindOutputDirectory) throws IOException, ConfigurationException {

        Collection<String> modStrings = new HashSet<>();

        INIConfiguration config = new INIConfiguration();
        config.setExpressionEngine( INIUtils.EXPRESSION_ENGINE );
        config.read( new FileReader( getParamsFile(pFindOutputDirectory) ) );

        String fullModString =  config.getString( "param/fixmod" );
        if(fullModString != null) {
            if(fullModString.endsWith(";")) {
                // trim off the trailing semi colon, because, why
                fullModString = StringUtils.chop(fullModString);
            }

            if(fullModString.length() > 0) {
                modStrings.addAll(Arrays.asList(fullModString.split(";") ) );
            }
        }

        return modStrings;
    }

}
