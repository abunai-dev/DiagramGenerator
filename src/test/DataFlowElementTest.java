package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVisitor;

import java.util.ArrayList;
import java.util.List;

class DataFlowElementTest {

	@Test
	void testGetParameters() {
		DataFlowElement element = new TestDataFlowElement("id", true, "name");
		List<String> parameters = element.getParameters();

		Assertions.assertNotNull(parameters);
		Assertions.assertEquals(0, parameters.size());
	}

	@Test
	void testAddParameter() {
		DataFlowElement element = new TestDataFlowElement("id", true, "name");
		element.addParameter("param");

		List<String> parameters = element.getParameters();

		Assertions.assertEquals(1, parameters.size());
		Assertions.assertEquals("param", parameters.get(0));
	}

	@Test
	void testAddParameters() {
		DataFlowElement element = new TestDataFlowElement("id", true, "name");
		List<String> parameters = new ArrayList<>();
		parameters.add("param1");
		parameters.add("param2");

		element.addParameters(parameters);

		List<String> elementParameters = element.getParameters();

		Assertions.assertEquals(2, elementParameters.size());
		Assertions.assertEquals("param1", elementParameters.get(0));
		Assertions.assertEquals("param2", elementParameters.get(1));
	}

	@Test
	void testGetId() {
		DataFlowElement element = new TestDataFlowElement("id", true, "name");
		String id = element.getId();

		Assertions.assertEquals("id", id);
	}

	@Test
	void testGetIsCalling() {
		DataFlowElement element = new TestDataFlowElement("id", true, "name");
		Boolean isCalling = element.getIsCalling();

		Assertions.assertTrue(isCalling);
	}

	@Test
	void testGetName() {
		DataFlowElement element = new TestDataFlowElement("id", true, "name");
		String name = element.getName();

		Assertions.assertEquals("name", name);
	}

	@Test
	void testEquals() {
		DataFlowElement element1 = new TestDataFlowElement("id", true, "name");
		DataFlowElement element2 = new TestDataFlowElement("id", true, "name");
		DataFlowElement element3 = new TestDataFlowElement("id2", true, "name");
		DataFlowElement element4 = new TestDataFlowElement("id", false, "name");
		DataFlowElement element5 = new TestDataFlowElement("id", false, "name2");

		Assertions.assertEquals(element1, element2);
		Assertions.assertNotEquals(element1, element3);
		Assertions.assertNotEquals(element1, element4);
		Assertions.assertNotEquals(element1, element5);
	}

	private static class TestDataFlowElement extends DataFlowElement {
		public TestDataFlowElement(String id, Boolean isCalling, String name) {
			super(id, isCalling, name);
		}

		@Override
		public void accept(DataFlowElementVisitor visitor) {
			// Implementation not required for testing
		}
	}
}
