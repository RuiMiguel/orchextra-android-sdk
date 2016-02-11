package com.gigigo.orchextra.device.actions.scheduler;

import android.content.Context;
import android.os.Bundle;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsScheduler;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.sdk.features.GooglePlayServicesFeature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class ActionsSchedulerGcmImpl implements ActionsScheduler {

  private static final long DEFAULT_DELAY = 3600L;
  public static final String BUNDLE_TASK_PARAM_NAME = "TASK";

  private final GcmNetworkManager gcmNetworkManager;
  private final AndroidBasicActionMapper androidBasicActionMapper;

  public ActionsSchedulerGcmImpl(Context context, FeatureListener featureListener, AndroidBasicActionMapper androidBasicActionMapper) {
    checkPlayServicesStatus(context, featureListener);
    this.gcmNetworkManager = GcmNetworkManager.getInstance(context);
    this.androidBasicActionMapper = androidBasicActionMapper;
  }

  private int checkPlayServicesStatus(Context context, FeatureListener featureListener) {
    GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
    int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
    featureListener.onFeatureStatusChanged(new GooglePlayServicesFeature(status));
    return status;
  }

  @Override public void scheduleAction(ScheduledAction action) {

    AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToExternalClass(
        action.getBasicAction());

    Bundle bundle = new Bundle();
    bundle.putParcelable(BUNDLE_TASK_PARAM_NAME, androidBasicAction);

    OneoffTask task = new OneoffTask.Builder()
        .setService(OrchextraGcmTaskService.class)
        .setTag(action.getId())
        .setExecutionWindow(action.getScheduleTime(), DEFAULT_DELAY)
        .setPersisted(true)
        .setUpdateCurrent(true)
        .setRequiresCharging(false)
        .setRequiredNetwork(Task.NETWORK_STATE_ANY)
        .setExtras(bundle)
        .build();

    gcmNetworkManager.schedule(task);
  }

  @Override public void cancelAction(ScheduledAction action) {
    gcmNetworkManager.cancelTask(action.getId(), OrchextraGcmTaskService.class);
  }

  @Override public boolean hasPersistence() {
    return true;
  }
}