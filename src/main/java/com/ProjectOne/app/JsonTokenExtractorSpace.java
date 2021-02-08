
package com.ProjectOne.app;

import java.util.regex.*;

import org.scribe.extractors.*;
import org.scribe.exceptions.*;
import org.scribe.model.*;
import org.scribe.utils.*;

public class JsonTokenExtractorSpace implements AccessTokenExtractor
{
  private Pattern accessTokenPattern = Pattern.compile("\"access_token\"\\s*:\\s*\"(\\S*?)\"");

  public Token extract(String response)
  {
    Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
    Matcher matcher = accessTokenPattern.matcher(response);
    if(matcher.find())
    {
      return new Token(matcher.group(1), "", response);
    }
    else
    {
      throw new OAuthException("Cannot extract an access token. Response was: " + response);
    }
  }

}
