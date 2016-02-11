package com.gigigo.orchextra.control.controllers.proximity;

import com.gigigo.orchextra.control.controllers.base.Delegate;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import java.util.List;

import me.panavtec.threaddecoratedview.views.qualifiers.NotDecorated;
import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView
public interface ProximityItemDelegate extends Delegate{

    @NotDecorated
    void onControllerReady();

    void registerGeofences(List<OrchextraGeofence> geofenceControlList);
}