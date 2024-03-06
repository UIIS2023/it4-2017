package strategy;

public class LoadManager implements Load {

	private Load load;
	
	public LoadManager(Load load) {
		this.load = load;
	}

	@Override
	public Object loadData(String path) {
		return load.loadData(path);
	}
}
