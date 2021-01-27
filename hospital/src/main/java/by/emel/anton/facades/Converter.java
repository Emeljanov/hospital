package by.emel.anton.facades;

public interface Converter<SOURCE, TARGET> {

TARGET convert (SOURCE from);

}
