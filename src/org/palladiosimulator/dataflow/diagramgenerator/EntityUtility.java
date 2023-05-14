package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingSEFFActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingUserActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.SEFFActionSequenceElement;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.impl.OperationSignatureImpl;
import org.palladiosimulator.pcm.repository.impl.ParameterImpl;
import org.palladiosimulator.pcm.seff.impl.AbstractActionImpl;
import org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl;
import org.palladiosimulator.pcm.seff.impl.ProbabilisticBranchTransitionImpl;
import org.palladiosimulator.pcm.seff.impl.ResourceDemandingBehaviourImpl;
import org.palladiosimulator.pcm.seff.impl.ResourceDemandingSEFFImpl;
import org.palladiosimulator.pcm.seff.impl.SetVariableActionImpl;
import org.palladiosimulator.pcm.seff.impl.StartActionImpl;
import org.palladiosimulator.pcm.seff.impl.StopActionImpl;
import org.palladiosimulator.pcm.usagemodel.impl.EntryLevelSystemCallImpl;

import gen.lib.dotgen.sameport__c;

public class EntityUtility {
	public static String getEntityName(AbstractActionSequenceElement<?> element) {
		String name = null;
		if (element instanceof CallingUserActionSequenceElement cuase) {
			name = ((CallingUserActionSequenceElement) element).getElement().getEntityName();
		} else if (element instanceof SEFFActionSequenceElement sase) {
			var innerElement = sase.getElement();
			name = ((AbstractActionImpl) ((SEFFActionSequenceElement) element).getElement()).getEntityName();
			if (innerElement instanceof ExternalCallActionImpl eca) {
			} else if (innerElement instanceof StartActionImpl sa) {
				var seff = sa.getResourceDemandingBehaviour_AbstractAction();
				if (seff instanceof ResourceDemandingSEFFImpl rds) {
					OperationSignatureImpl operation = (OperationSignatureImpl) rds.getDescribedService__SEFF();
					name = operation.getEntityName();
				} else if (seff instanceof ResourceDemandingBehaviourImpl rdb) {
					ProbabilisticBranchTransitionImpl branch = (ProbabilisticBranchTransitionImpl) rdb.getAbstractBranchTransition_ResourceDemandingBehaviour();
					name = branch.getEntityName();
				}
				else {
					throw new Error("Not implemented");
				}				
			} else if (innerElement instanceof SetVariableActionImpl sva) {
			} else {
				throw new Error("Not implemented");
			}
		} else {
			throw new Error("Not implemented");
		}
		return name;
	}

	public static Boolean getIsCalling(AbstractActionSequenceElement element) {
		if (element instanceof CallingUserActionSequenceElement cuase) {
			return cuase.isCalling();
		} else if (element instanceof CallingSEFFActionSequenceElement csase) {
			return csase.isCalling();
		}

		return null;
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

	public static String getParameterString(AbstractActionSequenceElement element) {
		String result = "";
		if (element instanceof SEFFActionSequenceElement<?> sase) {
			List<Parameter> parameters = sase.getParameter();
			for (var entry : parameters) {
				ParameterImpl parameter = (ParameterImpl) entry;
				if (result.length() == 0) {
					result = parameter.getParameterName();
				} else {
					result += ", " + parameter.getParameterName();
				}
			}
		}

		return result;
	}
}
