package eu.h2020.symbiote.messaging.model;

import eu.h2020.symbiote.core.RDFFormat;

/**
 * Object representing created platform
 *
 * Created by Mael on 08/09/2016.
 */
public class CreatedPlatform {

    private Long id;
    private String instance;
    private RDFFormat format;
    private Long modelId;

    CreatedPlatform() {

    }

    public CreatedPlatform(Long id, String instance, RDFFormat format, Long modelId) {
        this.id = id;
        this.instance = instance;
        this.format = format;
        this.modelId = modelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

}
