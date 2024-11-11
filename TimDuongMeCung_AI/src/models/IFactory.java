package models;

public interface IFactory<T> {
	T createProduct(String name);
}
