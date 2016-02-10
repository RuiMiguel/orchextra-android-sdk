package com.gigigo.orchextra.dataprovision.config.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.config.strategy.ConfigInfoResult;

import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface ConfigDBDataSource {
  boolean saveConfigData(ConfigInfoResult config);

  BusinessObject<ConfigInfoResult> obtainConfigData();
  BusinessObject<OrchextraGeofence> obtainGeofenceById(String uuid);
  BusinessObject<List<OrchextraGeofence>> obtainGeofences();

  BusinessObject<List<OrchextraRegion>> obtainRegionsForScan();
}
