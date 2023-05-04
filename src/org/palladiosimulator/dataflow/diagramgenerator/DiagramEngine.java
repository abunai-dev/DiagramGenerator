package org.palladiosimulator.dataflow.diagramgenerator;

import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.ActionSequence;

public interface DiagramEngine {
	public void initialize();
	public void finish();
	public void consumeActionSequence(ActionSequence actionSequence);
}
