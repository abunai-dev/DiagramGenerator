package org.palladiosimulator.dataflow.diagramgenerator;

public class CommandLineArgumentUtils {
	public static String getNextArgumentValue(String[] args, int index) {
		if (index + 1 < args.length) {
			return args[index + 1];
		} else {
			System.err.println("Missing value for option: " + args[index]);
			return null;
		}
	}
}
