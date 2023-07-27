# DiagramGenerator
This generator creates beautiful data flow diagrams based on Palladio software architecture models.

## Installation

1. Install the version `2022-12` of the Eclipse Modelling Tools from the [official site](https://www.eclipse.org/downloads/packages/release/2022-12/r/eclipse-modeling-tools)
2. Clone the confidentiality analysis repository from [GitHub](https://github.com/PalladioSimulator/Palladio-Addons-DataFlowConfidentiality-Analysis).
3. Import the dependencies.p2f file into Eclipse to install the dependencies of the project. This is achieved by going to File->Import->General->Install from File
4. Clone the uncertainty impact analysis reposiotry from [GitHub](https://github.com/abunai-dev/UncertaintyImpactAnalysis)
5. Import all projects of the data flow analysis, uncertainty impact analysis and also all projects from the `bundles` and `tests` folders from this repository

## Usage

- The bundle `org.palladiosimulator.dataflow.diagramgenerator.tests` contains several test cases to test the functionality and to run the generator
- The bundle `org.palladiosimulator.dataflow.diagramgenerator.testmodels` contains PCM models for testing and evaluation
  - If you wish to use your own PCM models, you can create the models inside this bundle and adapt the test cases accordingly (or create a new one)
- The tests demonstrate how to use the generator to generate diagrams as SVG files using PlantUML as a drawing engine
- Output files are generated in the `output` folder of the `org.palladiosimulator.dataflow.diagramgenerator.tests` bundle