package com.ProjectOne.app;

//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.exceptions.JedisConnectionException;
//import com.ProjectOne.app.GetJedis;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;
import org.neo4j.graphdb.schema.*;
import org.neo4j.index.lucene.*;
//import org.neo4j.index.*;
import org.neo4j.graphdb.index.*;
import org.neo4j.cypher.javacompat.*;
import org.neo4j.cypher.javacompat.ExecutionResult;
import javax.json.*;
/*
import org.neo4j.graphdb.index.UniqueFactory;
import org.neo4j.graphdb.index.UniqueFactory.*;
import org.neo4j.graphdb.index.UniqueFactory.UniqueNodeFactory;
import org.neo4j.graphdb.index.UniqueFactory.UniqueRelationshipFactory;
*/
public class Neo4jWithCache {

	private	static GraphDatabaseService graphDb;
	private static ExecutionEngine		cypherEngine;
//	private static Map<String, UniqueFactory<Node>> nodeFactories;
//	private static Map<String, UniqueFactory.UniqueNodeFactory> nodeFactories;
//	private static Map<String, UniqueFactory<Relationship>> relationFactories;

	private static enum RelTypes implements RelationshipType
	{
    		KNOWS
	}
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
    		// Registers a shutdown hook for the Neo4j instance so that it
    		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
    		// running application).
    		Runtime.getRuntime().addShutdownHook( new Thread()
    		{			
        		@Override
        		public void run()
        		{		
            			graphDb.shutdown();
        		}		
    		} );
	}

	public static void init() {
//		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "/root/neo4j-community-2.1.0-M01/data/graph.db" );

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder("/root/neo4j-community-2.1.0-M01/data/tmp.db")
		.loadPropertiesFromFile("/root/neo4j-community-2.1.0-M01/conf/neo4j.properties")
		.newGraphDatabase() ;

		registerShutdownHook( graphDb );


		//cypherEngine = new ExecutionEngine(graphDb);

		//nodeFactories = new HashMap<String, UniqueFactory.UniqueNodeFactory>();
//		nodeFactories = new HashMap<String, UniqueFactory<Node>>();
//		relationFactories = new HashMap<String, UniqueFactory<Relationship>>();

		}

		public static String runCypher(String query) {

			ExecutionResult	result = cypherEngine.execute(query);

			ResourceIterator<Map<String,Object>> iterator = result.iterator();
			//String		resultStr="{ links: [" ;
			String		resultStr ="";
			String		linkStr="" ;
			String		nodeStr="" ;
			JsonArrayBuilder	nodeArrayBuilder = Json.createArrayBuilder();
			JsonArrayBuilder	linksArrayBuilder = Json.createArrayBuilder();
			Map<String,Object> mapElem = null;		
//			Transaction tx = graphDb.beginTx(); 
/*{

			while( iterator.hasNext() ) {
			
				resultStr  = resultStr +  iterator.next().toString();
				resultStr = resultStr + ",";
			}
			resultStr = resultStr.substring(0, resultStr.length() -1) + "] }";
			iterator.close();
			tx.success();
		}
*/
/*
{

			while( iterator.hasNext() ) {
			
				mapElem = iterator.next();
				resultStr = resultStr + "{";
				for(String key : mapElem.keySet()) {
				String nodeElem =   mapElem.get(key).toString();
				String nodeNum =   nodeElem.substring(5, nodeElem.length() -1) ;
				resultStr  = resultStr +   key + ":" +  nodeNum ;
				resultStr = resultStr + ",";
				}
				resultStr = resultStr.substring(0, resultStr.length() -1) + "}";
				resultStr = resultStr + ",";
			}
			resultStr = resultStr.substring(0, resultStr.length() -1) + "] }";
			iterator.close();
			tx.success();
		}
*/
/*
{

			//nodeStr = nodeStr + "{name : 0 }, { name : 1 }, {name : 2}, {iname : 3},{name : 4}, {name:5},{name:6}, {name:7},{name:8},  {name:9},";
			nodeStr = nodeStr + " { id:1 , name : 1 }, {id:4, name : 4}, {id: 5, name:5}, {id:7, name:7}, {id:9, name:9},";
			while( iterator.hasNext() ) {
			
				mapElem = iterator.next();
				linkStr = linkStr + "{";
				for(String key : mapElem.keySet()) {
				String nodeElem =   mapElem.get(key).toString();
				String nodeNum =   nodeElem.substring(5, nodeElem.length() -1) ;
				
				linkStr  = linkStr +   key + ":" +  nodeNum ;
				linkStr = linkStr + ",";
				}
				linkStr = linkStr.substring(0, linkStr.length() -1) + "}";
				linkStr = linkStr + ",";
			}
			resultStr = "{" + nodeStr.substring(0,nodeStr.length() -1) + "]" + "," + linkStr.substring(0, linkStr.length() -1) + "] }";
			iterator.close();
			tx.success();
		}
*/
/*
{

			//nodeStr = nodeStr + "{name : 0 }, { name : 1 }, {name : 2}, {iname : 3},{name : 4}, {name:5},{name:6}, {name:7},{name:8},  {name:9},";
		//	nodeStr = nodeStr + " { id:1 , index : 1 }, {id:4, index : 4}, {id: 5, index:5}, {id:7, index:7}, {id:9, index:9},";
			while( iterator.hasNext() ) {
			
				mapElem = iterator.next();
				linkStr = linkStr + "{";
				for(String key : mapElem.keySet()) {
				String nodeElem =   mapElem.get(key).toString();
				String nodeNum =   nodeElem.substring(5, nodeElem.length() -1) ;
				
				if( start == true) {

					if( key.equals("source") ) {

						nodeStr = nodeStr + "{id:" + nodeNum + "} ,";
					}
					start = false;
				}
			
				if( key.equals("target") ) {

					nodeStr = nodeStr + "{id:" + nodeNum + "} ,";
				}
				linkStr  = linkStr +   key + ":"  +  nodeNum   ;
				//linkStr  = linkStr +   key + ":" + "{id:" +  nodeNum + "}"  ;
				linkStr = linkStr + ",";
				}
				linkStr = linkStr.substring(0, linkStr.length() -1) + "}";
				linkStr = linkStr + ",";
			}
			resultStr = "{" + nodeStr.substring(0,nodeStr.length() -1) + "]" + "," + linkStr.substring(0, linkStr.length() -1) + "] }";
			iterator.close();
			tx.success();
		}
*/
/*
{

			while( iterator.hasNext() ) {
			
				mapElem = iterator.next();
				resultStr = resultStr + "{ ";
				for(String key : mapElem.keySet()) {
				String nodeElem =   mapElem.get(key).toString();
				resultStr  = resultStr +  key + ":" + nodeElem ;  
				resultStr = resultStr + ",";
				}
			resultStr = resultStr.substring(0, resultStr.length() -1) + "},";
			}
			resultStr = resultStr.substring(0, resultStr.length() -1) + " }";
			iterator.close();
			tx.success();
		}
*/
/*
{

			List<String>	columnKeys = result.columns();
			
			for(String key : columnKeys) {
		//	String key = columnKeys.get(1);
			ResourceIterator<Object> columnIterator ;
			columnIterator = result.columnAs(key);
			resultStr = resultStr + "{ ";
			resultStr  = resultStr +  key + "  " ;  
			while( columnIterator.hasNext() ) {
			
				resultStr  = resultStr  +  ": " + columnIterator.next().toString() + ",";  
				}
			resultStr = resultStr.substring(0, resultStr.length() -1) + "},";
			//columnIterator.close();
			}
			resultStr = resultStr.substring(0, resultStr.length() -1) + " }";
			tx.success();
		}
*/
/*
{
			while( iterator.hasNext() ) {
			
				mapElem = iterator.next();
				resultStr = resultStr + "{ ";
				for(String key : mapElem.keySet()) {
				Object	nodeElem =   mapElem.get(key);
				if( nodeElem instanceof java.util.Collection) {

					resultStr = resultStr + "collection     ";
					Collection entityList = ((Collection)nodeElem);

			//	resultStr = resultStr + "{ " + entityList.getClass().getComponentType().getName();
				if( !entityList.isEmpty() )
					resultStr = resultStr + "elems : " + entityList.size();
				for(Object entity : entityList) {
				resultStr  = resultStr +  key + ":" + getInstanceType(entity) + ",";  
				}
				} else { 
					resultStr = resultStr + getInstanceType(nodeElem);
					resultStr = resultStr + nodeElem.toString();
				}
			resultStr = resultStr.substring(0, resultStr.length() -1) + "},";
				}
			resultStr = resultStr.substring(0, resultStr.length() -1) + "},";
			}
			resultStr = resultStr.substring(0, resultStr.length() -1) + " }";
			iterator.close();
			tx.success();
		}
*/

//		try {
			while( iterator.hasNext() ) {
			
				mapElem = iterator.next();
				for(String key : mapElem.keySet()) {
				Object	nodeElem =   mapElem.get(key);
				String instanceType = getInstanceType(nodeElem);

				if( instanceType.equals("collection")) {

					processCollection((Collection) nodeElem,
								nodeArrayBuilder,
								linksArrayBuilder);

				} else if( instanceType.equals("path")) {

					processPath( (org.neo4j.graphdb.Path)nodeElem,
							nodeArrayBuilder,
							linksArrayBuilder);

				} else if( instanceType.equals("relationship")){

					processRelationship( (Relationship) nodeElem,
								nodeArrayBuilder,
								linksArrayBuilder);

				} else if( instanceType.equals("node")) {

					processNode( (Node)nodeElem,
							nodeArrayBuilder);

				}
				
				}
			}
			iterator.close();

			JsonObjectBuilder	resultObjectBuilder = Json.createObjectBuilder();

			resultObjectBuilder.add("nodes", nodeArrayBuilder);
			resultObjectBuilder.add("links", linksArrayBuilder);
			JsonObject resultJson = resultObjectBuilder.build();	
			resultStr = AppJsonUtil.toString(resultJson);
/*
			tx.success();
		}finally {
			tx.finish();

		}
*/
//			 return result.dumpToString();
			 return resultStr;
		}

	public static Relationship createRelationship(Node node1, Node  node2, String type, Map<String, String> properties) {

		RelationshipType	relType = DynamicRelationshipType.withName(type);
		Relationship		rel;

		Transaction tx = graphDb.beginTx(); 

		try{
			rel = node1.createRelationshipTo( node2, relType);

			if( properties != null ) {
			for(String prop : properties.keySet() ) {
				rel.setProperty( prop, properties.get(prop));

			}
			}
			tx.success();
		} finally {
			tx.finish();

		}
		return rel;
	}

	public static void createIndex(String index, String key) {



		Transaction tx = graphDb.beginTx(); 
	try{

			boolean		found = false ;
			Schema schema = graphDb.schema();
			Label	label	= DynamicLabel.label( index);
			Iterable<IndexDefinition> indexList = schema.getIndexes(label);

			for( IndexDefinition idxDef : indexList)
				found = true ;

			if( found == false ) {
			IndexDefinition	indexDefinition;
			indexDefinition = schema.indexFor(label)
            				.on( key)
            					.create();	
			System.out.printf("creating index on %s \n", index);
			} else {

			System.out.printf("index exists %s \n", index);

			}
    			tx.success();

		} finally {
			tx.finish();
		}
	}

	public static Node createNodeWithLabel( String index, String key, String value) {
		Node 	node;
		Transaction tx = graphDb.beginTx(); 
	try{
			Label label = DynamicLabel.label( index);
			node = graphDb.createNode(label);
		
			node.setProperty(key, value);

			

    			tx.success();

		} finally {
			tx.finish();
		}


		
		return node;	

	}

	public static Node createNodeWithLabel( List<String> index, Map<String, String> properties) {
		Node 	node;
		Transaction tx = graphDb.beginTx(); 
	try{
			node = graphDb.createNode();
		
			for(String idx: index) {	
				Label label = DynamicLabel.label( idx);
				node.addLabel(label);
			}
			for( String prop : properties.keySet()) {
				node.setProperty(prop, properties.get(prop));

			}

    			tx.success();

		} finally {
			tx.finish();
		}


		
		return node;	

	}


	public static ArrayList<Node> lookupNode( String index, String key, String value) {

		Label label = DynamicLabel.label( index );
		Transaction tx = graphDb.beginTx();
		ArrayList<Node> nodes = new ArrayList<Node>();
		try {
			ResourceIterator<Node> keys =
            			graphDb.findNodesByLabelAndProperty( label, key, value ).iterator();
		

        			while ( keys.hasNext() )
        			{
            				nodes.add( keys.next() );
        			}


			tx.success();
		} finally {

			tx.finish();
		} 


		return nodes;

	}

	public static void  updatePropertyAndLabel(Node node, List<String>index, Map<String,String> properties){
		Transaction tx = graphDb.beginTx(); 
	try{
		
			for(String idx: index) {	
				Label label = DynamicLabel.label( idx);
				node.addLabel(label);
			}
			for( String prop : properties.keySet()) {
				node.setProperty(prop, properties.get(prop));

			}

    			tx.success();

		} finally {
			tx.finish();
		}


		
	}



	
	public static Node lookup( String index, String key, String value){

		List<Node>	lookupNodes ;	
		Node 		node;
		lookupNodes = lookupNode(index, key,value);

		
		if( lookupNodes.size() == 0 ) {
			node = null ;
		} else {
			node = lookupNodes.get(0);
		}
		return node;

	}

	public static Node lookupOrCreate(String index, String key, String value) {

		Node 		node;

		node = lookup(index,key, value) ;

		if( node == null) {
			//createIndex(index, key);
			node = createNodeWithLabel(index, key, value);
		}


		return node;
	}
	
/*
	public static Node lookupOrCreate(Jedis jedis, String index, String key, String value) {

		Node 		node;
		String		cacheKey;
		String		nodeId;
		int		ex;

		cacheKey = "lookup" + ":" +  index + ":" + key + ":" + value;	
		ex = 600;

		nodeId = jedis.get(cacheKey) ;
		
		// get the node by nodeId 
		if ( nodeId != null ) {

			jedis.expire(cacheKey, ex);
		
			Transaction tx = graphDb.beginTx(); 
			try{
				node = graphDb.getNodeById(Long.parseLong(nodeId));

    				tx.success();

			} finally {
				tx.finish();
			}
		} else {
		node = lookup(index,key, value) ;

		if( node == null) {
			//createIndex(index, key);
			node = createNodeWithLabel(index, key, value);
		}

		// save to redis

		try {
			jedis.setex(cacheKey, ex, (Long.toString( node.getId())));	
			//System.out.printf("added to redis\n");
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		}
		}

		return node;
	}
	public static void createUniqueFactory(String index) {

		if( nodeFactories.containsKey(index) == false ) {
		//UniqueFactory<Node>	factory = new UniqueFactory.UniqueNodeFactory(graphDb, index);

		Transaction tx = graphDb.beginTx(); 
		try{
		UniqueFactory<Node>	factory;
		factory  = new UniqueFactory.UniqueNodeFactory(graphDb, index)
		{
			@Override
			protected void initialize(Node created, Map<String, Object> properties) {
				for( String key : properties.keySet() ) {

		//			created.setProperty(key, properties.get(key));
				}	

			}
		};
		nodeFactories.put(index, factory);

    			tx.success();

		} finally {
			tx.finish();
		}
		}
		
	}
	public static Node getOrCreate(String index, String key, String value) {

		Node		node;

		Transaction tx = graphDb.beginTx(); 
		try{
			node = nodeFactories.get(index).getOrCreate(key, value);

			Label	label	= DynamicLabel.label( index);
			node.addLabel(label);
			node.setProperty(key, value);		
    			tx.success();

		} finally {
			tx.finish();
		}
		return node;
	}


*/
/*
	public static String writeObjectToString(Object element) {

		String	resultStr = "{ ";
	try {
		

//     		byte b[] = element.getBytes(); 
 //    		ByteArrayInputStream bi = new ByteArrayInputStream(b);
     		DataInputStream si = new DataInputStream(element);

     		List<String>  list= si.readObject();

		for(String str : list) {
			resultStr = resultStr + str + ",";
		}
		resultStr = resultStr.substring(0, resultStr.length() -1 ) + "}";
		
 	} catch (Exception e) {
     		System.out.println(e);
 	}
	}
*/
/*
	public static String convertPath() {

  	while (rels.hasNext()){
            Path path = rels.next();

            Iterable<Relationship>  relationships = path.relationships();
            java.util.Iterator<Relationship> relIterator = relationships.iterator();
            while (relIterator.hasNext()){
                Relationship rel = relIterator.next();
                String aNode = (String) rel.getStartNode().getProperty("name");
                String zNode = (String) rel.getEndNode().getProperty("name");
                Long value = (Long) rel.getProperty("value");
                System.out.println(aNode +" is connected to "+zNode + " with value "+value);


            }


	}

*/
	public static void processCollection(Collection	collection,
					JsonArrayBuilder nodeArrayBuilder,
					JsonArrayBuilder linksArrayBuilder) {



		for(Object entity : collection) {
			String instanceType =  getInstanceType(entity);  

			if( instanceType.equals("collection")) {

				processCollection((Collection) entity,
								nodeArrayBuilder,
								linksArrayBuilder);

			} else if( instanceType.equals("path")) {

				processPath( (org.neo4j.graphdb.Path)entity,
						nodeArrayBuilder,
						linksArrayBuilder);

			} else if( instanceType.equals("relationship")) {

				processRelationship( (Relationship)entity,
							nodeArrayBuilder,
							linksArrayBuilder);

			} else if( instanceType.equals("node")) {

				processNode( (Node)entity,
						nodeArrayBuilder);

			}
				
		}

	}
	public static void processPath ( org.neo4j.graphdb.Path path, 
					JsonArrayBuilder nodeArrayBuilder,
					JsonArrayBuilder linksArrayBuilder) {

		

            Iterable<Relationship>  relationships = path.relationships();
            java.util.Iterator<Relationship> relIterator = relationships.iterator();
            while (relIterator.hasNext()){
		
                Relationship rel = relIterator.next();
		processRelationship(rel, nodeArrayBuilder, linksArrayBuilder);
	    }
	}

	public static void processRelationship ( Relationship  rel, 
					JsonArrayBuilder nodeArrayBuilder,
					JsonArrayBuilder linksArrayBuilder) {

		Transaction tx = graphDb.beginTx(); 

		try {
                Node source = rel.getStartNode();
		processNode(source, nodeArrayBuilder);
                Node target =  rel.getEndNode();
		processNode(target, nodeArrayBuilder);
                String value =  rel.getType().name();

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder() ;
		objectBuilder.add("source", source.getId());
		objectBuilder.add("target", target.getId());
		objectBuilder.add("value", value);
			
		linksArrayBuilder.add(objectBuilder);		
		
		tx.success();
		}finally {
			tx.finish();

		}
	}

	public static void processNode ( Node  node, 
					JsonArrayBuilder nodeArrayBuilder) {

		
		Transaction tx = graphDb.beginTx(); 
		String		property = "";
		try {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder() ;
		objectBuilder.add("id", node.getId());
		property = (String)node.getProperty("_id", null);	
		if( property != null )
			objectBuilder.add("_id", property);
		property = (String) node.getProperty("name", null);	
		if( property != null )
			objectBuilder.add("name", property);

		Iterable<Label> labels = node.getLabels();
		for( Label label : labels) {
			objectBuilder.add("label", label.name());
			break;
		}
		nodeArrayBuilder.add(objectBuilder);		

		tx.success();
		}finally {
			tx.finish();

		}
	}

	public static String getInstanceType( Object object) {

		String resultStr = "";
		if( object instanceof java.util.Collection) {

			resultStr = resultStr + "collection";
		} else  if( object instanceof org.neo4j.graphdb.Node){ 
			resultStr = resultStr + "node";
	
		} else if( object instanceof org.neo4j.graphdb.Path) {
			resultStr = resultStr + "path";
		} else if( object instanceof org.neo4j.graphdb.Relationship) {
			resultStr = resultStr + "relationship";
		} else if( object instanceof java.util.Iterator) {
		
			resultStr = resultStr + "iterator";
		} else { 
			resultStr = resultStr + object.getClass().getName() + "     ";
		}
		return resultStr;
	}

}


