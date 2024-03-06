package command;

import shapes.Line;

public class CmdLineUpdate implements Command {

	private Line oldState;
	private Line newState;
	private Line original = new Line();

	public CmdLineUpdate(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		/*original.getStartPoint().setX(oldState.getStartPoint().getX());
		original.getStartPoint().setY(oldState.getStartPoint().getY());
		original.getStartPoint().setColor(oldState.getStartPoint().getColor());
		
		oldState.getStartPoint().setX(newState.getStartPoint().getX());
		oldState.getStartPoint().setY(newState.getStartPoint().getY());
		oldState.getStartPoint().setColor(newState.getStartPoint().getColor());*/
		
		original = oldState.clone(original);
		oldState = newState.clone(oldState);
	}

	@Override
	public void unexecute() {
		/*oldState.getStartPoint().setX(original.getStartPoint().getX());
		oldState.getStartPoint().setY(original.getStartPoint().getY());
		oldState.getStartPoint().setColor(original.getStartPoint().getColor());*/
		
		oldState = original.clone(oldState);
	}

	@Override
	public String log() {
		return "Edited: " + newState.toString();
	}

}
