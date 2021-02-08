package com.ProjectOne.app;

import java.io.*;
import java.util.*;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.scribe.extractors.*;


public class GoogleOAuth20ServiceImpl implements OAuthService {

 private static final String VERSION = "2.0";
	private final GoogleApi20 api;
  private final OAuthConfig config;
	public GoogleOAuth20ServiceImpl(GoogleApi20 api, OAuthConfig config) {

		this.api = api;
		this.config = config;
	}
public Token getAccessToken(Token requestToken, Verifier verifier)
  {
    OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
    request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
    request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
    request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
    request.addBodyParameter("grant_type","authorization_code");

    request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
    //if(config.hasScope()) request.addBodyParameter(OAuthConstants.SCOPE, config.getScope());
    Response response = request.send();
    return api.getAccessTokenExtractor().extract(response.getBody());
  }

public Token getRequestToken()
  {
    throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
  }

  /**
   * {@inheritDoc}
   */
  public String getVersion()
  {
    return VERSION;
  }

  /**
   * {@inheritDoc}
   */
  public void signRequest(Token accessToken, OAuthRequest request)
  {
    request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
  }

  /**
   * {@inheritDoc}
   */
  public String getAuthorizationUrl(Token requestToken)
  {
    return api.getAuthorizationUrl(config);
  }

}
