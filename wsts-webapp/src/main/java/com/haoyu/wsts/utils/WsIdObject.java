package com.haoyu.wsts.utils;

import java.io.Serializable;

import com.haoyu.sip.core.utils.ThreadContext;

public class WsIdObject implements Serializable {
	
	private static final long serialVersionUID = 9137061416587572842L;

	public static final String WSIDOBJECT_KEY = ThreadContext.class.getName() + "_WSID_OBJECT_KEY";
	
	private String wsid;
	
	public WsIdObject(){}
	
	public WsIdObject(String wsid) {
		super();
		this.wsid = wsid;
	}

	public String getWsid() {
		return wsid;
	}

	public void setWsid(String wsid) {
		this.wsid = wsid;
	}

	public static void bind(WsIdObject wsIdObject){
		if(wsIdObject!=null){
			ThreadContext.put(WSIDOBJECT_KEY,wsIdObject);
		}
	}
	
	public static WsIdObject getWsIdObject(){
		return (WsIdObject)ThreadContext.get(WSIDOBJECT_KEY);
	}
	
}
