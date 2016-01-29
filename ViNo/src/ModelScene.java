import java.util.ArrayList;

public class ModelScene {
	
	// class variables
	private String name;
	private ArrayList<ModelSceneElement> elements;
	private int background;
	private int startElement;
	
	// main constructor
	public ModelScene(String nam, int bg) {
		name = nam;
		background = bg;
		elements = new ArrayList<ModelSceneElement>();
		startElement = 0;
	}
	
	public void addElement(ModelSceneElement obj) {
		elements.add(obj);
	}
	
	public void setStartElement(int e) {
		startElement = e;
	}
	
	public ModelSceneElement getElement(int i) {
		return elements.get(i);
	}
	
	public int getBackground() {
		return background;
	}
	
	public String getName() {
		return name;
	}
	
	public ModelSceneElement getStartElement() {
		return elements.get(startElement);
	}

}
