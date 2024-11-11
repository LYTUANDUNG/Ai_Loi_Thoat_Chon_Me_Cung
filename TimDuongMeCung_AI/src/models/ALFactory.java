package models;

public class ALFactory implements IFactory<IAlogrithm> {

	@Override
	public IAlogrithm createProduct(String name) {
		IAlogrithm al;
		switch (name) {
		case "PSO": {
			al = new PSO();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + name);
		}
		return al;
	}

}
