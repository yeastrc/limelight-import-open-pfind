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

package org.yeastrc.limelight.xml.open_pfind.annotation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterDirectionType;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterablePsmAnnotationType;
import org.yeastrc.limelight.xml.open_pfind.constants.Constants;



public class PSMAnnotationTypes {

	// open pfind scores
	public static final String PFIND_ANNOTATION_TYPE_QVALUE = "q-value";
	public static final String PFIND_ANNOTATION_TYPE_RAW_SCORE = "raw score";
	public static final String PFIND_ANNOTATION_TYPE_FINAL_SCORE = "final score";
	public static final String PFIND_ANNOTATION_TYPE_MASS_SHIFT = "mass shift";
	public static final String PFIND_ANNOTATION_TYPE_AVG_FRAG_MASS_SHIFT = "avg. frag. mass shift";
	public static final String PFIND_ANNOTATION_TYPE_SPECIFICITY = "specificity";

	public static final String PFIND_ANNOTATION_TYPE_CALCULATED_FDR = "Calculated FDR";

	
	
	public static List<FilterablePsmAnnotationType> getFilterablePsmAnnotationTypes() {
		List<FilterablePsmAnnotationType> types = new ArrayList<FilterablePsmAnnotationType>();

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_QVALUE );
			type.setDescription( "Q-value" );
			type.setFilterDirection( FilterDirectionType.BELOW );

			types.add( type );
		}

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_RAW_SCORE );
			type.setDescription( "Raw Score" );
			type.setFilterDirection( FilterDirectionType.ABOVE );

			types.add( type );
		}

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_FINAL_SCORE );
			type.setDescription( "Final Score" );
			type.setFilterDirection( FilterDirectionType.BELOW );

			types.add( type );
		}

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_MASS_SHIFT );
			type.setDescription( "Mass Shift" );
			type.setFilterDirection( FilterDirectionType.BELOW );

			types.add( type );
		}

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_AVG_FRAG_MASS_SHIFT );
			type.setDescription( "Average fragment mass shift" );
			type.setFilterDirection( FilterDirectionType.BELOW );

			types.add( type );
		}

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_SPECIFICITY );
			type.setDescription( "Specificity" );
			type.setFilterDirection( FilterDirectionType.ABOVE );

			types.add( type );
		}

		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( PFIND_ANNOTATION_TYPE_CALCULATED_FDR );
			type.setDescription( "Calculated FDR" );
			type.setFilterDirection( FilterDirectionType.BELOW );
			type.setDefaultFilterValue( new BigDecimal("0.01" ));

			types.add( type );
		}

		return types;
	}
	
	
}
