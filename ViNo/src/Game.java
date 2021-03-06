import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game {
	
	final private String version = "Vino v0.6";
	private ViewPlay viewPlay;
	private ModelStory story;
	private ModelScene currentScene;
	private ModelSceneElement currentElement;

	
	public Game() throws IOException {
		
		// main
		story = new ViNoDebug().getDebugStory();
		viewPlay = new ViewPlay(story.getTitle() + " - " + version);
		
		// event listeners
		viewPlay.getNextButton().addActionListener(new nextButtonListener());
		viewPlay.getDecisionButton(0).addActionListener(new decisionButtonListener());
		viewPlay.getDecisionButton(1).addActionListener(new decisionButtonListener());
		viewPlay.getDecisionButton(2).addActionListener(new decisionButtonListener());
		viewPlay.getDecisionButton(3).addActionListener(new decisionButtonListener());
		viewPlay.getDecisionButton(4).addActionListener(new decisionButtonListener());
		viewPlay.getDecisionButton(5).addActionListener(new decisionButtonListener());
		viewPlay.getExplorationButton(0).addActionListener(new explorationButtonListener());
		viewPlay.getExplorationButton(1).addActionListener(new explorationButtonListener());
		viewPlay.getExplorationButton(2).addActionListener(new explorationButtonListener());
		viewPlay.getExplorationButton(3).addActionListener(new explorationButtonListener());
		viewPlay.getExplorationButton(4).addActionListener(new explorationButtonListener());
		viewPlay.getExplorationButton(5).addActionListener(new explorationButtonListener());
		
		// start game
		runScene(story.getStartPoint());

	}
	
	private class nextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (currentElement instanceof ModelBeat) {
				runElement(currentScene.getElement(currentElement.getNext()));
			}
		}
	}
	
	// action listener used for all six decision buttons
	private class decisionButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			runElement(currentScene.getElement(((ModelDecision) currentElement).getNextForDecision(((ModelDecisionButton) arg0.getSource()).getDecisionId())));
		}
	}
	
	// action listener used for all six exploration objects
		private class explorationButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				runElement(currentScene.getElement(((ModelExploration) currentElement).getObjectNext(((ModelExplorationObjectButton) arg0.getSource()).getObjectId())));
			}
		}
	
	// loads a scene to the UI
	private void runScene(ModelScene scene) {
		currentScene = scene;
		viewPlay.setBackground(story.getBackground(scene.getBackground()));
		runElement(scene.getStartElement());
	}
	
	// passes the current element to the appropriate handler
	private void runElement(ModelSceneElement e) {
		if (e instanceof ModelBeat) {
			runBeat((ModelBeat) e);
		}
		else if (e instanceof ModelDecision) {
			runDecision((ModelDecision) e);
		}
		else if (e instanceof ModelExploration) {
			runExploration((ModelExploration) e);
		}
		else if (e instanceof ModelLogic) {
			runLogic((ModelLogic) e);
		}
		else if (e instanceof ModelToggle) {
			runToggle((ModelToggle) e);
		}
		else {
			runScene(story.getScene(e.getNext()));
		}
	}
	
	// loads a beat to the UI
	private void runBeat(ModelBeat beat) {
		
		currentElement = beat;
		
		// update any actor changes
		if (beat.getSpeaker() != null) {
			viewPlay.setSpeakerName(beat.getSpeaker());
		}
		if (beat.getLeftActor() != -1) {
			viewPlay.setLeftActor(story.getActor(beat.getLeftActor()));
		}
		if (beat.getRightActor() != -1) {
			viewPlay.setRightActor(story.getActor(beat.getRightActor()));
		}
		if (beat.getMidActor() != -1) {
			viewPlay.setMidActor(story.getActor(beat.getMidActor()));
		}
		
		// display dialog text
		viewPlay.setDialog(beat.getDialog());
		viewPlay.showDialog();
	}
	
	// loads a decision element to the UI
	private void runDecision(ModelDecision decision) {
		
		currentElement = decision;
		viewPlay.clearDecisions();
		
		for (int i = 0; i < decision.getDecisionCount(); ++i) {
			viewPlay.setDecision(i, ((ModelDecision) currentElement).getDecision(i));
		}
		
		viewPlay.showDecisions();
	}
	
	// loads an exploration to the UI
	private void runExploration(ModelExploration e) {
		
		currentElement = e;
		viewPlay.clearExploration();
		
		for (int i = 0; i < e.getObjectCount(); ++i) {
			viewPlay.setExplorationIcon(i, e.getObjectX(i), e.getObjectY(i), story.getExplorationObject(e.getObjectImage(i)));
		}
		
		viewPlay.showExploration();
	}
	
	// performs a logical comparison with a switch or counter
	private void runLogic(ModelLogic log) {
		
		currentElement = log;
		// next element will depend on the comparison
		// if type is -1, it is a bool comparison
		if (log.getType() == -1) {
			runElement(currentScene.getElement(log.compare(story.getSwitch(log.getIndex()).getValue())));
		}
		// otherwise it is a counter comparison
		else {
			runElement(currentScene.getElement(log.compare(story.getCounter(log.getIndex()).getValue())));
		}
	}
	
	// changes the value on one switch or counter
	private void runToggle(ModelToggle t) {
		
		currentElement = t;
		int type = t.getType();
		
		// new bool value
		if (type == 0) {
			story.getSwitch(t.getIndex()).setValue(t.getNewBool());
		}
		// new integer value
		else if (type == 1) {
			story.getCounter(t.getIndex()).setValue(t.getNewValue());
		}
		// increment
		else if (type == 2) {
			story.getCounter(t.getIndex()).setValue(story.getCounter(t.getIndex()).getValue() + 1);
		}
		// decrement
		else {
			story.getCounter(t.getIndex()).setValue(story.getCounter(t.getIndex()).getValue() - 1);
		}
		
		runElement(currentScene.getElement(t.getNext()));
	}

}
