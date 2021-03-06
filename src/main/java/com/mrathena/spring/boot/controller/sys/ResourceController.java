package com.mrathena.spring.boot.controller.sys;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrathena.spring.boot.entity.SYSResource;
import com.mrathena.spring.boot.service.SYSResourceService;
import com.mrathena.toolkit.Json;

@Controller
@RequestMapping("resource")
public class ResourceController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SYSResourceService resourceService;
	
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.POST)
	public Object getAllResources() {
		Json json = null;
		try {
			List<SYSResource> resources = resourceService.getAllResources();
			json = Json.success().put("resources", resources);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "avaliable", method = RequestMethod.POST)
	public Object getAllAvaliableResources() {
		Json json = null;
		try {
			List<SYSResource> resources = resourceService.getAllAvaliableResources();
			json = Json.success().put("resources", resources);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Object insert(SYSResource resource) {
		Json json = null;
		try {
			boolean flag = resourceService.insertResource(resource);
			json = flag ? Json.success().put("resource", resource) : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object update(SYSResource resource) {
		Json json = null;
		try {
			boolean flag = resourceService.updateResourceByResourceId(resource);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "enable", method = RequestMethod.POST)
	public Object enable(@RequestParam("resourceIds[]") Long... resourceIds) {
		Json json = null;
		try {
			boolean flag = resourceService.enableResourcesByResourceIds(resourceIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "disable", method = RequestMethod.POST)
	public Object disable(@RequestParam("resourceIds[]") Long... resourceIds) {
		Json json = null;
		try {
			boolean flag = resourceService.disableResourcesByResourceIds(resourceIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Object delete(@RequestParam("resourceIds[]") Long... resourceIds) {
		Json json = null;
		try {
			boolean flag = resourceService.deleteResourcesByResourceIds(resourceIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
}