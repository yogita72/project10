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

public class FacebookClient {

	private OAuthService service ;
  	private static final Token EMPTY_TOKEN = null;

	public String  getUrl( String callbackUrl) 
	{

		this.service = new ServiceBuilder()
                                .provider(FacebookApi.class)
                                .apiKey("1454826821430173")
                                .apiSecret("48a86017afc213cb7026f121f7f14892")
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

  		String resourceUrl = "https://graph.facebook.com/me";

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

  		String resourceUrl = "https://graph.facebook.com/me/friends";

		String body = getProtectedResource(accessToken, resourceUrl);

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
		return body;
	}
	public String getProtectedResource(Token accessToken, String resourceUrl)
	{
		// Now let's go and ask for a protected resource!
    		System.out.println("Now we're going to access a protected resource...");
    		OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl);
    		service.signRequest(accessToken, request);
    		Response response = request.send();
    		System.out.println("Got it! Lets see what we found...");
    		System.out.println();
    		System.out.println(response.getCode());
    		System.out.println(response.getBody());
		return response.getBody();
	}	

}
