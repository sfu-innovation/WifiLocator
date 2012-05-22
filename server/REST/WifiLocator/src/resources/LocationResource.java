package resources;

import java.io.BufferedReader;
import java.io.FileReader;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class LocationResource extends ServerResource {
	@Get()
	public String toString() {
		String zone = "unknown";
		FileReader fr;
		try {
			fr = new FileReader(getClass().getResource("../data/res.csv").getPath());
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens.length > 1 && tokens[0].equals((String)getRequestAttributes().get("bssid"))) {
					zone = line.split(",")[1];
					break;
				}
			}
			
			fr.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Zone: " + zone;
	}
}
