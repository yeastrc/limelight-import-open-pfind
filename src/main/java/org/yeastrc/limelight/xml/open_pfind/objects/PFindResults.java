package org.yeastrc.limelight.xml.open_pfind.objects;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class PFindResults {

	private Map<PFindReportedPeptide, Collection<PFindPSM>> peptidePSMMap;
	private Collection<String> staticMods;
	private Collection<String> dynamicMods;

	/**
	 * @return the peptidePSMMap
	 */
	public Map<PFindReportedPeptide, Collection<PFindPSM>> getPeptidePSMMap() {
		return peptidePSMMap;
	}
	/**
	 * @param peptidePSMMap the peptidePSMMap to set
	 */
	public void setPeptidePSMMap(Map<PFindReportedPeptide, Collection<PFindPSM>> peptidePSMMap) {
		this.peptidePSMMap = peptidePSMMap;
	}

	public Collection<String> getStaticMods() {
		return staticMods;
	}

	public void setStaticMods(Collection<String> staticMods) {
		this.staticMods = staticMods;
	}

	public Collection<String> getDynamicMods() {
		return dynamicMods;
	}

	public void setDynamicMods(Collection<String> dynamicMods) {
		this.dynamicMods = dynamicMods;
	}
}
