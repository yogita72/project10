package com.ProjectOne.app;

import com.ProjectOne.app.Database;
import com.ProjectOne.app.DataBlob;
import com.ProjectOne.app.DataQuery;
import java.io.*;
import java.lang.InterruptedException;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.neo4j.graphdb.*;
import com.ProjectOne.app.AppJsonUtil;
import javax.json.*;

import javax.inject.Inject;

@WebServlet(
	name = "appDriver",
	urlPatterns = {"/data", "/auth", "/request", "/one-graph-api-auth", "/one-graph-api"}
)
public class AppDriver extends HttpServlet {

	@Inject
	private Database	database; 

	@Inject 
	public GraphJson	graphJson;

	private LinkedinClient linkedinClient;
	private OneGraphApiClient oneGraphApiClient;
	private FacebookClient	facebookClient;
	private GoogleClient	googleClient;
	@Override
	public void	init() 
		throws ServletException
	{
		System.out.println("calling Neo4j init");
//		Neo4jWithCache.init();
		linkedinClient = new LinkedinClient();
		oneGraphApiClient = new OneGraphApiClient();
		facebookClient = new FacebookClient();
		googleClient = new GoogleClient();
	}
	@Override 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	//	response.getWriter().println("Hello World!");


		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		System.out.println("contextPath :" + contextPath);
		String urlPattern = uri.substring(contextPath.length(), uri.length() ); 
		System.out.printf("get %s\n", uri);
		switch (urlPattern) {

			case "/data" :
				processDataRequest(request, response);
				break;

			case  "/auth" :
				processAuth(request, response);
				break;
			case  "/request" :
				processRequest(request, response);
				break;
			
			case  "/one-graph-api-auth" :
				processOneGraphApiAuth(request, response);
				break;
			case  "/one-graph-api" :
				processOneGraphApiRequest(request, response);
				break;
			default :
				//displayStartPage(request, response);
				break;


		} 
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{

		String uri = request.getRequestURI();

		String contextPath = request.getContextPath();
		System.out.println("contextPath :" + contextPath);
		String urlPattern = uri.substring(contextPath.length(), uri.length() ); 
		System.out.printf("post linkedin %s\n", uri);
		
		switch (urlPattern) {

			case "/data" :
				processDataRequest(request, response);
				break;

			case  "/auth" :
				processAuth(request, response);
				break;
			case  "/request" :
				processRequest(request, response);
				break;
			
			case  "/one-graph-api-auth" :
				processOneGraphApiAuth(request, response);
				break;
			case  "/one-graph-api" :
				processOneGraphApiRequest(request, response);
				break;
			default :
				//displayStartPage(request, response);
				break;


		} 
	}
	private void processCypherRequest( HttpServletRequest request,
						HttpServletResponse response)
		throws ServletException, IOException
	{
       		String action = request.getParameter("action");
		System.out.printf("Cypher action = %s\n", action);
        	if(action == null)
            		action = "query";
        	switch(action)
        	{
			case "query":
				this.showStartForm(request, response);
				break;
			
			case "execute":
				this.executeQuery(request,response);
				break;

			case "show":
				this.display(request,response);
				break;
			default:
				response.sendRedirect("data");
				break;
		}

    }


	private void processDataRequest(HttpServletRequest request,
								HttpServletResponse response)
			throws ServletException, IOException
	{

		String query = request.getParameter("body");
		String skipTrue = request.getParameter("skipTrue");
		String skipFalse = request.getParameter("skipFalse");
		String name = request.getParameter("name");
		String action = request.getParameter("action");

		request.getSession(true).setAttribute("originReferer", request.getHeader("referer"));	

		String skip = "";
		if( skipTrue != null ) {

			skip = "true";

		} else if ( skipFalse != null ) {

			skip = "false";
		}
		switch ( action ) {

			case "connectgraph":
			case "onegraph":

				System.out.println( "skip :" + skip );
				String prevUsername = (String)request.getSession(true).getAttribute("Username");	

				if( (prevUsername != null ) && !prevUsername.equals(query) ) {
					// remove user specific session attributes

					request.getSession(true).removeAttribute("skip");	

				}
				request.getSession(true).setAttribute("Username", query);	
				database.addQuery(query, action, skip);
 				response.sendRedirect("one-graph-api-auth");
				break;

			case "query":
				database.addQuery(query, action, null);
 				response.sendRedirect("one-graph-api-auth");
				break;

			case "one":
		//		String body = getBodyFromRequest(request);
				if( name != null) {
					query = name + ";" + query;
				}
				database.addData(query, action, request.getHeader("referer"));
 				response.sendRedirect("one-graph-api-auth");
				break;

			default:
				//request.getRequestDispatcher("/index.jsp").forward(request,response);
				response.sendRedirect("/index.jsp");
				
		}	
	}
	private void processAuth( HttpServletRequest request,
						HttpServletResponse response)
		throws ServletException, IOException
	{

		StringBuffer requestUrl = request.getRequestURL();
		String source = request.getParameter("source");
		String callbackUrl = requestUrl.substring(0, requestUrl.indexOf("/auth")) + "/request?source=" + source;
		
		request.getSession(true).setAttribute("originReferer", request.getHeader("referer"));	
		String authUrl;	
		switch ( source) {

			case "facebook" :
			authUrl = facebookClient.getUrl(callbackUrl);	
			break;

			case "google" :
			authUrl = googleClient.getUrl(callbackUrl);
			break; 

			case "linkedin" :
			default:
			authUrl = linkedinClient.getUrl(callbackUrl);	
			break;

		}	
 		response.sendRedirect(authUrl);
	}	


	private void processRequest( HttpServletRequest request,
						HttpServletResponse response)
		throws ServletException, IOException
	{

		String oauthVerifier = "" ;
		String oauthToken = "";
		String source = request.getParameter("source");
		if( source.equals("linkedin")) {
		oauthVerifier = request.getParameter("oauth_verifier");
		oauthToken  = request.getParameter("oauth_token");
		} else if( source.equals("facebook") || source.equals("google")) {

		oauthVerifier = request.getParameter("code");
		}
		switch (source) {
		
		case ("facebook"):
		{
			Token accessToken = facebookClient.getCredentials(oauthToken, oauthVerifier);

			String profile = facebookClient.getProfile(accessToken);
			String connections = facebookClient.getConnections(accessToken);
			break;
		}


		case ("google"):
		{
			Token accessToken = googleClient.getCredentials(oauthToken, oauthVerifier);

			//String profile = googleClient.getProfile(accessToken);
			String connections = googleClient.getConnections(accessToken);
			
			String referer = (String) request.getSession(false).getAttribute("originReferer");	
			database.addData(connections, source, referer);
 			response.sendRedirect("one-graph-api-auth");
			break;
		}
		case ("linkedin"):
		default:
		{
		Token accessToken = linkedinClient.getCredentials(oauthToken, oauthVerifier);

/*
		Neo4jWithCache.createUniqueFactory("persons");
		Neo4jWithCache.createUniqueFactory("profession");
		Neo4jWithCache.createUniqueFactory("nation");
		Neo4jWithCache.createUniqueFactory("school");
		Neo4jWithCache.createUniqueFactory("company");
*/
/*
		Neo4jWithCache.createIndex("persons", "name");
		Neo4jWithCache.createIndex("profession", "name");
		Neo4jWithCache.createIndex("nation", "name");
		Neo4jWithCache.createIndex("school", "name");
		Neo4jWithCache.createIndex("company", "name");

		LinkedinObject profile = linkedinClient.getProfile(accessToken);
		
		Node rootNode = profile.commitToNeo4j(null);
		List<LinkedinObject> connections = linkedinClient.getConnections(accessToken);

		for( LinkedinObject connection : connections ) {

			connection.commitToNeo4j(rootNode);
		}
		*/

		String profile = linkedinClient.getProfile(accessToken);
		String connections = linkedinClient.getConnections(accessToken);

		JsonObjectBuilder dataObjectBuilder = Json.createObjectBuilder();
		dataObjectBuilder.add("profile", AppJsonUtil.fromString(profile));
		dataObjectBuilder.add("connections", AppJsonUtil.fromString(connections));
		JsonObject dataJson = dataObjectBuilder.build();
		String data = AppJsonUtil.toString(dataJson); 

		String referer = (String) request.getSession(false).getAttribute("originReferer");	
		database.addData(data, source, referer);
		/*
		String contextPath = request.getContextPath();
		String oneGraphApiUrl = contextPath + "/one-graph-api-auth";
		*/
 		response.sendRedirect("one-graph-api-auth");
		break;
		}
		}
	}


	private void processOneGraphApiAuth( HttpServletRequest request,
						HttpServletResponse response)
		throws ServletException, IOException
	{

		StringBuffer requestUrl = request.getRequestURL();
		String callbackUrl = requestUrl.substring(0, requestUrl.indexOf("/one-graph-api-auth")) + "/one-graph-api";
		String authUrl = oneGraphApiClient.getUrl(callbackUrl);	
 		response.sendRedirect(authUrl);
	}


	private void processOneGraphApiRequest( HttpServletRequest request,
						HttpServletResponse response)
		throws ServletException, IOException
	{

		String oauthVerifier = request.getParameter("code");

		System.out.printf("Authorisation code = %s\n", oauthVerifier);
		Token accessToken = oneGraphApiClient.getCredentials(null, oauthVerifier);

		DataBlob dataBlob = database.getData();
		if ( dataBlob != null) {

			oneGraphApiClient.uploadData(accessToken, dataBlob.data, dataBlob.source);
			if( !dataBlob.source.equals("one")) {
				// invalidate root attribute, if new data has been uploaded
				request.getSession(false).removeAttribute("RootJson");	

			}
			if( dataBlob.referer != null ) {
				System.out.println("redirecting to " + dataBlob.referer);
				request.getSession(false).removeAttribute("originReferer");	
				response.sendRedirect(dataBlob.referer);
			} else {
			
				System.out.println("not redirecting " );
			}
	/*
			if( dataBlob.source.equals("one")) {
		//		response.sendRedirect("/update.jsp");
		//		request.getRequestDispatcher("/update.jsp").forward(request,response);

				if( dataBlob.referer != null ) {
				System.out.println("redirecting to " + dataBlob.referer);
				response.sendRedirect(dataBlob.referer);
				} else {
			
				System.out.println("not redirecting " );
				}

			} else {
			//	request.getRequestDispatcher("/index.jsp").forward(request,response);
				response.sendRedirect("/index.jsp");
			}
	*/
		} else {

			String result="";
			DataQuery dataQuery = database.getQuery();
			//String result = oneGraphApiClient.query(accessToken, query);

			Integer skip = 0;

			if( (dataQuery.type).equals("onegraph")
				|| (dataQuery.type).equals("connectgraph")) {

				if( dataQuery.skip != null) {
				if( dataQuery.skip.equals("true") ) {

					Object  skipAttr = request.getSession(false).getAttribute("skip");	
					if( skipAttr != null ) {
						skip = (Integer) skipAttr;
					}
				}		
				}
				request.getSession(true).setAttribute("skip", skip+100);	

				result = oneGraphApiClient.graph(accessToken, dataQuery.query, dataQuery.type, skip);
			} else {

				result = oneGraphApiClient.query(accessToken, dataQuery.query);
			}
			
			

			if( (dataQuery.type).equals("onegraph") ) {
	//				request.getRequestDispatcher("/index.jsp").forward(request,response);
					String filename = "/" + request.getSession(true).getId() +  "-onegraph.json";	
					writeToFile(result, filename);
					request.getSession(true).setAttribute("OneGraphJson", filename);	
					response.sendRedirect("/index.jsp");

			} else if( (dataQuery.type).equals("connectgraph") ) {

			//	request.getRequestDispatcher("/update.jsp").forward(request,response);
				String filename = "/" + request.getSession(true).getId() + skip.toString() +  "-connectGraph.json";	
				writeToFile(result, filename);
				request.getSession(true).setAttribute("ConnectGraphJson", filename);	
				response.sendRedirect("/update.jsp");

			} else {
				String filename = "/" + request.getSession(true).getId() + "-root.json";	
				writeToFile(result, filename);
				request.getSession(true).setAttribute("RootJson", filename);	
				String referer = (String) request.getSession(false).getAttribute("originReferer");	
				request.getSession(false).removeAttribute("originReferer");	
				//response.sendRedirect(referer);
				// response.sendRedirect("/update.jsp");
				response.getWriter().printf("%s", filename);
			}
		}	
//		String query = "match path = ( (n:persons {name : \"Yogita Bijani\"})--m) return path";
//		oneGraphApiClient.getProtectedResource(accessToken);

	}


	private void showStartForm(HttpServletRequest request,
								HttpServletResponse response)
			throws ServletException, IOException
	{


		request.getRequestDispatcher("/WEB-INF/jsp/view/startForm.jsp").forward(request,response);
	}

	private void executeQuery(HttpServletRequest request,
								HttpServletResponse response)
			throws ServletException, IOException
	{

		String	query;
		query = request.getParameter("body");


	//	database.addQuery(query);
/*
		String contextPath = request.getContextPath();
		String oneGraphApiUrl = contextPath + "/one-graph-api-auth";
*/
 		response.sendRedirect("one-graph-api-auth");
//		String result = Neo4jWithCache.runCypher(query);

	//	String filename = writeToFile(result);
		//response.setContentType("application/json");
			
//		response.getWriter().printf("%s", result);

//		request.getRequestDispatcher("/WEB-INF/jsp/view/vivagraph.jsp").forward(request,response);
 //		response.sendRedirect("cypher?action=show&result=" + result);

	}

	private void display(HttpServletRequest request,
								HttpServletResponse response)
			throws ServletException, IOException
	{
		String jsonStr= request.getParameter("result");

		request.getSession(true).setAttribute("inputJson", jsonStr);	
		graphJson.setJson(jsonStr);	
		System.out.println(graphJson.getJson());
		request.getSession(true).setAttribute("graphJson", graphJson);	
		
	//	response.getWriter().printf("result :\n%s\n", jsonStr);
//	request.getRequestDispatcher("/WEB-INF/jsp/view/example3.jsp").forward(request,response);
//		request.getRequestDispatcher("/WEB-INF/jsp/view/vivagraph.jsp").forward(request,response);
		//request.getRequestDispatcher("/WEB-INF/jsp/view/graph3.jsp").forward(request,response);
		request.getRequestDispatcher("/WEB-INF/jsp/view/load.jsp").forward(request,response);
	//	request.getRequestDispatcher("/WEB-INF/jsp/view/collapsibleForceLayout1.jsp").forward(request,response);
	}

	public void	writeToFile(String str, String filename) {

	
        File file = new File(getServletContext().getRealPath(filename));
        PrintWriter printWriter = null;
	OutputStream oFile = null; 
        try
        {
		oFile = new FileOutputStream(file, false); 


           	printWriter = new PrintWriter(oFile, true);
           	printWriter.println(str);
	    	printWriter.flush();
		oFile.flush();
		System.out.println("file flushed");
            	if ( oFile != null ) 
            	{
               		oFile.close();
            	}
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if ( printWriter != null ) 
            {
                printWriter.close();
            }
        }
	}

      private String getBodyFromRequest(HttpServletRequest request)
        {
                StringBuffer jb = new StringBuffer();
                String line = "";
                int     bytesRead = 0 ;
                int offset = 0 ;
                int     available;
                try {
                ServletInputStream reader = request.getInputStream();
                System.out.printf( "Available bytes : %d\n", reader.available());
                line = convertStreamToString(reader);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return line;
        }

        private String convertStreamToString(java.io.InputStream is) {
                java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
        }

}


