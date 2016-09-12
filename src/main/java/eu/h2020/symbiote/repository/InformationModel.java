package eu.h2020.symbiote.repository;

import eu.h2020.symbiote.core.RDFFormat;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;

/**
 * Created by Mael on 09/09/2016.
 */
public class InformationModel {


    @Id
    private BigInteger id;
    private String instance;
    private RDFFormat format;

    InformationModel() {

    }

    public InformationModel(String instance, RDFFormat format) {
        this.instance = instance;
        this.format = format;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public RDFFormat getFormat() {
        return format;
    }

    public void setFormat(RDFFormat format) {
        this.format = format;
    }
}
