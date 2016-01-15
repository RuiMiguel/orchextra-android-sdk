package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  AuthenticationInteractor provideauthenticationInteractor();
  RetrieveGeofencesFromDatabaseInteractor provideRetrieveGeofencesFromDatabaseInteractor();
}
