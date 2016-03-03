/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceIntentServiceHandler;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.di.scopes.PerService;
import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;
import com.gigigo.orchextra.domain.background.BackgroundTasksManagerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/1/16.
 */
@Module
public class ServicesModule {

  @PerService @Provides BackgroundTasksManager provideBackgroundTasksManager(BeaconScanner beaconScanner,
                                                                             GeofenceRegister geofenceRegister){
    return new BackgroundTasksManagerImpl(beaconScanner, geofenceRegister);
  }

  @PerService
  @Provides
  LocationMapper provideLocationMapper() {
    return new LocationMapper();
  }

  @PerService @Provides
  AndroidGeofenceIntentServiceHandler provideAndroidGeofenceIntentServiceHandler(LocationMapper locationMapper) {
    return new AndroidGeofenceIntentServiceHandler(locationMapper);
  }

}
