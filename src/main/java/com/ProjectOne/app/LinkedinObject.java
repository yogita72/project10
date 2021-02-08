package com.ProjectOne.app;

import java.io.*;
import java.util.*;
import javax.json.*;
import org.neo4j.graphdb.*;
import com.ProjectOne.app.Neo4jWithCache;

public class LinkedinObject 
{
	public JsonObject	json;
	public LinkedinObject(JsonObject json) {

		this.json = json;
		JsonValue nameJson = json.get("formattedName");	
		if( nameJson != null) {
		String name = json.get("formattedName").toString();	
		System.out.printf("NodeName : %s \n", name);
		}
	}

	public Node commitToNeo4j(Node rootNode) {

		if(!json.containsKey("formattedName") ) {
			System.out.printf("No Name\n");
			return null;
		}

		JsonValue nameJson = json.get("formattedName");	
		if( nameJson != null) {
		String name = json.get("formattedName").toString();	
		System.out.printf("NodeName1 : %s \n", name);
		}
		Node personNode = Neo4jWithCache.lookupOrCreate( "persons", "name", json.get("formattedName").toString().replace("\"", "") ); 
		
		if( rootNode != null) {

			Neo4jWithCache.createRelationship(rootNode, personNode, "knows", null); 

		}
		if( json.containsKey("industry")) {
			Node professionNode = Neo4jWithCache.lookupOrCreate( "profession", "name", json.get("industry").toString().replace("\"", ""));

			Neo4jWithCache.createRelationship(personNode, professionNode, "practices", null); 
	


		}

		
		if( json.containsKey("location")) {

			JsonObject country= ((JsonObject) ((JsonObject)json.get("location")).get("country"));
			if( country.containsKey("code")) {
			String countryCode = country.get("code").toString().replace("\"", "");
			Node nationNode = Neo4jWithCache.lookupOrCreate("nation", "name", countryCode);;

			Neo4jWithCache.createRelationship(personNode, nationNode, "location", null); 

			}
		}
		
		if( json.containsKey("educations") && ((JsonObject)json.get("educations")).containsKey("values") ) {

			for( JsonValue education : ((JsonArray) ((JsonObject)json.get("educations")).get("values"))) {

			if( ((JsonObject)education).containsKey("schoolName")) {
			Node schoolNode = Neo4jWithCache.lookupOrCreate("school", "name", ((JsonObject)education).get("schoolName").toString().replace("\"", "") );
			
			Map<String, String> properties = new HashMap<String, String>();

			if( ((JsonObject)education).containsKey("startDate")) {	
				properties.put("startDate", ((JsonObject) ((JsonObject)education).get("startDate")).get("year").toString().replace("\"", ""));
			}
			if(((JsonObject) education).containsKey("endDate")) {	
				properties.put("endDate",((JsonObject) ((JsonObject)education).get("endDate")).get("year").toString().replace("\"", ""));
			}

			Neo4jWithCache.createRelationship(personNode, schoolNode, "studied", properties); 
			}	
			}

		}

		if( json.containsKey("positions") && ((JsonObject)json.get("positions")).containsKey("values") ) {

			for( JsonValue position : ((JsonArray) ((JsonObject)json.get("positions")).get("values"))) {

			if( ((JsonObject)position).containsKey("company") && ((JsonObject) ((JsonObject)position).get("company")).containsKey("name")) {
			Node companyNode = Neo4jWithCache.lookupOrCreate("company", "name",((JsonObject) ((JsonObject)position).get("company")).get("name").toString().replace("\"", "") );
			
			Map<String, String> properties = new HashMap<String, String>();

			if( ((JsonObject)position).containsKey("startDate")) {	
				properties.put("startDate", ((JsonObject)((JsonObject)position).get("startDate")).get("year").toString().replace("\"", ""));
			}
			if( ((JsonObject)position).containsKey("endDate")) {	
				properties.put("endDate", ((JsonObject)((JsonObject)position).get("endDate")).get("year").toString().replace("\"", ""));
			}

			Neo4jWithCache.createRelationship(personNode, companyNode, "worked", properties); 
			}	
			}



		}
		
		return personNode;
	}
}
