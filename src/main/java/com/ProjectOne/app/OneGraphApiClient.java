package com.ProjectOne.app;

import java.io.*;
import java.util.*;
import java.util.*;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class OneGraphApiClient
{
  private static final Token EMPTY_TOKEN = null;
private OAuthService service ;
private List<Token> requestTokenList = new ArrayList<Token>();

	public String  getUrl( String callbackUrl) 
	{
    // Replace these with your own api key and secret
    String apiKey = "oauth2test";
    String apiSecret = "oauth2clientsecret";
    service = new ServiceBuilder()
                                  .provider(OneGraphApi.class)
                                  .apiKey(apiKey)
                                  .apiSecret(apiSecret)
                                  .callback(callbackUrl)
                                  .build();
   // Scanner in = new Scanner(System.in);

    System.out.println("=== OAuth Workflow ===");
    System.out.println();

    // Obtain the Authorization URL
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


	public String uploadData( Token accessToken, String data, String source)
	{

		String resourceUrl = "http://www.one-graph.com/one-graph/api/people/upload_data";
    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    System.out.println("Access Token" + accessToken.getToken());
	
    //OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl + accessToken.getToken());
    OAuthRequest request = new OAuthRequest(Verb.POST, resourceUrl );

	request.addHeader("Authorization",  "Bearer " + accessToken.getToken());
	request.addHeader("Content-Type",  "text/html");
	request.addPayload(  data);
//	request.addBodyParameter("Content-Type", "text/html");
	request.addQuerystringParameter("data", "body");
	request.addQuerystringParameter("source", source);
   // service.signRequest(accessToken, request);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getCode());
    System.out.println(response.getBody());

    System.out.println();
    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");

	return response.getBody();
  }

	public String query( Token accessToken, String query)
{

		String resourceUrl = "http://www.one-graph.com/one-graph/api/people/query";
    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    System.out.println("Access Token" + accessToken.getToken());
	
    //OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl + accessToken.getToken());
    OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl );

	request.addHeader("Authorization",  "Bearer " + accessToken.getToken());
	request.addQuerystringParameter("query", query);
   // service.signRequest(accessToken, request);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getCode());
    System.out.println(response.getBody());

    System.out.println();
    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
	return response.getBody();
}
	public String graph( Token accessToken, String names, String type, Integer skip)
 {
		String resourceUrl;	 
		if( type.equals("onegraph") ){
			resourceUrl = "http://www.one-graph.com/one-graph/api/people/onegraph";
		} else {
			resourceUrl = "http://www.one-graph.com/one-graph/api/people/connectgraph";
		}
    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    System.out.println("Access Token" + accessToken.getToken());
	
    //OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl + accessToken.getToken());
    OAuthRequest request = new OAuthRequest(Verb.GET, resourceUrl );

	request.addHeader("Authorization",  "Bearer " + accessToken.getToken());
	request.addQuerystringParameter("names", names);
	if( skip != 0 ) {
		System.out.printf( "skip = %d\n", skip);
		request.addQuerystringParameter("skip", skip.toString());
	}
   // service.signRequest(accessToken, request);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getCode());
    System.out.println(response.getBody());

    System.out.println();
    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
	return response.getBody();
  } 
}

