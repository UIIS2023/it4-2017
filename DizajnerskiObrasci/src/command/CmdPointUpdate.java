package command;

import shapes.Point;

public class CmdPointUpdate implements Command {

	private Point oldState;
	private Point newState;
	private Point original = new Point();
	
	public CmdPointUpdate(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		/*original.setX(oldState.getX());
		original.setY(oldState.getY());
		original.setColor(oldState.getColor());

		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setColor(newState.getColor());*/
		
		original = oldState.clone(original);
		oldState = newState.clone(oldState);
	}

	@Override
	public void unexecute() {
		/*oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setColor(original.getColor());*/
		
		oldState = original.clone(oldState);
	}

	@Override
	public String log() {
		return "Edited: " + newState.toString();
	}

}
