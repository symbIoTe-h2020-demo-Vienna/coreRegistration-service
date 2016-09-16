package eu.h2020.symbiote.repository;

import java.math.BigInteger;
/**
 * Created by Mael on 08/09/2016.
 */
public class Mapping {

    private BigInteger id;
    private BigInteger modelId1;
    private BigInteger modelId2;
    private String mapping;

    public Mapping() {
    }

    public Mapping(String mapping, BigInteger modelId1, BigInteger modelId2) {
        this.mapping = mapping;
        this.modelId1 = modelId1;
        this.modelId2 = modelId2;
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public BigInteger getModelId1() {
        return modelId1;
    }

    public void setModelId1(BigInteger modelId1) {
        this.modelId1 = modelId1;
    }

    public BigInteger getModelId2() {
        return modelId2;
    }

    public void setModelId2(BigInteger modelId2) {
        this.modelId2 = modelId2;
    }
}

