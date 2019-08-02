package edu.hsalbsig.swarch.justintime.configuration.producer;

import javax.enterprise.inject.Produces;

import org.modelmapper.ModelMapper;

public class ModelMapperProducer {
    @Produces
    public ModelMapper produceMapper() {
        return new ModelMapper();
    }
}
