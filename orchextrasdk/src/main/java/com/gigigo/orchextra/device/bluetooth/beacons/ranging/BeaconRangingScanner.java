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

package com.gigigo.orchextra.device.bluetooth.beacons.ranging;

import com.gigigo.orchextra.domain.abstractions.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import java.util.List;
import org.altbeacon.beacon.Region;


public interface BeaconRangingScanner extends RegionsProviderListener {

  void initRangingScanForAllKnownRegions(AppRunningModeType appRunningModeType);
  //TODO LIB_CRUNCH altBeacon
  void initRangingScanForDetectedRegion(List<Region> regions,
      BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType);

  void stopAllCurrentRangingScannedRegions();
  //TODO LIB_CRUNCH altBeacon
  void stopRangingScanForDetectedRegion(Region region);

  BackgroundBeaconsRangingTimeType getBackgroundBeaconsRangingTimeType();

  boolean isRanging();
}
