package org.yeastrc.limelight.xml.open_pfind.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.limelight.xml.open_pfind.constants.Constants;

public class PSMAnnotationTypeSortOrder {

	public static List<SearchAnnotation> getPSMAnnotationTypeSortOrder() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();

		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.PFIND_ANNOTATION_TYPE_CALCULATED_FDR );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PFIND );
			annotations.add( annotation );
		}

		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.PFIND_ANNOTATION_TYPE_QVALUE );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PFIND );
			annotations.add( annotation );
		}
		
		return annotations;
	}
}
