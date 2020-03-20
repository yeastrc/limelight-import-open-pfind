package org.yeastrc.limelight.xml.open_pfind.objects;

public class TargetDecoyCountFactory {

    public static TargetDecoyCounts getTargetDecoyCountsByFinalScore(PFindResults pfindResults) {

        TargetDecoyCounts tdCounts = new TargetDecoyCounts();

        for (PFindReportedPeptide pfrp : pfindResults.getPeptidePSMMap().keySet()) {
            for (PFindPSM psm : pfindResults.getPeptidePSMMap().get(pfrp)) {

                if (psm.isDecoy())
                    tdCounts.addDecoy(psm.getFinalScore());
                else
                    tdCounts.addTarget(psm.getFinalScore());

            }
        }

        return tdCounts;
    }
}
