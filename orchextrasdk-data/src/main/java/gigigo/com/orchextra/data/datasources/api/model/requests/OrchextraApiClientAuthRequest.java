package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class OrchextraApiClientAuthRequest extends OrchextraApiAuthRequest{

  public OrchextraApiClientAuthRequest(GrantType grantType, Credentials credentials) {
    super(grantType, credentials);
  }

  @Override ApiCredentials obtainApiCredentialsFromCredentials(Credentials credentials) {
    return new ApiClientAuthCredentials(credentials);
  }

}
