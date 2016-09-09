package eu.h2020.symbiote.messaging.model;

import eu.h2020.symbiote.core.RDFFormat;

/**
 * Created by Mael on 08/09/2016.
 */
public class OntologyModel {


    private Long id;
    private String body;
    private RDFFormat format;

    OntologyModel() {

    }

    public OntologyModel(Long id, String body, RDFFormat format) {
        this.id = id;
        this.body = body;
        this.format = format;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public RDFFormat getFormat() {
        return format;
    }

    public void setFormat(RDFFormat format) {
        this.format = format;
    }

}
