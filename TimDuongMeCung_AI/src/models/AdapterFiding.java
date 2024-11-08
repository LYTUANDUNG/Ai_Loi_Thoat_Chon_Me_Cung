package models;

public class AdapterFiding implements IAlogrithm{
	private IAlogrithm alogrithm;

    public Point[] execute() {
        return alogrithm.execute();
    }
}
