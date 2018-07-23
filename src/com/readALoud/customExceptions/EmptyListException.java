package com.readALoud.customExceptions;

import java.util.List;

public class EmptyListException extends Exception{

	//List list;
	String msg;
	public EmptyListException(String str){
		this.msg=str;
	}
	@Override
	public String toString(){
		return "Sorry::"+msg;
	}
}
