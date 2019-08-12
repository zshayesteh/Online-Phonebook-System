package ir.maktabsharif.api.filter;

//import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import ir.maktabsharif.api.filter.AuthenticationFilter;
//import com.howtodoinjava.jersey.provider.GsonMessageBodyHandler;

public class CustomAppResourceConfig extends ResourceConfig
{
	public CustomAppResourceConfig()
	{
		packages("ir.maktabsharif");
		register(AuthenticationFilter.class);
		register(CORSresponseFilter.class);
	}
}