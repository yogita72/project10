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

public class LinkedinClient {

	private OAuthService service ;
	private List<Token> requestTokenList = new ArrayList<Token>();

	public String  getUrl( String callbackUrl) 
	{

		this.service = new ServiceBuilder()
                                .provider(LinkedInApi.class)
                                .apiKey("758yc2bz9bnj0c")
                                .apiSecret("hzTOriGyAIboxlk8")
				.callback(callbackUrl) 
                                .build();
    		//Scanner in = new Scanner(System.in);
    
    		System.out.println("=== LinkedIn's OAuth Workflow ===");
    		System.out.println();

    		// Obtain the Request Token
    		System.out.println("Fetching the Request Token...");
    		Token requestToken = service.getRequestToken();
		this.requestTokenList.add(requestToken);
    		System.out.println("Got the Request Token!");
    		System.out.println();

    		System.out.println("Now go and authorize Scribe here:");

		String authUrl = service.getAuthorizationUrl(requestToken);
    		System.out.println(authUrl);
		
		return authUrl;
	}

	public Token getCredentials(String oauthToken, String oauthVerifier)
	{


		Token requestToken = getRequestTokenFromOauthToken(oauthToken);
		Verifier verifier = new Verifier(oauthVerifier);
		// Trade the Request Token and Verfier for the Access Token
    		System.out.println("Trading the Request Token for an Access Token...");
    		Token accessToken = service.getAccessToken(requestToken, verifier);
    		System.out.println("Got the Access Token!");
    		System.out.println("(if your curious it looks like this: " + accessToken + " )");
    		System.out.println();
		return accessToken;
	 }

	public String getProfile( Token accessToken)
	{

		String resourceUrl = "http://api.linkedin.com/v1/people/~:(id,formatted-name,email-address,location,industry,positions,educations)?format=json"; 

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

		String resourceUrl = "http://api.linkedin.com/v1/people/~/connections:(id,formatted-name,email-address,location,industry,positions,educations)?format=json"; 

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
    		OAuthRequest oauthrequest = new OAuthRequest(Verb.GET, resourceUrl);
    		service.signRequest(accessToken, oauthrequest);
    		Response linkedinresponse = oauthrequest.send();
    		System.out.println("Got it! Lets see what we found...");
    		System.out.println();
    		System.out.println(linkedinresponse.getBody());
		return linkedinresponse.getBody();

	}	

	private Token getRequestTokenFromOauthToken( String oauthToken) 
	{

		Token		requestToken = null ; 

		for(Token token : this.requestTokenList) {

			String tokenStr = token.getToken();
			if( tokenStr.equals(oauthToken) ) {
				requestToken = token;
				requestTokenList.remove(token);
				break;

			}
		}
		return requestToken;

	}
}
