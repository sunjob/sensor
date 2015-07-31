package com.jlj.util;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Operation;
import com.jlj.model.Project;
import com.jlj.service.IOperationService;
import com.jlj.service.IProjectService;


@Component("logInterceptor")
@Scope("prototype")
public class LogInterceptor {
	
	private IOperationService operationService;
	private IProjectService projectService;
	private Operation operation;
	private Project project;
	
	
	
	
	public void addLog(String optype,String content,int projectid)
	{
		operation = new Operation();
		operation.setOptime(new Date());
		operation.setOptype(optype);
		operation.setContent(content);
		project = projectService.getById(projectid);
		operation.setProject(project);
		try {
			operationService.add(operation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public IOperationService getOperationService() {
		return operationService;
	}
	@Resource
	public void setOperationService(IOperationService operationService) {
		this.operationService = operationService;
	}
	
	

	public IProjectService getProjectService() {
		return projectService;
	}

	@Resource
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}


	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	
}