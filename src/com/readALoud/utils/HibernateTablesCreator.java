package com.readALoud.utils;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateTablesCreator 
{
	@SuppressWarnings("rawtypes")
	public final static void createTables(Class className){

		Configuration config=new Configuration();
		config.addAnnotatedClass(className.getClass());
		config.configure();		
		new SchemaExport(config).create(true,true);
	}
}
