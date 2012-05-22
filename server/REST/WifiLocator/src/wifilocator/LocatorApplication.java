package wifilocator;

import org.restlet.*;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import datamodel.Contact;

import resources.ContactResource;
import resources.ContactServerResource;
import resources.LocationResource;

public class LocatorApplication extends ServerResource {
    public static void main(String[] args) throws Exception {   
       final Component component = new Component();
       component.getServers().add(Protocol.HTTP, 8182);
       final Router router = new Router(component.getContext().createChildContext());
       router.attach("/contact", ContactServerResource.class);
       router.attach("/location/{bssid}", LocationResource.class);
       component.getDefaultHost().attach("/wifilocator", router);
       
       final Thread t = new Thread() {
    	   @Override
    	   public void run() {
    		   try {
				component.start();
			} catch (final Exception e) {
				e.printStackTrace();
			}
    	   }
       };
       t.run();
       
       final ClientResource cr = new ClientResource(
    		   "http://localhost:8182/wifilocator/contact");
       ContactResource resource = cr.wrap(ContactResource.class);
       Contact contact = resource.retrieve();
       System.out.println(contact.firstname + " " + contact.lastname + ", age: " + contact.age);
       
       contact.firstname = "justin";
       contact.age = 13;
       
       resource.store(contact);
       
       resource = cr.wrap(ContactResource.class);
       Contact contact2 = resource.retrieve();
       System.out.println(contact2.firstname + " " + contact2.lastname + ", age: " + contact2.age);
    }
}
