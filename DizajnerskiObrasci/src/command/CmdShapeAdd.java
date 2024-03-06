package command;

import mvc.DrawingModel;
import shapes.Shape;

public class CmdShapeAdd implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public CmdShapeAdd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}

	@Override
	public String log() {
		return "Added: " + shape.toString();
	}

}
