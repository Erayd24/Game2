package axohEngine2.project;

public class State {

	private String currentState;
	
	public State(String startState) {
		currentState = startState;
	}
	
	public void changeState(String state) {
		currentState = state;
	}
	
	public String getState() {
		return currentState;
	}
}
