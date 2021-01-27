package by.emel.anton.facade;

public interface Converter<SOURCE, TARGET> {

TARGET convert (SOURCE from);

}
