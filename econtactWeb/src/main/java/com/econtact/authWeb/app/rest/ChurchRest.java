package com.econtact.authWeb.app.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.google.gson.Gson;
import com.share.dto.ChurchDto;



@Path("/church")
@RequestScoped
public class ChurchRest {

	@Inject
	private GenericService genericService;
	
	
	@GET
	@Path(value="/all")
	public Response getChurchs() {
		List<ChurchEntity> res = genericService.find(new SearchCriteria<ChurchEntity>(new GenericFilterDefQueries(ChurchEntity.class)));
		List<ChurchDto> resDto = new ArrayList<>();
		res.forEach(item ->	resDto.add(new ChurchDto(item.getId(), item.getNameChurch(), item.getOwner().getLogin())));
		return Response.ok(new Gson().toJson(resDto)).build();
	}
	
	@GET
	@Path("/verify")
	public String verifyRESTService(InputStream incomingData) {
		String result = "CrunchifyRESTService Successfully started..";
 
		// return HTTP response 200 in case of success
		return result;
	}
}
