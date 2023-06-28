package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DrawingStrategy;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLDrawingStrategy implements DrawingStrategy {
	private String source;

	public PlantUMLDrawingStrategy() {
		super();
		this.source = "";
	}

	@Override
	public void generate(List<DataFlowNode> dataFlowNodes) {
		this.initialize();
		// first, initialize all elements
		for (DataFlowNode node : dataFlowNodes) {
			DataFlowElement element = node.getElement();

			PlantUMLDataFlowElementInitializerDrawingVisitor drawingVisitor = new PlantUMLDataFlowElementInitializerDrawingVisitor();
			element.accept(drawingVisitor);
			this.addToSource(drawingVisitor.getDrawResult());

			PlantUMLDataFlowLiteralEmbeddingDrawingVisitor literalEmbeddingVisitor = new PlantUMLDataFlowLiteralEmbeddingDrawingVisitor(
					this.source);
			node.accept(literalEmbeddingVisitor);
			this.source = literalEmbeddingVisitor.getDrawResult();

			PlantUMLDataFlowVariableEmbeddingDrawingVisitor variableVisitor = new PlantUMLDataFlowVariableEmbeddingDrawingVisitor(
					this.source);
			node.accept(variableVisitor);
			this.source = variableVisitor.getDrawResult();
		}

		// second, draw the edges inbetween
		for (DataFlowNode node : dataFlowNodes) {
			PlantUMLDataFlowNodeDrawingVisitor drawingVisitor = new PlantUMLDataFlowNodeDrawingVisitor();
			node.accept(drawingVisitor);
			this.addToSource(drawingVisitor.getDrawResult());
		}
		this.finish();
	}

	private void addToSource(String addition) {
		this.source += addition;
	}

	@Override
	public boolean saveToDisk(String path) {
		SourceStringReader reader = new SourceStringReader(this.source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// Write the first image to "os"
		try {
			String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			os.close();

			final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(svg);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private void initialize() {
		this.source += "@startuml\n";
		this.source += "digraph dfd {\n";
	}

	private void finish() {
		this.source += "}\n";
		this.source += "@enduml";
		System.out.println(this.source);
	}
}
