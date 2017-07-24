package com.mrathena.spring.boot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrathena.spring.boot.dao.SYSResourceMapper;
import com.mrathena.spring.boot.entity.SYSResource;

@Service("SYSResourceService")
public class SYSResourceServiceImpl implements SYSResourceService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SYSResourceMapper resourceDao;
	
	@Override
	public List<SYSResource> getAllResources() {
		return resourceDao.selectAll();
	}

	@Override
	public List<SYSResource> getAllAvaliableResources() {
		return resourceDao.selectAvaliable();
	}

	@Override
	public boolean insertResource(SYSResource resource) {
		resource.withId(null).withType("navigation").withName("新资源节点").withAvailable(false);
		return resourceDao.insertSelective(resource) != 0;
	}

	@Override
	public boolean updateResourceByResourceId(SYSResource resource) {
		return resourceDao.updateByPrimaryKeySelective(resource) != 0;
	}

	@Override
	public boolean deleteResourcesByResourceIds(Long... resourceIds) {
		return resourceDao.deleteByIds(resourceIds) != 0;
	}

	@Override
	public boolean enableResourcesByResourceIds(Long... resourceIds) {
		return resourceDao.enableByIds(resourceIds) != 0;
	}

	@Override
	public boolean disableResourcesByResourceIds(Long... resourceIds) {
		return resourceDao.disableByIds(resourceIds) != 0;
	}

}