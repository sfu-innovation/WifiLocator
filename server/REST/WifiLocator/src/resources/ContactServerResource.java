package resources;

import org.restlet.resource.ServerResource;

import datamodel.Contact;

public class ContactServerResource extends ServerResource implements
		ContactResource {
	private static volatile Contact contact = new Contact("jordan", "fox", 23);
	
	@Override
	public Contact retrieve() {
		return contact;
	}

	@Override
	public void store(Contact contact) {
		this.contact = contact;
	}
}
