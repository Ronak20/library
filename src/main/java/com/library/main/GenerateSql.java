package com.library.main;

import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.library.model.User;

public class GenerateSql {

	public static void main(String ... args)
	{
	
	Configuration config = new Configuration();

	Properties properties = new Properties();

	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/library"); 
	properties.put("hibernate.connection.username", "root");
	properties.put("hibernate.connection.password", "");
	properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	properties.put("hibernate.show_sql", "true");
	config.setProperties(properties);

	config.addAnnotatedClass(User.class);
	
	SchemaExport schemaExport = new SchemaExport(config);
	schemaExport.setDelimiter(";");

	schemaExport.create(true, false);
	}
}
