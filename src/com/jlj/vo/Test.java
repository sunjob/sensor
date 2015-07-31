package com.jlj.vo;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class Test {

	/**
	 * @param args
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		String text = "<?xml version='1.0' encoding='utf-8'?><CSubmitState xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns='http://tempuri.org/'>"
					  +"<State>0</State>"
					  +"<MsgID>1507061414307370501</MsgID>"
					  +"<MsgState>提交成功</MsgState>"
					  +"<Reserve>0</Reserve>"
					  +"</CSubmitState>";
		Document document = DocumentHelper.parseText(text);
		Element rootEle=document.getRootElement();
//		listNodes(rootEle);
		      Element StateEle = rootEle.element("State");
		      Element MsgIDEle = rootEle.element("MsgID");
		      Element MsgStateEle = rootEle.element("MsgState");
		      Element ReserveEle = rootEle.element("Reserve");
		      System.out.println(StateEle.getText()+"-"+MsgIDEle.getText()+"-"+MsgStateEle.getText()+"-"+ReserveEle.getText());
		
	}
	
	public String parseSmsResultXml(String xmltext){
		Document document;
		try {
			document = DocumentHelper.parseText(xmltext);
		
			Element rootEle=document.getRootElement();
			//listNodes(rootEle);
		    Element StateEle = rootEle.element("State");
		    Element MsgIDEle = rootEle.element("MsgID");
		    Element MsgStateEle = rootEle.element("MsgState");
		    Element ReserveEle = rootEle.element("Reserve");
		    return StateEle.getText()+"-"+MsgIDEle.getText()+"-"+MsgStateEle.getText()+"-"+ReserveEle.getText();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "exception";
		}
		    
		 
	}

}
