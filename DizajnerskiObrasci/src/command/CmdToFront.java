package command;

import mvc.DrawingModel;
import shapes.Shape;

public class CmdToFront implements Command {

	private DrawingModel model;

	public CmdToFront(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) { // trazi selektovani
				if (j == model.getShapes().size() - 1) { // ako je vec na najvisoj poziciji...
					return;
				} else { // ako nije..
					Shape s = model.getShapes().get(j + 1); // shape ispred njega
					model.getShapes().set(j + 1, model.getShapes().get(j)); // postavlja moj selektovani shape na
																			// poziciju ispred
					model.getShapes().set(j, s); // a na poziciju iza postavlja onaj koji je bio ispred njega
					// u sustini radi swap dva shape-a
					return;
				}
			}
		}
	}

	@Override // unexecute je zapravo CmdToBack
	public void unexecute() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) { // trazi selektovani
				if (j == 0) { // ako je vec na najnizoj poziciji...
					return;
				} else { // ako nije..
					Shape s = model.getShapes().get(j - 1); // shape iza njega
					model.getShapes().set(j - 1, model.getShapes().get(j)); // postavlja moj selektovani shape na
																			// poziciju iza
					model.getShapes().set(j, s); // a na poziciju ipsred njega postavlja onaj koji je bio iza
					return;
				}
			}
		}
	}

	@Override
	public String log() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) {
				return "Forward for one position: " + model.getShapes().get(j).toString();
			}
		}
		return null;
	}

}
