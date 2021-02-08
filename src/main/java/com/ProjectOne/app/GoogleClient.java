package com.ProjectOne.app;

import java.io.*;
import java.util.*;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.w3c.dom.*;
import java.lang.Exception;
import org.xml.sax.SAXException;
import javax.json.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GoogleClient {

	private OAuthService service ;
  	private static final Token EMPTY_TOKEN = null;
	private static final String SCOPE="https://www.google.com/m8/feeds";

	public String  getUrl( String callbackUrl) 
	{

		this.service = new ServiceBuilder()
                                .provider(GoogleApi20.class)
                                .apiKey("374980516767-ep5h5tqddv446lqln676hn55avffjqje.apps.googleusercontent.com")
                                .apiSecret("W8Y5gdvQ3UPRRNc4Wg_O_urW")
				.scope(SCOPE)
				.callback(callbackUrl) 
                                .build();
    		//Scanner in = new Scanner(System.in);
    
		System.out.println("Fetching the Authorization URL...");
    		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
    		System.out.println("Got the Authorization URL!");
    		System.out.println("Now go and authorize Scribe here:");
    		System.out.println(authorizationUrl);

		return authorizationUrl;
	}

	public Token getCredentials(String oauthToken, String oauthVerifier)
	{

    		Verifier verifier = new Verifier(oauthVerifier);
    		System.out.println();
    
    		// Trade the Request Token and Verfier for the Access Token
    		System.out.println("Trading the Request Token for an Access Token...");
    		Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
    		System.out.println("Got the Access Token!");
    		System.out.println("(if your curious it looks like this: " + accessToken + " )");
    		System.out.println();

		return accessToken;
	 }

	public String getProfile( Token accessToken)
	{

  		String resourceUrl ="https://www.google.com/m8/feeds/contacts/default/full?start-index=26&max-results=25";


		String body = getProtectedResource(accessToken, resourceUrl);

/*
		JsonStructure json = AppJsonUtil.fromString(body);

		LinkedinObject profile;

		profile = new LinkedinObject((JsonObject) json);

		return profile;
*/
		return body;
	}

	
	public String getConnections( Token accessToken)
	{

		int startIndex = 1;
		int lastIndex = 25;
		StringBuffer	cummulativeBuffer= new StringBuffer();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		do {
  		String resourceUrlFormat ="https://www.google.com/m8/feeds/contacts/default/full?alt=json&start-index=%d&max-results=25";

		String resourceUrl = String.format(resourceUrlFormat, startIndex);
		
		String body = getProtectedResource(accessToken, resourceUrl);
		if( startIndex == 1 ) {

			lastIndex = extractTotalResults(body);
		}
		
		arrayBuilder.add(body);
		startIndex = startIndex + 25 ;	
		} while ( lastIndex > startIndex); 
		
		JsonObjectBuilder	objectBuilder = Json.createObjectBuilder();	
		
		objectBuilder.add("feeds", arrayBuilder);
		JsonObject json = objectBuilder.build();
		String cummulativeResults = AppJsonUtil.toString(json);

		System.out.println(cummulativeResults);
		
		/*
		JsonObject  json= (JsonObject)AppJsonUtil.fromString(body);

		JsonArray jsonArray = (JsonArray)json.get("values");
		Iterator<JsonValue> iterator = jsonArray.iterator();
		List<LinkedinObject>	connections = new ArrayList<LinkedinObject>();

		while( iterator.hasNext()) {
			LinkedinObject connection = new LinkedinObject((JsonObject)(iterator.next()));
			connections.add(connection);
		}
		return connections;
		*/
		return cummulativeResults;
	}
	public String getProtectedResource(Token accessToken, String resourceUrl)
	{
		// Now let's go and ask for a protected resource!
    		System.out.println("Now we're going to access a protected resource...");
    		OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl);
    		service.signRequest(accessToken, request);
		request.addHeader("GData-Version", "3.0");
    		Response response = request.send();
    		System.out.println("Got it! Lets see what we found...");
    		System.out.println();
    		System.out.println(response.getCode());
    		System.out.println(response.getBody());
		return response.getBody();
	}	

	public int extractTotalResults(String body) {

  		Pattern accessTokenPattern = Pattern.compile("totalResults\":\\{\"\\$t\":\"(\\S*?)\"");

    		Matcher matcher = accessTokenPattern.matcher(body);
    		if(matcher.find())
    		{
			if(  matcher.group(1) != null ) {
				System.out.println("lastIndex found .. " );
				return Integer.parseInt(matcher.group(1));
			}else {
				System.out.println("lastIndex not found null .. " );
				return 25;
			}
		} else {
			System.out.println("lastIndex not found find .. " );
			return 25;
		}
	}
}
