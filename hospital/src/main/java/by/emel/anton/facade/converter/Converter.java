package by.emel.anton.facade.converter;

public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE from);

}
