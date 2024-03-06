package command;

import mvc.DrawingModel;
import shapes.Shape;

public class CmdBringToFront implements Command {

	private DrawingModel model;

	public CmdBringToFront(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) {
			if (model.getShapes().get(j).isSelected()) {
				if (j == model.getShapes().size() - 1) {
					return;
				} else {
					Shape s = model.getShapes().get(j + 1);
					model.getShapes().set(j + 1, model.getShapes().get(j));
					model.getShapes().set(j, s);

					// ovde nemam return naredbu, jer ne zelim da mi ga pomeri samo za jednu
					// poziciju, nego do kraja
					// swapuje selektovani i onaj ispred njega sve dok selektovani ne bude na vrhu
				}
			}
		}
	}

	@Override // unexecute je zapravo CmdBringToBack
	public void unexecute() {
		for (int j = model.getShapes().size() - 1; j >= 0; j--) {
			if (model.getShapes().get(j).isSelected()) {
				if (j == 0)
					return;
			} else {
				Shape s = model.getShapes().get(j - 1);
				model.getShapes().set(j - 1, model.getShapes().get(j));
				model.getShapes().set(j, s);
			}
		}
	}

	@Override
	public String log() {
		for (int j = model.getShapes().size() - 1; j >= 0; j--) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) {
				return "Bring to the front: " + model.getShapes().get(j).toString();
			}
		}
		return null;
	}
}
