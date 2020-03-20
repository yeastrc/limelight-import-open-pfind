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
import java.util.*;

public class PFindResultsReader {

    public static PFindResults getPFindResults(File pFindOutputDirectory) throws Throwable {

        // map of all possible mods
        ModLookup modLookup = new ModLookup(pFindOutputDirectory);

        // get dynamic mods
        Collection<String> dynamicMods = PFindParamsFileReader.getDynamicModStrings(pFindOutputDirectory);

        // get static mods
        Collection<String> staticMods = PFindParamsFileReader.getStaticModStrings(pFindOutputDirectory);

        // get the results
        Map<PFindReportedPeptide, Collection<PFindPSM>> psmMap = getPFindPSMs( pFindOutputDirectory, dynamicMods, staticMods, modLookup );

        PFindResults results = new PFindResults();
        results.setPeptidePSMMap( psmMap );
        results.setDynamicMods( dynamicMods );
        results.setStaticMods( staticMods );
        results.setModLookup( modLookup );

        return results;
    }

    public static Map<PFindReportedPeptide, Collection<PFindPSM>> getPFindPSMs(File pFindOutputDirectory, Collection<String> dynamicMod, Collection<String> staticMods, ModLookup modLookup) throws Exception {

        File resultsFile = PFindUtils.getSpectraResultsFile( pFindOutputDirectory );
        Map<PFindReportedPeptide, Collection<PFindPSM>> results = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(resultsFile))) {

            // skip first line
            br.readLine();

            for (String line = br.readLine(); line != null; line = br.readLine()) {

                String[] fields = line.split("\t");

                if(fields[4].equals( "1024" ) || fields[4].equals( "512" )) {    // weird lines with no peptide and a q-value of 1024 or 512... skip
                    continue;
                }

                if( fields.length != 19 )
                    throw new Exception("Got invalid number of fields for PSM on line: " + line);

                // NO DECOYS!
//                if(fields[15].equals( "decoy"))
//                    continue;

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
                String proteinList = fields[12];
                BigDecimal avgFragMassShift = new BigDecimal(fields[17]);

                boolean isDecoy = false;
                if(fields[15].equals( "decoy")) {
                    isDecoy = true;
                }

                Map<Integer, BigDecimal> dynamicMods = getModsFromModsList(modsList, modLookup, staticMods);

                // the reported peptide
                PFindReportedPeptide reportedPeptide = ReportedPeptideUtils.getReportedPeptide(peptideSequence, dynamicMods);
                if(!results.containsKey(reportedPeptide)) {
                    results.put(reportedPeptide, new HashSet<>());
                }

                // the collection of protein matches
                Collection<String> proteinMatches = getProteinsFromList(proteinList);

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
                psm.setProteinNames( proteinMatches );
                psm.setDecoy(isDecoy);

                results.get(reportedPeptide).add( psm );

                // this will ensure the reported peptide in the map has these protein matches
                reportedPeptide.setProteinMatches( proteinMatches );
            }
        }

        return results;
    }

    /**
     * Expand list of protein names into a collection of names
     *
     * @param proteinList
     * @return
     */
    private static Collection<String> getProteinsFromList(String proteinList) {
        // in the form of: sp|P02768|ALBU_HUMAN/tr|A0A087WWT3|A0A087WWT3_HUMAN/tr|A0A0C4DGB6|A0A0C4DGB6_HUMAN/tr|B7WNR0|B7WNR0_HUMAN/tr|C9JKR2|C9JKR2_HUMAN/tr|D6RHD5|D6RHD5_HUMAN/tr|H0YA55|H0YA55_HUMAN/sp|P02768-2|ALBU_HUMAN/sp|P02768-3|ALBU_HUMAN/
        // split on /

        // remove trailing /
        proteinList = StringUtils.chomp(proteinList);

        String[] proteinNames = proteinList.split("/" );
        return Arrays.asList(proteinNames);
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
    private static Map<Integer, BigDecimal> getModsFromModsList(String modsList, ModLookup modLookup, Collection<String> staticMods) throws Exception {

        Map<Integer, BigDecimal> mods = new HashMap<>();

        if(modsList != null && modsList.length() > 0) {

            StringUtils.chop(modsList); // remove trailing ;
            if(modsList.length() > 0) {
                String[] modStrings = modsList.split(";");
                for(String modString : modStrings) {
                    String[] fields = modString.split(",");

                    if(fields.length != 2) {
                        throw new Exception( "Invalid mod syntax: " + modString );
                    }

                    if(staticMods.contains(fields[1])) {
                        continue;   // static mod, don't use it
                    }

                    int position = Integer.parseInt(fields[0]);
                    BigDecimal modMass = modLookup.getModByName(fields[1]);

                    if( mods.containsKey(position) ) {
                        modMass = modMass.add(mods.get(position));
                    }

                    mods.put(position, modMass);
                }
            }
        }

        return mods;
    }
}
