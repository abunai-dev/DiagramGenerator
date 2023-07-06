package org.palladiosimulator.dataflow.diagramgenerator;

public class CommandLineParser {
	public static GeneratorOptions parseCommandLineOptions(String[] args) {
		GeneratorOptions options = GeneratorOptions.getInstance();

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "-p":
			case "--project":
				options.setProjectName(CommandLineArgumentUtils.getNextArgumentValue(args, i));
				break;

			case "-u":
			case "--usageModel":
				options.setUsageModelPath(CommandLineArgumentUtils.getNextArgumentValue(args, i));
				break;

			case "-a":
			case "--allocation":
				options.setAllocationPath(CommandLineArgumentUtils.getNextArgumentValue(args, i));
				break;
				
			case "-c":
			case "--characteristics":
				options.setCharacteristicsPath(CommandLineArgumentUtils.getNextArgumentValue(args, i));
				break;

			default:
				System.err.println("Unknown option: " + arg);
				return null;
			}
		}

		if (!options.isValid()) {
			System.err.println("Missing required options");
			return null;
		}

		return options;
	}
}
