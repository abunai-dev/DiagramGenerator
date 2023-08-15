package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.ExternalEntityDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ProcessDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataStoreDataFlowElement;

public class PlantUMLDataFlowElementInitializerDrawingVisitor extends PlantUMLDataFlowElementDrawingVisitor {

	@Override
	public Void visit(ProcessDataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += String.format("""
				"%s" [
				    shape = Mrecord;margin=0;padding=0;
				    %s
				    label =
				    <
				        <table border="0" cellspacing="0" cellborder="1">
				            <tr>
				                <td colspan="3" border="0"><b>%s</b>  </td>
				            </tr>
				            // title end
				            // characteristics end
				            // variables end
				        </table>
				    >
				];
				""", uniqueIdentifier, element.isViolation() ? "color = red;" : "", element.getName());

		this.setDrawResult(result);
		return null;
	}

	@Override
	public Void visit(ExternalEntityDataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += String.format("""
				"%s" [
				    shape = none;margin=0;padding=0;
				    %s
				    label =
				    <
				        <table border="1" cellspacing="0" cellborder="1">
				            <tr>
				                <td colspan="3" border="0"><b>%s</b>  </td>
				            </tr>
				            // title end
				            // characteristics end
				            // variables end
				        </table>
				    >
				];
				""", uniqueIdentifier, element.isViolation() ? "color = red;" : "", element.getName());

		this.setDrawResult(result);
		return null;
	}

	@Override
	public Void visit(DataStoreDataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += String.format("""
				"%s" [
				        shape=none;%smargin=0;padding=0;label=
				        <
				            <table border="1" cellspacing="0" cellborder="1">
				                <tr>
				                	<td colspan="1" border="1" sides="r"> </td>
				                	<td colspan="2" border="0"><b>%s</b>  </td>
				                	<td colspan="1" border="1" sides="l"> </td>
				                </tr>
				                // title end
				                // characteristics end
				                // variables end
				            </table>
				        >
				    ];
								""", uniqueIdentifier, element.isViolation() ? "color = red;" : "", element.getName());

		this.setDrawResult(result);
		return null;
	}
}
