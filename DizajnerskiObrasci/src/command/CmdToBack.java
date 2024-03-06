package command;

import mvc.DrawingModel;
import shapes.Shape;

public class CmdToBack implements Command {

	private DrawingModel model;

	public CmdToBack(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) { // trazi selektovani
				if (j == 0) { // ako je vec na najnizoj poziciji...
					return;
				} else { // ako nije..
					Shape s = model.getShapes().get(j - 1); // shape iza njega
					model.getShapes().set(j - 1, model.getShapes().get(j)); // postavlja moj selektovani shape na
																			// poziciju iza
					model.getShapes().set(j, s); // a prethodni shape sada postavlja ispred
					return;
				}
			}
		}
	}

	@Override // unexecute je zapravo CmdToFront
	public void unexecute() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) { // trazi selektovani
				if (j == model.getShapes().size() - 1) { // ako je vec na najvisoj poziciji...
					return;
				} else { // ako nije..
					Shape s = model.getShapes().get(j + 1); // shape ispred njega
					model.getShapes().set(j + 1, model.getShapes().get(j)); // postavlja moj selektovani shape na
																			// poziciju ispred
					model.getShapes().set(j, s); // a na poziciju iza njega postavlja onaj shape koji je bio ispred
													// njega
					return;
				}
			}
		}
	}

	@Override
	public String log() {
		for (int j = 0; j <= model.getShapes().size() - 1; j++) { // prolazi kroz sve shapes
			if (model.getShapes().get(j).isSelected()) {
				return "Backward for one position: " + model.getShapes().get(j).toString();
			}
		}
		return null;
	}

}
