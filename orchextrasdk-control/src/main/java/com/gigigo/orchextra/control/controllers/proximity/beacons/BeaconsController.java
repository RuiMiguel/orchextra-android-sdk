package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventType;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;
import javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class BeaconsController {

  private final InteractorInvoker interactorInvoker;
  private final ActionDispatcher actionDispatcher;
  private final Provider<InteractorExecution> beaconsEventsInteractorExecutionProvider;
  private final Provider<InteractorExecution> regionsProviderInteractorExecutionProvider;
  private final ErrorLogger errorLogger;

  public BeaconsController(InteractorInvoker interactorInvoker, ActionDispatcher actionDispatcher,
      Provider<InteractorExecution> beaconsEventsInteractorExecutionProvider,
      Provider<InteractorExecution> regionsProviderInteractorExecutionProvider,
      ErrorLogger errorLogger) {

    this.interactorInvoker = interactorInvoker;
    this.actionDispatcher = actionDispatcher;

    this.beaconsEventsInteractorExecutionProvider = beaconsEventsInteractorExecutionProvider;

    this.regionsProviderInteractorExecutionProvider = regionsProviderInteractorExecutionProvider;
    this.errorLogger = errorLogger;
  }

  public void getAllRegionsFromDataBase(final RegionsProviderListener regionsProviderListener) {
    executeBeaconInteractor(regionsProviderInteractorExecutionProvider.get(),
        new InteractorResult<List<OrchextraRegion>>() {
          @Override public void onResult(List<OrchextraRegion> regions) {
            regionsProviderListener.onRegionsReady(regions);
          }
        });
  }

  public void onBeaconsDetectedInRegion(List<OrchextraBeacon> beacons, OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor = (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(BeaconEventType.BEACONS_DETECTED);
    dispatchBeaconEvent(beacons, execution, beaconEventsInteractor);
  }

  public void onRegionEnter(OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor = (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(BeaconEventType.REGION_ENTER);
    dispatchBeaconEvent(region, execution, beaconEventsInteractor);
  }

  public void onRegionExit(OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor = (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(BeaconEventType.REGION_EXIT);
    dispatchBeaconEvent(region, execution, beaconEventsInteractor);
  }

  private void dispatchBeaconEvent(Object data, InteractorExecution interactorExecution, BeaconEventsInteractor beaconEventsInteractor) {
    beaconEventsInteractor.setData(data);
    executeBeaconInteractor(interactorExecution, new InteractorResult<List<BasicAction>>() {
      @Override public void onResult(List<BasicAction> actions) {
        for (BasicAction action:actions){
          action.performAction(actionDispatcher);
        }

      }
    });
  }

  private void executeBeaconInteractor(InteractorExecution interactorExecution, InteractorResult interactorResult) {
    interactorExecution.result(interactorResult)
        .error(InteractorError.class, new InteractorResult<InteractorError>() {
          @Override public void onResult(InteractorError result) {
            if (result instanceof BeaconsInteractorError) {
              BeaconsInteractorError beaconsInteractorError = (BeaconsInteractorError) result;
              manageBeaconInteractorError(beaconsInteractorError);
            } else {
              manageInteractorError(result);
            }
          }
        }).execute(interactorInvoker);
  }

  private void manageInteractorError(InteractorError result) {
    errorLogger.log(result.getError());
  }

  private void manageBeaconInteractorError(BeaconsInteractorError result) {

    switch (result.getBeaconBusinessErrorType()){
      case NO_SUCH_REGION_IN_ENTER:
        errorLogger.log(result.getError());
        break;
      case ALREADY_IN_ENTER_REGION:
        errorLogger.log(result.getError());
        break;
      default:
        errorLogger.log(result.getError());
        break;
    }
  }

}
