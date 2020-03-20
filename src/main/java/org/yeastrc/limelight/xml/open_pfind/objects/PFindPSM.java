package org.yeastrc.limelight.xml.open_pfind.objects;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class PFindPSM {

	private BigDecimal qValue;
	private BigDecimal massShift;
	private BigDecimal rawScore;
	private BigDecimal finalScore;
	private BigDecimal avgFragMassShift;
	private int specificity;

	private int scanNumber;
	private BigDecimal precursorNeutralMass;
	private int charge;

	private String peptideSequence;
	private Map<Integer,BigDecimal> modifications;
	private Collection<String> proteinNames;

	private boolean isDecoy;

	@Override
	public String toString() {
		return "PFindPSM{" +
				"qValue=" + qValue +
				", massShift=" + massShift +
				", rawScore=" + rawScore +
				", finalScore=" + finalScore +
				", avgFragMassShift=" + avgFragMassShift +
				", specificity=" + specificity +
				", scanNumber=" + scanNumber +
				", precursorNeutralMass=" + precursorNeutralMass +
				", charge=" + charge +
				", peptideSequence='" + peptideSequence + '\'' +
				", modifications=" + modifications +
				", proteinNames=" + proteinNames +
				", isDecoy=" + isDecoy +
				'}';
	}

	public boolean isDecoy() {
		return isDecoy;
	}

	public void setDecoy(boolean decoy) {
		isDecoy = decoy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PFindPSM pFindPSM = (PFindPSM) o;
		return scanNumber == pFindPSM.scanNumber &&
				charge == pFindPSM.charge &&
				precursorNeutralMass.equals(pFindPSM.precursorNeutralMass) &&
				peptideSequence.equals(pFindPSM.peptideSequence);
	}

	@Override
	public int hashCode() {
		return Objects.hash(scanNumber, precursorNeutralMass, charge, peptideSequence);
	}

	public BigDecimal getqValue() {
		return qValue;
	}

	public void setqValue(BigDecimal qValue) {
		this.qValue = qValue;
	}

	public BigDecimal getMassShift() {
		return massShift;
	}

	public void setMassShift(BigDecimal massShift) {
		this.massShift = massShift;
	}

	public BigDecimal getRawScore() {
		return rawScore;
	}

	public void setRawScore(BigDecimal rawScore) {
		this.rawScore = rawScore;
	}

	public BigDecimal getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(BigDecimal finalScore) {
		this.finalScore = finalScore;
	}

	public BigDecimal getAvgFragMassShift() {
		return avgFragMassShift;
	}

	public void setAvgFragMassShift(BigDecimal avgFragMassShift) {
		this.avgFragMassShift = avgFragMassShift;
	}

	public int getSpecificity() {
		return specificity;
	}

	public void setSpecificity(int specificity) {
		this.specificity = specificity;
	}

	public int getScanNumber() {
		return scanNumber;
	}

	public void setScanNumber(int scanNumber) {
		this.scanNumber = scanNumber;
	}

	public BigDecimal getPrecursorNeutralMass() {
		return precursorNeutralMass;
	}

	public void setPrecursorNeutralMass(BigDecimal precursorNeutralMass) {
		this.precursorNeutralMass = precursorNeutralMass;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public String getPeptideSequence() {
		return peptideSequence;
	}

	public void setPeptideSequence(String peptideSequence) {
		this.peptideSequence = peptideSequence;
	}

	public Map<Integer, BigDecimal> getModifications() {
		return modifications;
	}

	public void setModifications(Map<Integer, BigDecimal> modifications) {
		this.modifications = modifications;
	}

	public Collection<String> getProteinNames() {
		return proteinNames;
	}

	public void setProteinNames(Collection<String> proteinNames) {
		this.proteinNames = proteinNames;
	}
}
