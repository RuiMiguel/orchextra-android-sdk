package com.gigigo.orchextra.di.modules.device;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;
import com.gigigo.orchextra.sdk.application.ForegroundTasksManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module(includes = {BluetoothModule.class, ActionsModule.class, NotificationsModule.class, GeolocationModule.class})
public class DeviceModule {

  @Singleton @Provides
  ForegroundTasksManager provideBackgroundTasksManager(BeaconScanner beaconScanner,
                                                       ConfigDelegateImp configDelegateImp,
                                                       GeofenceRegister geofenceRegister){
    return  new ForegroundTasksManagerImpl(beaconScanner, configDelegateImp, geofenceRegister);
  }

  @Provides PermissionChecker providePermissionChecker(ContextProvider contextProvider) {
    return new AndroidPermissionCheckerImpl(contextProvider.getApplicationContext(), contextProvider);
  }

  @Singleton
  @Provides PermissionLocationImp providePermissionLocationImp() {
    return new PermissionLocationImp();
  }

  @Singleton
  @Provides GoogleApiClientConnector provideGoogleApiClientConnector(ContextProvider contextProvider,
      PermissionChecker permissionChecker,
      PermissionLocationImp permissionLocationImp) {
    return new GoogleApiClientConnector(contextProvider, permissionChecker, permissionLocationImp);
  }

  @Singleton
  @Provides AndroidApp provideAndroidApp() {
    return new AndroidApp();
  }

  @Singleton
  @Provides AndroidDevice provideAndroidDevice(ContextProvider contextProvider) {
    return new AndroidDevice(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides ErrorLogger provideErrorLogger() {
    return new ErrorLogger() {
      @Override public void log(BusinessError businessError) {
        GGGLogImpl.log(businessError.getMessage(), LogLevel.ERROR);
      }

      @Override public void log(String message) {
        GGGLogImpl.log(message, LogLevel.ERROR);
      }
    };
  }


}
