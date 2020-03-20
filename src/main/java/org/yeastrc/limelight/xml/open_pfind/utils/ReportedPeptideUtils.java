package org.yeastrc.limelight.xml.open_pfind.utils;

import org.yeastrc.limelight.xml.open_pfind.objects.PFindPSM;
import org.yeastrc.limelight.xml.open_pfind.objects.PFindReportedPeptide;
import org.yeastrc.limelight.xml.open_pfind.objects.PFindResults;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class ReportedPeptideUtils {

    public static PFindReportedPeptide getReportedPeptide(String peptideSequence, Map<Integer, BigDecimal> mods) {

        PFindReportedPeptide reportedPeptide = new PFindReportedPeptide();

        reportedPeptide.setMods( mods );
        reportedPeptide.setNakedPeptide( peptideSequence );
        reportedPeptide.setReportedPeptideString( ReportedPeptideUtils.getReportedPeptideString(peptideSequence, mods ));

        return reportedPeptide;
    }

    /**
     * Get the string representation of a reported peptide in the form of: PEP[21.2933]TIDE
     * @param peptideSequence
     * @param mods
     * @return
     */
    private static String getReportedPeptideString(String peptideSequence, Map<Integer, BigDecimal> mods ) {

        if( mods == null || mods.size() < 1 )
            return peptideSequence;

        StringBuilder sb = new StringBuilder();

        // n-terminal mod
        if(mods.containsKey(0)) {
            BigDecimal mass = mods.get(0);

            sb.append( "n[" );
            sb.append( mass.setScale( 2, RoundingMode.HALF_UP ).toString() );
            sb.append( "]" );
        }

        for (int i = 0; i < peptideSequence.length(); i++){
            String r = String.valueOf( peptideSequence.charAt(i) );
            sb.append( r );

            if( mods.containsKey( i + 1 ) ) {

                BigDecimal mass = mods.get( i + 1 );

                sb.append( "[" );
                sb.append( mass.setScale( 2, RoundingMode.HALF_UP ).toString() );
                sb.append( "]" );

            }
        }

        // c-terminal mod
        if(mods.containsKey(peptideSequence.length() + 1)) {
            BigDecimal mass = mods.get(peptideSequence.length() + 1);

            sb.append( "c[" );
            sb.append( mass.setScale( 2, RoundingMode.HALF_UP ).toString() );
            sb.append( "]" );
        }

        return sb.toString();
    }

    public static boolean reportedPeptideOnlyContainsDecoys(PFindResults pfindResults, PFindReportedPeptide pfindReportedPeptide ) {

        for(PFindPSM psm : pfindResults.getPeptidePSMMap().get(pfindReportedPeptide)) {

            if( !psm.isDecoy() ) {
                return false;
            }
        }

        return true;
    }

}
