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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.yeastrc.limelight.xml.open_pfind.constants.Constants;
import org.yeastrc.limelight.xml.open_pfind.objects.ConversionParameters;
import org.yeastrc.limelight.xml.open_pfind.objects.ConversionProgramInfo;

import picocli.CommandLine;

@CommandLine.Command(name = "java -jar " + Constants.CONVERSION_PROGRAM_NAME,
		mixinStandardHelpOptions = true,
		version = Constants.CONVERSION_PROGRAM_NAME + " " + Constants.CONVERSION_PROGRAM_VERSION,
		sortOptions = false,
		synopsisHeading = "%n",
		descriptionHeading = "%n@|bold,underline Description:|@%n%n",
		optionListHeading = "%n@|bold,underline Options:|@%n",
		description = "Convert the results of an Open pFind analysis to a Limelight XML file suitable for import into Limelight.\n\n" +
				"More info at: " + Constants.CONVERSION_PROGRAM_URI
)
public class MainProgram implements Runnable {

	@CommandLine.Option(names = { "-d", "--directory" }, required = true, description = "Full path to the Open pFind output directory. E.g., c:\\my_data\\pFind3\\my_results  Should contain a \"param\" and \"result\" directory.")
	private File pFindOutputDirectory;

	@CommandLine.Option(names = { "-f", "--fasta-file" }, required = true, description = "Full path to FASTA file used in the experiment.")
	private File fastaFile;

	@CommandLine.Option(names = { "-o", "--out-file" }, required = true, description = "Full path to use for the Limelight XML output file (including file name).")
	private String outFile;

	@CommandLine.Option(names = { "-v", "--verbose" }, required = false, description = "If this parameter is present, error messages will include a full stacktrace. Helpful for debugging.")
	private boolean verboseRequested = false;

	private String[] args;

	public void run() {

		printRuntimeInfo();

		if( !fastaFile.exists() ) {
			System.err.println( "Could not find fasta file: " + fastaFile.getAbsolutePath() );
			System.exit( 1 );
		}

		if( !pFindOutputDirectory.exists() ) {
			System.err.println( "Could not find pFind directory: " + pFindOutputDirectory.getAbsolutePath() );
			System.exit( 1 );
		}

		if( !pFindOutputDirectory.isDirectory() ) {
			System.err.println( "pFind output directory isn't a directory: " + pFindOutputDirectory.getAbsolutePath() );
			System.exit( 1 );
		}

		ConversionProgramInfo cpi = ConversionProgramInfo.createInstance( String.join( " ",  args ) );
		ConversionParameters cp = new ConversionParameters(pFindOutputDirectory, fastaFile, outFile, cpi);

		try {
			ConverterRunner.createInstance().convertOpenPfindToLimelightXML(cp);
		} catch( Throwable t ) {

			if(verboseRequested) {
				t.printStackTrace();
			}

			System.err.println( "Encountered error during conversion: " + t.getMessage() );
			System.exit( 1 );
		}

		System.exit( 0 );
	}

	public static void main( String[] args ) {

		MainProgram mp = new MainProgram();
		mp.args = args;

		CommandLine.run(mp, args);
	}

	/**
	 * Print runtime info to STD ERR
	 * @throws Exception 
	 */
	public static void printRuntimeInfo() {

		try( BufferedReader br = new BufferedReader( new InputStreamReader( MainProgram.class.getResourceAsStream( "run.txt" ) ) ) ) {

			String line = null;
			while ( ( line = br.readLine() ) != null ) {

				line = line.replace( "{{URL}}", Constants.CONVERSION_PROGRAM_URI );
				line = line.replace( "{{VERSION}}", Constants.CONVERSION_PROGRAM_VERSION );

				System.err.println( line );
				
			}
			
			System.err.println( "" );

		} catch( Exception e) {
			;
		}
	}

}
