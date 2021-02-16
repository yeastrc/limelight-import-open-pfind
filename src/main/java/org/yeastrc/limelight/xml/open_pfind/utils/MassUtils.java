package org.yeastrc.limelight.xml.open_pfind.utils;

import org.yeastrc.limelight.xml.open_pfind.constants.MassConstants;
import org.yeastrc.limelight.xml.open_pfind.objects.PFindPSM;

import java.math.BigDecimal;

public class MassUtils {

    /**
     * Get the calculated m/z for a precursor for a psm that is (neutral mass + charge * hydrogen mass) / charge
     *
     * @param psm
     * @return
     */
    public static BigDecimal getObservedMoverZForPsm(PFindPSM psm) {

        final double charge = psm.getCharge();
        final double observedMH = psm.getPrecursorMHPlus().doubleValue();
        final double observedMoverZ = (MassConstants.MASS_HYDROGEN_MONO.doubleValue() * (charge - 1) + observedMH) / charge;

        return BigDecimal.valueOf(observedMoverZ);
    }

}
