package org.palladiosimulator.dataflow.diagramgenerator.dfd;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DrawingStrategy;

import mdpa.dfd.dataflowdiagram.impl.DataFlowDiagramImpl;
import mdpa.dfd.dataflowdiagram.impl.NodeImpl;
import mdpa.dfd.dataflowdiagram.impl.dataflowdiagramFactoryImpl;

public class DFDDrawingStrategy implements DrawingStrategy {

	@Override
	public void generate(List<DataFlowNode> dataFlowNodes) {
		dataflowdiagramFactoryImpl dfdFactory = new dataflowdiagramFactoryImpl();

		DataFlowDiagramImpl dfd = (DataFlowDiagramImpl) dfdFactory.createDataFlowDiagram();

		DFDDataFlowElementVisitor visitor = new DFDDataFlowElementVisitor();
		
		List<NodeImpl> dfdNodes = new ArrayList<>();

		for (DataFlowNode node : dataFlowNodes) {
			DataFlowElement element = node.getElement();

			NodeImpl dfdNode = (NodeImpl) element.accept(visitor);
			
			dfdNodes.add(dfdNode);
		}
	}

	@Override
	public boolean saveToDisk(String path) {
//		ResourceSet resourceSet = new ResourceSetImpl();
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
//		.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
//		resourceSet.getPackageRegistry().put(dataflowdiagramPackage.eNS_URI,
//				dataflowdiagramPackage.eINSTANCE);
//
//		Resource resource = resourceSet.createResource(URI.createFileURI("output/changedModel.dataflowdiagrammodel"));
//
//		resource.getContents().add(dfd);
		return false;
	}

}
