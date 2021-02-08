
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

public class OneGraphApi extends DefaultApi20
{
  private static final String AUTHORIZATION_URL = "http://www.one-graph.com/one-graph/api/authenticate?client_id=%s&response_type=code&redirect_uri=%s";

  @Override
  public String getAccessTokenEndpoint()
  {
    return "http://www.one-graph.com/one-graph/api/access_token?grant_type=authorization_code";
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
    return new JsonTokenExtractor();
  }
}
