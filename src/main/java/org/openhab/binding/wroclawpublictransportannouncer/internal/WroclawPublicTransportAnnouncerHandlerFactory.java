/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wroclawpublictransportannouncer.internal;

import static org.openhab.binding.wroclawpublictransportannouncer.internal.WroclawPublicTransportAnnouncerBindingConstants.THING_TYPE_DEPARTURES;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link WroclawPublicTransportAnnouncerHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.wroclawpublictransportannouncer", service = ThingHandlerFactory.class)
public class WroclawPublicTransportAnnouncerHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_DEPARTURES);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_DEPARTURES.equals(thingTypeUID)) {
            return new WroclawPublicTransportAnnouncerHandler(thing);
        }

        return null;
    }
}