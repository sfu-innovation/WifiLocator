package resources;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

import datamodel.Contact;

public interface ContactResource {
	@Get()
	public Contact retrieve();
	
	@Put()
	public void store(Contact contact);
}
