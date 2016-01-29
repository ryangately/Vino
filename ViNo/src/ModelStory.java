import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ModelStory {
	
	// class variables
	private String title;
	private ArrayList<BufferedImage> backgrounds;
	private ArrayList<BufferedImage> actors;
	private ArrayList<BufferedImage> explorationObjects;
	private ArrayList<ModelScene> scenes;
	private ArrayList<ModelSwitch> switches;
	private ArrayList<ModelCounter> counters;
	private int startPoint;
	
	// constructor
	public ModelStory(String title) throws IOException {
		this.title = title;
		backgrounds = new ArrayList<BufferedImage>();
		actors = new ArrayList<BufferedImage>();
		explorationObjects = new ArrayList<BufferedImage>();
		scenes = new ArrayList<ModelScene>();
		switches = new ArrayList<ModelSwitch>();
		counters = new ArrayList<ModelCounter>();
		startPoint = 0;
		actors.add(ImageIO.read(new File("images/empty.png")));
	}
	
	/** MUTATORS */
	// adds a background image to the internal db
	public void addBackground(String path) throws IOException {
		backgrounds.add(ImageIO.read(new File(path)));
	}
	
	// adds an actor image to the internal db
	public void addActor(String path) throws IOException {
		actors.add(ImageIO.read(new File(path)));
	}
	
	public void addExplorationObject(String path) throws IOException {
		explorationObjects.add(ImageIO.read(new File(path)));
	}
	
	// adds a scene to the internal db
	public void addScene(ModelScene scene) {
		scenes.add(scene);
	}
	
	// changes the start point to (scene, element)
	public void setStartPoint(int s) {
		startPoint = s;
	}
	
	// adds a boolean variable to the internal db
	public void addSwitch(ModelSwitch s) {
		switches.add(s);
	}
	
	// adds a counter variable to the internal db
	public void addCounter(ModelCounter c) {
		counters.add(c);
	}
	
	
	
	/** ACCESSORS */
	// returns the number of backgrounds in the db
	public int getBackgroundSize() {
		return backgrounds.size();
	}
	
	// returns the number of actor images in the db
	public int getActorsSize() {
		return actors.size();
	}
	
	public int getExplorationSize() {
		return explorationObjects.size();
	}
	
	// returns the number of scenes in the db
	public int getScenesSize() {
		return scenes.size();
	}
	
	public int getSwitchesSize() {
		return switches.size();
	}
	
	public int getCountersSize() {
		return counters.size();
	}
	
	// returns the name of the story
	public String getTitle() {
		return title;
	}
	
	// returns the starting point for the story
	public ModelScene getStartPoint() {
		return scenes.get(startPoint);
	}
	
	public ModelScene getScene(int index) {
		return scenes.get(index);
	}
	
	public BufferedImage getBackground(int index) {
		return backgrounds.get(index);
	}
	
	public BufferedImage getActor(int index) {
		if (index == 0) return null;
		return actors.get(index);
	}
	
	public BufferedImage getExplorationObject(int index) {
		return explorationObjects.get(index);
	}
	
	public ModelSwitch getSwitch(int index) {
		return switches.get(index);
	}
	
	public ModelCounter getCounter(int index) {
		return counters.get(index);
	}

}
