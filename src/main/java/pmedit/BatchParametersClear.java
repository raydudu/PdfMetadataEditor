package pmedit;

import java.awt.Frame;

public class BatchParametersClear extends BatchParametersEdit {

	public BatchParametersClear(BatchOperationParameters params, Frame owner) {
		super( params, owner);
		defaultMetadataPane.disableEdit();
		setMessage();
	}

}
