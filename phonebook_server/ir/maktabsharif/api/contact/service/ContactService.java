package ir.maktabsharif.api.contact.service;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ir.maktabsharif.api.contact.dto.ContactFullDTO;
import ir.maktabsharif.api.contact.dto.ContactLiteDTO;
import ir.maktabsharif.api.user.service.UserService;
import ir.maktabsharif.model.entity.Contact;
import ir.maktabsharif.model.manager.ContactManager;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("contact")
public class ContactService{
	static Logger log = LogManager.getLogger(UserService.class);
	private static final Logger SPECIAL_LOG = LogManager.getLogger("SPECIAL_LOGGER");   

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser"})
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(ContactFullDTO e) {
		Contact c=e.convertToObject();
		try{
			if(ContactManager.getInstance().add(c)){//if inserting was successful and if condition was true
				SPECIAL_LOG.info("A contact added with name: "+c.getName());
				return Response.status(Status.OK).entity("Contact added successfully").build();//204:The server has fulfilled the request but does not need to return an entity-body, and might want to return updated metainformation.
			}
			SPECIAL_LOG.info("A contact couldn't add with name: "+c.getName());
			return Response.status(Status.NOT_ACCEPTABLE).entity("Input values are not acceptable!").build();//if inserting was not successful and if condition was false
		}
		catch(Exception x){ 
			SPECIAL_LOG.info("A contact couldn't add with name: "+c.getName());
			return Response.status(Status.NOT_ACCEPTABLE).entity(x.getMessage()).build();//406:The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request.
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin"})
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remove(ContactFullDTO e) {
		Contact c=e.convertToObject();
		try{
			if(ContactManager.getInstance().delete(c)){//if deleting was successful and if condition was true
				SPECIAL_LOG.info("A contact deleted with name: "+c.getName());
				return Response.status(Status.OK).entity("Contact deleted successfully").build();
			}
			SPECIAL_LOG.info("A contact couldn't delete with name: "+c.getName());
			return Response.status(Status.NOT_ACCEPTABLE).entity("Contact not found!").build();//if deleting was not successful and if condition was false
		}
		catch(Exception x){
			SPECIAL_LOG.info("A contact couldn't delete with name: "+c.getName());
			return Response.status(Status.NOT_ACCEPTABLE).entity(x.getMessage()).build();//if condition in if condition throw an exception
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin"})
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") Integer id) {
		try{
			if(ContactManager.getInstance().delete(id)){//if deleting was successful and if condition was true
				SPECIAL_LOG.info("A contact deleted with id: "+id);
				return Response.status(Status.OK).entity("Contact deleted successfully!").build();
			}
			SPECIAL_LOG.info("A contact couldn't delete with id: "+id);
			return Response.status(Status.NOT_ACCEPTABLE).entity("Contact not found!").build();//if deleting was not successful and if condition was false
		}
		catch(Exception x){
			SPECIAL_LOG.info("A contact couldn't delete with id: "+id);
			return Response.status(Status.NOT_ACCEPTABLE).entity(x.getMessage()).build();//if condition in if condition throw an exception
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin"})
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(ContactFullDTO e) {
		Contact c=e.convertToObject();
		try{
			if(ContactManager.getInstance().update(c)){//if updating was successful and if condition was true
				SPECIAL_LOG.info("A contact updated with name: "+c.getName());
				return Response.status(Status.OK).entity("Contact updated successfully!").build();
			}
			SPECIAL_LOG.info("A contact couldn't updated with name: "+c.getName());
			return Response.status(Status.NOT_ACCEPTABLE).entity("Contact not found!").build();//if updating was not successful and if condition was false
		}
		catch(Exception x){
			SPECIAL_LOG.info("A contact couldn't updated with name: "+c.getName());
			return Response.status(Status.NOT_ACCEPTABLE).entity(x.getMessage()).build();
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser","guest"})
	@GET
	@Path("/getAll/fullContact")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContactFullDTO> getAllFull() {
		List<Contact> c= new ArrayList<Contact>();
		c=ContactManager.getInstance().list();
		List<ContactFullDTO> dtos=new ArrayList<>();
		for(int i=0;i<c.size();i++){
			ContactFullDTO cfdto=new ContactFullDTO();
			dtos.add(cfdto.convertToDto(c.get(i)));
		}
		return dtos;
	}

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser","guest"})
	@GET
	@Path("/getAll/liteContact")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContactLiteDTO> getAllLite() {
		List<Contact> c= new ArrayList<Contact>();
		c=ContactManager.getInstance().list();
		List<ContactLiteDTO> dtos=new ArrayList<>();
		for(int i=0;i<c.size();i++){
			ContactLiteDTO cfdto=new ContactLiteDTO();
			dtos.add(cfdto.convertToDto(c.get(i)));
		}
		return dtos;
	}

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser","guest"})
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContactFullDTO get(@PathParam("id") Integer id) {
		Contact c= new Contact();
		c=ContactManager.getInstance().get(id);
		ContactFullDTO dto=new ContactFullDTO();
		dto.convertToDto(c);
		return dto;
	}

}
