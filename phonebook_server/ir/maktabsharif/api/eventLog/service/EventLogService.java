package ir.maktabsharif.api.eventLog.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ir.maktabsharif.api.eventLog.dto.EventLogFullDTO;
import ir.maktabsharif.model.entity.EventLog;
import ir.maktabsharif.model.manager.EventLogManager;

@Path("/eventLog")
public class EventLogService {

	@RolesAllowed({"mainAdmin","simpleAdmin"})
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventLogFullDTO> getAll() {
		List<EventLog> e= new ArrayList<EventLog>();
		e=EventLogManager.getInstance().list();
		List<EventLogFullDTO> dtos=new ArrayList<>();
		for(int i=0;i<e.size();i++){
			EventLogFullDTO efdto=new EventLogFullDTO();
			dtos.add(efdto.convertToDto(e.get(i)));
		}
		return dtos;
	}
}