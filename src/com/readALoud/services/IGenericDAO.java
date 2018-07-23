package com.readALoud.services;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO <T, ID extends Serializable> 
{
	List findAll();

	
}
