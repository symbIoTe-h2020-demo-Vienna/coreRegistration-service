package eu.h2020.symbiote.platform;

import eu.h2020.symbiote.core.RDFFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mael on 30/08/2016.
 */
@Entity
public class Platform {

	@Id
	@GeneratedValue
	private Long id;

	private String model;
	private String instance;
	private RDFFormat format;

	Platform() {

	}

	public Platform(String model, String instance, RDFFormat format) {
		this.model = model;
		this.instance = instance;
		this.format = format;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public RDFFormat getFormat() {
		return format;
	}

	public void setFormat(RDFFormat format) {
		this.format = format;
	}
}
