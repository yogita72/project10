
package com.ProjectOne.app;

import java.io.*;
import java.util.*;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.scribe.extractors.*;
import org.scribe.model.*;
import org.scribe.utils.*;

public class GoogleApi20 extends DefaultApi20
{
  private static final String AUTHORIZATION_URL = "https://accounts.google.com/o/oauth2/auth?client_id=%s&response_type=code&redirect_uri=%s&scope=https://www.google.com/m8/feeds";

  @Override
  public String getAccessTokenEndpoint()
  {
    return "https://accounts.google.com/o/oauth2/token";
  }

@Override
public Verb getAccessTokenVerb()
  {
    return Verb.POST;
  }

  @Override
  public String getAuthorizationUrl(OAuthConfig config)
  {
    Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Foursquare2 does not support OOB");
    return String.format(AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
  }

  @Override
  public AccessTokenExtractor getAccessTokenExtractor()
  {
    return new JsonTokenExtractorSpace();
  }


	@Override
	public OAuthService createService(OAuthConfig config)
  {
    return new GoogleOAuth20ServiceImpl(this, config);
  }

}
