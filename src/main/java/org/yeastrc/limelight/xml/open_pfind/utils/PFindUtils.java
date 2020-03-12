package org.yeastrc.limelight.xml.open_pfind.utils;

import java.io.File;
import java.io.FileNotFoundException;

public class PFindUtils {

    /**
     * Get the directory containing the results of this pFind search
     *
     * @param pFindOutputDirectory
     * @return
     * @throws FileNotFoundException
     */
    public static File getResultDirectory(File pFindOutputDirectory) throws FileNotFoundException {
        if( !pFindOutputDirectory.exists()) {
            throw new FileNotFoundException("Could not find directory: " + pFindOutputDirectory.getAbsolutePath() );
        }

        if( !pFindOutputDirectory.isDirectory() ) {
            throw new FileNotFoundException("Could not find directory: " + pFindOutputDirectory.getAbsolutePath() );
        }

        File resultDir = new File(pFindOutputDirectory, "result");
        if( !resultDir.exists() ) {
            throw new FileNotFoundException("Could not find directory: " + resultDir.getAbsolutePath() );
        }

        if( !resultDir.isDirectory() ) {
            throw new FileNotFoundException("Could not find directory: " + resultDir.getAbsolutePath() );
        }

        return resultDir;
    }

    /**
     * Get the results file for this pFind search
     *
     * @param pFindOutputDirectory
     * @return
     * @throws FileNotFoundException
     */
    public static File getSpectraResultsFile(File pFindOutputDirectory) throws FileNotFoundException {
        File resultDirectory = getResultDirectory(pFindOutputDirectory);
        File resultsFile = new File(resultDirectory, "pFind.spectra");
        if( !resultsFile.exists()) {
            throw new FileNotFoundException("Could not find results file: " + resultsFile.getAbsolutePath() );
        }

        return resultsFile;
    }

    /**
     * Get the file containing all of the mods reported for this open pFind search
     * @param pFindOutputDirectory
     * @return
     * @throws FileNotFoundException
     */
    public static File getModsFile(File pFindOutputDirectory) throws FileNotFoundException {
        File resultDirectory = getResultDirectory(pFindOutputDirectory);
        File modFile = new File(resultDirectory, "1.mod");
        if( !modFile.exists()) {
            throw new FileNotFoundException("Could not find mods file: " + modFile.getAbsolutePath() );
        }

        return modFile;
    }

}
