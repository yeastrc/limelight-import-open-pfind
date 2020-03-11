package org.yeastrc.limelight.xml.open_pfind.objects;

import java.io.File;

public class ConversionParameters {

    public ConversionParameters(File pFindOutputDirectory, File fastaFilePath, String outputFilePath, ConversionProgramInfo conversionProgramInfo) {
        this.openPfindOutputDirectory = pFindOutputDirectory;
        this.fastaFilePath = fastaFilePath;
        this.outputFilePath = outputFilePath;
        this.conversionProgramInfo = conversionProgramInfo;
    }

    public File getOpenPfindOutputDirectory() {
        return openPfindOutputDirectory;
    }

    public File getFastaFilePath() {
        return fastaFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public ConversionProgramInfo getConversionProgramInfo() {
        return conversionProgramInfo;
    }

    private File openPfindOutputDirectory;
    private File fastaFilePath;
    private String outputFilePath;
    private ConversionProgramInfo conversionProgramInfo;

}
