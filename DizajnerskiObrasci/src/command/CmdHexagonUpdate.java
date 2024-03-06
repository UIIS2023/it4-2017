package command;

import adapter.HexagonAdapter;
//import shapes.Point;
import shapes.Point;

public class CmdHexagonUpdate implements Command {

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter();

	public CmdHexagonUpdate(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {

		HexagonAdapter hexaHelp = new HexagonAdapter(new Point(oldState.getX(), oldState.getY()), oldState.getR(),
				oldState.getAreaColor(), oldState.getBorderColor());
		original.setHexagon(hexaHelp.getCenter(), hexaHelp.getR(), hexaHelp.getAreaColor(), hexaHelp.getBorderColor());
		
		HexagonAdapter hexaHelp1 = new HexagonAdapter(new Point(newState.getX(), newState.getY()), newState.getR(),
				newState.getAreaColor(), newState.getBorderColor());
		oldState.setHexagon(hexaHelp1.getCenter(), hexaHelp1.getR(), hexaHelp1.getAreaColor(),
				hexaHelp1.getBorderColor());
		
//		 original.getHexagon().setX(oldState.getHexagon().getX()); 
//		 original.getHexagon().setY(oldState.getHexagon().getY());
//		 original.getHexagon().setR(oldState.getHexagon().getR());
//		 original.getHexagon().setBorderColor(oldState.getHexagon().getBorderColor());
//		 original.getHexagon().setAreaColor(oldState.getHexagon().getAreaColor());
//		 
//		 oldState.getHexagon().setX(newState.getHexagon().getX()); 
//		 oldState.getHexagon().setY(newState.getHexagon().getY());
//		 oldState.getHexagon().setR(newState.getHexagon().getR());
//		 oldState.getHexagon().setBorderColor(newState.getHexagon().getBorderColor());
//		 oldState.getHexagon().setAreaColor(newState.getHexagon().getAreaColor());
		 

		
//		 original = oldState.clone(original); 
//		 oldState = newState.clone(oldState);
		 

	}

	@Override
	public void unexecute() {

		HexagonAdapter hexaHelp2 = new HexagonAdapter(new Point(original.getX(), original.getY()), original.getR(),
				original.getAreaColor(), original.getBorderColor());
		oldState.setHexagon(hexaHelp2.getCenter(), hexaHelp2.getR(), hexaHelp2.getAreaColor(),
				hexaHelp2.getBorderColor());

		/*
		 * oldState.getCenter().setX(original.getCenter().getX());
		 * oldState.getCenter().setY(original.getCenter().getY());
		 * oldState.setR(original.getR());
		 * oldState.setBorderColor(original.getBorderColor());
		 * oldState.setAreaColor(original.getAreaColor());
		 */

	}

	@Override
	public String log() {
		return "Edited: " + newState.toString();
	}

}
