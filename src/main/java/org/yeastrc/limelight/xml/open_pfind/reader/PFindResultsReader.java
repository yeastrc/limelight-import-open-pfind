package org.yeastrc.limelight.xml.open_pfind.reader;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.yeastrc.limelight.xml.open_pfind.objects.ModLookup;
import org.yeastrc.limelight.xml.open_pfind.objects.PFindPSM;
import org.yeastrc.limelight.xml.open_pfind.objects.PFindReportedPeptide;
import org.yeastrc.limelight.xml.open_pfind.objects.PFindResults;
import org.yeastrc.limelight.xml.open_pfind.utils.PFindUtils;
import org.yeastrc.limelight.xml.open_pfind.utils.ReportedPeptideUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PFindResultsReader {

    public static PFindResults getPFindResults(File pFindOutputDirectory) throws Throwable {

        // get dynamic mods
        Collection<String> dynamicMods = PFindParamsFileReader.getDynamicModStrings(pFindOutputDirectory);

        // get static mods
        Collection<String> staticMods = PFindParamsFileReader.getStaticModStrings(pFindOutputDirectory);

        // get the results
        Map<PFindReportedPeptide, Collection<PFindPSM>> psmMap = getPFindPSMs( pFindOutputDirectory, dynamicMods, staticMods );

        PFindResults results = new PFindResults();
        results.setPeptidePSMMap( psmMap );
        results.setDynamicMods( dynamicMods );
        results.setStaticMods( staticMods );

        return results;
    }

    public static Map<PFindReportedPeptide, Collection<PFindPSM>> getPFindPSMs(File pFindOutputDirectory, Collection<String> dynamicMod, Collection<String> staticMods) throws IOException {

        File resultsFile = PFindUtils.getSpectraResultsFile( pFindOutputDirectory );
        ModLookup modLookup = new ModLookup(pFindOutputDirectory);
        Map<PFindReportedPeptide, Collection<PFindPSM>> results = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(resultsFile))) {

            // skip first line
            br.readLine();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] fields = line.split("\t");

                assert fields.length == 19 : "Got invalid number of fields for PSM on line: " + line;

                // NO DECOYS!
                if(fields[15].equals( "decoy"))
                    continue;

                //String fileName = fields[0];
                int scanNumber = Integer.parseInt(fields[1]);
                BigDecimal precursorMass = new BigDecimal(fields[2]);
                int charge = Integer.parseInt(fields[3]);
                BigDecimal qValue = new BigDecimal(fields[4]);
                String peptideSequence = fields[5];
                BigDecimal massShift = new BigDecimal(fields[7]);
                BigDecimal rawScore = new BigDecimal(fields[8]);
                BigDecimal finalScore = new BigDecimal(fields[9]);
                String modsList = fields[10];
                int specificity = Integer.parseInt(fields[11]);
                //String proteinList = fields[12];
                BigDecimal avgFragMassShift = new BigDecimal(fields[17]);

                Map<Integer, BigDecimal> dynamicMods = getModsFromModsList(modsList, modLookup, staticMods);

                // the reported peptide
                PFindReportedPeptide reportedPeptide = ReportedPeptideUtils.getReportedPeptide(peptideSequence, dynamicMods);
                if(!results.containsKey(reportedPeptide)) {
                    results.put(reportedPeptide, new HashSet<>());
                }

                PFindPSM psm = new PFindPSM();
                psm.setAvgFragMassShift( avgFragMassShift );
                psm.setCharge( charge );
                psm.setFinalScore( finalScore );
                psm.setMassShift( massShift );
                psm.setModifications( dynamicMods );
                psm.setPeptideSequence( peptideSequence );
                psm.setPrecursorNeutralMass( precursorMass );
                psm.setqValue( qValue );
                psm.setRawScore( rawScore );
                psm.setScanNumber( scanNumber );
                psm.setSpecificity( specificity );

                results.get(reportedPeptide).add( psm );
            }
        }

        return results;
    }

    /**
     * Get the mods for this PSM encoded as a semi-colon-delimited string in the form of:
     * 5,Carbamidomethyl[C];6,Carbamidomethyl[C];13,Carbamidomethyl[C];
     *
     * @param modsList The semi-colon-delimited list of mods
     * @param modLookup
     * @param staticMods
     * @return
     * @throws IOException
     */
    private static Map<Integer, BigDecimal> getModsFromModsList(String modsList, ModLookup modLookup, Collection<String> staticMods) throws IOException {

        Map<Integer, BigDecimal> mods = new HashMap<>();

        if(modsList != null && modsList.length() > 0) {

            StringUtils.chop(modsList); // remove trailing ;
            if(modsList.length() > 0) {
                String[] modStrings = modsList.split(";");
                for(String modString : modStrings) {
                    String[] fields = modString.split(",");

                    assert fields.length == 2 : "Invalid mod syntax: " + modString;

                    if(staticMods.contains(fields[1])) {
                        continue;   // static mod, don't use it
                    }

                    int position = Integer.parseInt(fields[0]);
                    BigDecimal modMass = modLookup.getModByName(fields[1]);

                    mods.put(position, modMass);
                }
            }
        }

        return mods;
    }
}
