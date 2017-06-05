package com.gigigo.orchextra;

import android.app.Application;
import com.gigigo.imagerecognition.core.ImageRecognition;
import com.gigigo.orchextra.device.bluetooth.beacons.BeaconBackgroundModeScan;

public class OrchextraBuilder {

  private final Application application;
  private String apiKey;
  private String apiSecret;
  private OrchextraCompletionCallback orchextraCompletionCallback;
  private ImageRecognition imageRecognitionModule;
  private OrchextraLogLevel orchextraLogLevel;
  private String gcmSenderId;
  private String notificationActivityName = "";
  private BeaconBackgroundModeScan bckBeaconMode = BeaconBackgroundModeScan.NORMAL;

  public OrchextraBuilder(Application application) {
    this.application = application;
  }

  public BeaconBackgroundModeScan getBckBeaconMode() {
    return bckBeaconMode;
  }

  public OrchextraBuilder setBackgroundBeaconScanMode(BeaconBackgroundModeScan bckBeaconMode) {
    this.bckBeaconMode = bckBeaconMode;
    return this;
  }

  /**
   * Level of log which will print to console.
   *
   * @param orchextraLogLevel <p>OrchextraSDKLogLevel.NONE Print nothing</p>
   * <p>OrchextraSDKLogLevel.ERROR Print only error logs</p>
   * <p>OrchextraSDKLogLevel.WARN Print warn logs</p>
   * <p>OrchextraSDKLogLevel.DEBUG Print debug logs</p>
   * <p>OrchextraSDKLogLevel.NETWORK Print debug and network logs</p>
   * <p>OrchextraSDKLogLevel.ALL Print debug, network and bluethoot logs</p>
   */
  public OrchextraBuilder setLogLevel(OrchextraLogLevel orchextraLogLevel) {
    this.orchextraLogLevel = orchextraLogLevel;
    return this;
  }

  /**
   * Set the api key and secret of your project.
   * <p/>
   * You can obtain this credentials from the Orchextra dashboard
   */
  public OrchextraBuilder setApiKeyAndSecret(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    return this;
  }

  public Application getApplication() {
    return application;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }

  public OrchextraCompletionCallback getOrchextraCompletionCallback() {
    return orchextraCompletionCallback;
  }

  /**
   * Callback status when orchextra is initialized
   */
  public OrchextraBuilder setOrchextraCompletionCallback(
      OrchextraCompletionCallback orchextraCompletionCallback) {
    this.orchextraCompletionCallback = orchextraCompletionCallback;
    return this;
  }

  public ImageRecognition getImageRecognitionModule() {
    return imageRecognitionModule;
  }

  /**
   * Include the image recognition module in Orchextra core.
   * <p/>
   * <a href="https://github.com/Orchextra/orchextra-android-sdk#image-recognition-integration">For
   * more info, visit this link</a>.
   */
  public OrchextraBuilder setImageRecognitionModule(ImageRecognition imageRecognitionModule) {
    this.imageRecognitionModule = imageRecognitionModule;
    return this;
  }

  public OrchextraLogLevel getOrchextraLogLevel() {
    return orchextraLogLevel;
  }

  public String getGcmSenderId() {
    return gcmSenderId;
  }

  public OrchextraBuilder setGcmSenderId(String gcmSenderId) {
    this.gcmSenderId = gcmSenderId;
    return this;
  }

  public String getNotificationActivityName() {
    return this.notificationActivityName;
  }

  public OrchextraBuilder setNotificationActivityClass(String mainActivityClass) {
    this.notificationActivityName = mainActivityClass;
    return this;
  }
}
