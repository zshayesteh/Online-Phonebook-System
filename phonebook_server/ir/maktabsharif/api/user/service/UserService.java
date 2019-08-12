package ir.maktabsharif.api.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ir.maktabsharif.api.user.dto.UserFullDTO;
import ir.maktabsharif.api.user.dto.UserLoginDTO;
import ir.maktabsharif.model.entity.User;
import ir.maktabsharif.model.manager.UserManager;

@Path("user")
public class UserService{

	private static final Logger SPECIAL_LOG = LogManager.getLogger("SPECIAL_LOGGER");   

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser","guest"})
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(UserLoginDTO e) {
		try{
			if (UserManager.getInstance().add(e.convertToObject())) {
				SPECIAL_LOG.info("A user signed up with username: "+e.getUserName());
				return Response.status(Status.OK).entity("Successfully signed up!").build();
			} 
			SPECIAL_LOG.info("a user couldn't sign up with username: "+e.getUserName());
			return Response.status(Status.NOT_ACCEPTABLE).entity("The username exist,try something else...").build();//repetitive user information
		}catch(Exception x){
			SPECIAL_LOG.info("a user couldn't sign up with username: "+e.getUserName());
			return Response.status(Status.NOT_ACCEPTABLE).entity(x.getMessage()).build();//null values
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser","guest"})
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signIn(UserLoginDTO e, @Context HttpServletRequest request){
		try{
			if (UserManager.getInstance().signIn(e.convertToObject()) != null) {
				SPECIAL_LOG.info("A user signed in with username: "+e.getUserName());
				HttpSession session = request.getSession();
				//session.setAttribute("role","guest");
				//request.getSession().setAttribute("role","simpleUser");

				return Response.ok(UserManager.getInstance().signIn(e.convertToObject()).getRole()).build();
			} else {
				SPECIAL_LOG.info("a user couldn't sign in with username: "+e.getUserName());
				return Response.status(Status.UNAUTHORIZED).entity("Authentication failed due to wrong username or password!").build();
			}
		}catch(Exception x){
			SPECIAL_LOG.info("a user couldn't sign in with username: "+e.getUserName());
			return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(x.getMessage()).build();//null values
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin","simpleUser","guest"})
	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUserName(@PathParam("userName") String userName) {
		UserFullDTO udto=new UserFullDTO();
		User user = UserManager.getInstance().getByUserName(userName);
		udto.convertToDto(user);
		if (udto.getUserName()!=null && udto.getRole()!=null)
			return Response.ok(udto).build();
		else 
			return Response.status(Status.NOT_FOUND).entity("Username not found!").build();
	}

	@RolesAllowed("mainAdmin")
	@DELETE
	public Response removeUser(User e) {//user with username & password
		try{
			if(UserManager.getInstance().delete(e)){
				return Response.status(Status.OK).entity("User removed successfully").build();
			} 
			return Response.status(Status.BAD_REQUEST).entity("User not found!").build();
		}
		catch(Exception x){
			return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(x.getMessage()).build();//null values
		}
	}

	@RolesAllowed("mainAdmin")
	@DELETE
	@Path("/{userName}")
	public Response remove(@PathParam("userName") String userName) {
		return removeUser(UserManager.getInstance().getByUserName(userName));
	}

	@RolesAllowed("mainAdmin")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(UserFullDTO e) {
		try{
			if(UserManager.getInstance().update(e.convertToObject())){
				return Response.status(Status.OK).entity("User updated successfully").build();
			}
			return Response.status(Status.BAD_REQUEST).entity("User not found!").build();
		}catch(Exception x){
			return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(x.getMessage()).build();//null values
		}
	}

	@RolesAllowed({"mainAdmin","simpleAdmin"})
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserFullDTO> getAll() {		
		List<User> u= new ArrayList<User>();
		u=UserManager.getInstance().list();
		List<UserFullDTO> dtos=new ArrayList<>();
		for(int i=0;i<u.size();i++){
			UserFullDTO ufdto=new UserFullDTO();
			dtos.add(ufdto.convertToDto(u.get(i)));
		}
		return dtos;
	}

}
