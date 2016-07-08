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

package com.gigigo.orchextra.dataprovision.imagerecognition;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.dataprovider.ImageRecognitionLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

public class ImageRecognitionLocalDataProviderImp implements ImageRecognitionLocalDataProvider {

  private final ConfigDBDataSource configDBDataSource;

  public ImageRecognitionLocalDataProviderImp(ConfigDBDataSource configDBDataSource) {
    this.configDBDataSource = configDBDataSource;
  }

  @Override public BusinessObject<VuforiaCredentials> obtainVuforiaInfo() {
    BusinessObject<ConfigInfoResult> bo = configDBDataSource.obtainConfigData();

    if (bo.isSuccess()) {
      ConfigInfoResult configInfoResult = bo.getData();
      if (configInfoResult.supportsVuforia()) {
        return new BusinessObject(configInfoResult.getVuforia(), BusinessError.createOKInstance());
      } else {
        return new BusinessObject(null, BusinessError.createKoInstance(
            "Image Recognition Module" + " configuration credentials not set or not enabled, review dashboard"));
      }
    } else {
      return new BusinessObject(null, bo.getBusinessError());
    }
  }
}