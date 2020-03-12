/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *                  
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.open_pfind.main;

import org.yeastrc.limelight.xml.open_pfind.builder.XMLBuilder;
import org.yeastrc.limelight.xml.open_pfind.constants.CruxConstants;
import org.yeastrc.limelight.xml.open_pfind.objects.*;
import org.yeastrc.limelight.xml.open_pfind.reader.*;

public class ConverterRunner {

	// conveniently get a new instance of this class
	public static ConverterRunner createInstance() { return new ConverterRunner(); }
	
	
	public void convertOpenPfindToLimelightXML(ConversionParameters conversionParameters ) throws Throwable {


		System.err.print( "\nReading Percolator XML data into memory..." );
		PFindResults percResults = PercolatorResultsReader.getPercolatorResults( percolatorXMLFile );
		System.err.println( " Found " + percResults.getReportedPeptideResults().size() + " peptides. " );


		System.err.print( "Reading pepXML file..." );
		PFindResults PFindResults = TidePepXMLResultsParser.getTideResults( pepXMLFile );
		System.err.println( " Found " + PFindResults.getPeptidePSMMap().size() + " peptides. " );

		System.err.println( "Verifying all percolator results have comet results..." );
		TidePercolatorValidator.validateData(PFindResults, percResults );

		System.err.print( "\nWriting out XML..." );
		(new XMLBuilder()).buildAndSaveXML(
				conversionParameters,
				PFindResults,
				percResults,
				tideLogFile,
				percolatorLogFile
				);

		System.err.println( " Done." );

	}
}
