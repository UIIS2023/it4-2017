package command;

import java.util.Deque;
import java.util.LinkedList;

public class CmdUndoRedo implements Command {

	// Deque - interfejs, prosirenje Queue interfejsa
	// Podrzava FIFO princip
	// Dodavanje i brisanje podrzano na oba kraja
	// Implementacija pomocu Linked Liste
	// Tip elemenata u deque je Command!
	private Deque<Command> undoDeque = new LinkedList<>();
	private Deque<Command> redoDeque = new LinkedList<>();

	public Deque<Command> getUndoDeque() {
		return undoDeque;
	}

	public void setUndoDeque(Deque<Command> undoDeque) {
		this.undoDeque = undoDeque;
	}

	public Deque<Command> getRedoDeque() {
		return redoDeque;
	}

	public void setRedoDeque(Deque<Command> redoDeque) {
		this.redoDeque = redoDeque;
	}
	
	// REDO
	@Override
	public void execute() {
		if (getRedoDeque().isEmpty() == false) {
			Command previousCommand = getRedoDeque().pollLast();
			getUndoDeque().offerLast(previousCommand);
			previousCommand.execute();
		}
	}

	// UNDO
	@Override
	public void unexecute() {
		if (getUndoDeque().isEmpty() == false) {
			Command previousCommand = getUndoDeque().pollLast();
			getRedoDeque().offerLast(previousCommand);
			previousCommand.unexecute();
		}
	}

	@Override
	public String log() {
		// TODO Auto-generated method stub
		return null;
	}
}
