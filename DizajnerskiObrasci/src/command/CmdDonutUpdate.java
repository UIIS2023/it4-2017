package command;

import shapes.Donut;

public class CmdDonutUpdate implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();

	public CmdDonutUpdate(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		/*original.setCenter(oldState.getCenter());
		original.setRadius(oldState.getRadius());
		try {
			original.setInnerRadius(oldState.getInnerRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		original.setInsideColor(oldState.getInsideColor());
		original.setOutsideColor(oldState.getOutsideColor());
		
		oldState.setCenter(newState.getCenter());
		oldState.setRadius(newState.getRadius());
		try {
			oldState.setInnerRadius(newState.getInnerRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldState.setInsideColor(newState.getInsideColor());
		oldState.setOutsideColor(newState.getOutsideColor());*/
		
		original = oldState.clone(original);
		oldState = newState.clone(oldState);

	}

	@Override
	public void unexecute() {
		/*oldState.setCenter(original.getCenter());
		oldState.setRadius(original.getRadius());
		try {
			oldState.setInnerRadius(original.getInnerRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldState.setInsideColor(original.getInsideColor());
		oldState.setOutsideColor(original.getOutsideColor());*/
		
		oldState = original.clone(oldState);
	}

	@Override
	public String log() {
		return "Edited: " + newState.toString();
	}

}
