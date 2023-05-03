package org.palladiosimulator.dataflow.diagramgenerator;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingSEFFActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingUserActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.SEFFActionSequenceElement;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl;
import org.palladiosimulator.pcm.seff.impl.AbstractActionImpl;
import org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl;
import org.palladiosimulator.pcm.seff.impl.SetVariableActionImpl;
import org.palladiosimulator.pcm.seff.impl.StartActionImpl;
import org.palladiosimulator.pcm.seff.impl.StopActionImpl;
import org.palladiosimulator.pcm.usagemodel.impl.EntryLevelSystemCallImpl;

public class EntityUtility {
	public static String getEntityName(AbstractActionSequenceElement<?> element) {
		String name = null;
		if (element instanceof CallingUserActionSequenceElement) {
			name = ((CallingUserActionSequenceElement) element).getElement().getEntityName();
		} else if (element instanceof SEFFActionSequenceElement) {
			var innerElement = ((SEFFActionSequenceElement) element).getElement();
			name = ((AbstractActionImpl) ((SEFFActionSequenceElement) element).getElement()).getEntityName();
			if (innerElement instanceof ExternalCallActionImpl) {
				var i = 1;
			} else if (innerElement instanceof StartActionImpl) {
				var successor = ((StartActionImpl) innerElement).getSuccessor_AbstractAction();
				if (successor instanceof ExternalCallActionImpl) {
					name = ((ExternalCallActionImpl) successor).getEntityName();
				} else if (successor instanceof SetVariableActionImpl) {
					name = ((SetVariableActionImpl) successor).getEntityName();
				} else if (successor instanceof StopActionImpl) {
					name = ((StopActionImpl) successor).getEntityName();
				}
				else {
					throw new Error();
				}
				var i = 1;
			} else if (innerElement instanceof SetVariableActionImpl) {
				var i = 1;
			} else {
				throw new Error();
			}
		} else {
			throw new Error();
		}
		if (name == "aName") {
			var i = 1;
		}
		return name;
	}

	public static String getEntityId(AbstractActionSequenceElement<?> element) {
		if (element instanceof CallingUserActionSequenceElement) {
			var innerElement = ((CallingUserActionSequenceElement) element).getElement();
			if (innerElement instanceof EntryLevelSystemCallImpl) {
				EntryLevelSystemCallImpl test = (EntryLevelSystemCallImpl) innerElement;
				EList<VariableUsage> x = test.getInputParameterUsages_EntryLevelSystemCall();
				for (var y : x) {
					if (y instanceof VariableUsageImpl) {
						VariableUsageImpl variable = (VariableUsageImpl) y;

						var w = 1;
					}
					var z = 1;
				}
				var i = 1;
			} else {
				throw new Error();
			}
			return ((CallingUserActionSequenceElement) element).getElement().getId();
		} else if (element instanceof SEFFActionSequenceElement) {
			var innerElement = ((SEFFActionSequenceElement) element).getElement();
			if (innerElement instanceof StartActionImpl) {
				StartActionImpl test = (StartActionImpl) innerElement;
				var i = 1;
			} else if (innerElement instanceof ExternalCallActionImpl) {
				var i = 1;
			} else if (innerElement instanceof SetVariableActionImpl) {
				var i = 1;
			} else {
				throw new Error();
			}
			return ((AbstractActionImpl) ((SEFFActionSequenceElement) element).getElement()).getId();
		} else {
			throw new Error();
		}
	}
}
