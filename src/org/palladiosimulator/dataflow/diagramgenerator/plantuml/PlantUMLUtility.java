package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLUtility {
	public static String concatActivityDiagramElements(String firstEntity, String secondEntity, String label) {
		if (label == null) {
			return "\"" + firstEntity + "\" --> \"" + secondEntity + "\"\n";
		} else {
			return "\"" + firstEntity + "\" --> [" + label + "] \"" + secondEntity + "\"\n";
		}
	}

	public static boolean convertSourceToSVG(String source, String path) {
		SourceStringReader reader = new SourceStringReader(source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// Write the first image to "os"
		try {
			String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			os.close();

			final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

			BufferedWriter writer = new BufferedWriter(new FileWriter("output/data-flow.svg"));
			writer.write(svg);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
