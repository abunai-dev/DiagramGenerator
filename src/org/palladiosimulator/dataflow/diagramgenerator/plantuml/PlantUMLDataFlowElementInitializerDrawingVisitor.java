package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.ExternalEntityDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ProcessDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataStoreDataFlowElement;

public class PlantUMLDataFlowElementInitializerDrawingVisitor extends PlantUMLDataFlowElementDrawingVisitor {

	@Override
	public void visit(ProcessDataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += String.format("""
				"%s" [
				    shape = Mrecord;margin=0;padding=0;
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
				""", uniqueIdentifier, element.getName());

		this.setDrawResult(result);
	}

	@Override
	public void visit(ExternalEntityDataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += String.format("""
				"%s" [
				    shape = none;margin=0;padding=0;
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
				""", uniqueIdentifier, element.getName());

		this.setDrawResult(result);
	}

	@Override
	public void visit(DataStoreDataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += String.format("""
				"%s" [
				        shape=none;margin=0;padding=0;label=
				        <
				            <table border="1" cellborder="1" sides="tlb">
				                <tr>
				                	<td colspan="2" border="0" sides="ltb"><b>%s</b>  </td>
				                </tr>
				                // title end
				                // characteristics end
				                // variables end
				            </table>
				        >
				    ];
								""", uniqueIdentifier, element.getName());

		this.setDrawResult(result);
	}
}
