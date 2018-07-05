/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wroclawpublictransportannouncer.internal;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.ImmutableSet;

/**
 * The {@link WroclawPublicTransportAnnouncerBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
@NonNullByDefault
public class WroclawPublicTransportAnnouncerBindingConstants {

    private static final String BINDING_ID = "wroclawpublictransportannouncer";

    public static final ThingTypeUID THING_TYPE_DEPARTURES = new ThingTypeUID(BINDING_ID, "departures");

    public static final String CHANNEL_FIRST_DEPARTURE = "firstDeparture";
    public static final String CHANNEL_SECOND_DEPARTURE = "secondDeparture";
    public static final String CHANNEL_THIRD_DEPARTURE = "thirdDeparture";
    public static final String CHANNEL_FOURTH_DEPARTURE = "fourthDeparture";
    public static final String CHANNEL_FIFTH_DEPARTURE = "fifthDeparture";
    public static final String CHANNEL_SIXTH_DEPARTURE = "sixthDeparture";
    public static final String CHANNEL_SEVENTH_DEPARTURE = "seventhDeparture";
    public static final String CHANNEL_EIGHT_DEPARTURE = "eightDeparture";
    public static final String CHANNEL_NINTH_DEPARTURE = "ninthDeparture";
    public static final String CHANNEL_TENTH_DEPARTURE = "tenthDeparture";
    public static final String CHANNEL_ELEVENTH_DEPARTURE = "eleventhDeparture";
    public static final String CHANNEL_TWELFTH_DEPARTURE = "twelfthDeparture";
    public static final String CHANNEL_THIRTEENTH_DEPARTURE = "thirteenthDeparture";
    public static final String CHANNEL_FOURTEENTH_DEPARTURE = "fourteenthDeparture";
    public static final String CHANNEL_FIFTEENTH_DEPARTURE = "fifteenthDeparture";
    public static final String CHANNEL_SIXTEENTH_DEPARTURE = "sixteenthDeparture";
    public static final String CHANNEL_SEVENTEENTH_DEPARTURE = "seventeenthDeparture";
    public static final String CHANNEL_EIGHTEENTH_DEPARTURE = "eighteenthDeparture";
    public static final String CHANNEL_NINETEENTH_DEPARTURE = "nineteenthDeparture";
    public static final String CHANNEL_TWENTIETH_DEPARTURE = "twentiethDeparture";

    public static final Set<String> SUPPORTED_CHANNEL_IDS = ImmutableSet.of(CHANNEL_FIRST_DEPARTURE,
            CHANNEL_SECOND_DEPARTURE, CHANNEL_THIRD_DEPARTURE, CHANNEL_FOURTH_DEPARTURE, CHANNEL_FIFTH_DEPARTURE,
            CHANNEL_SIXTH_DEPARTURE, CHANNEL_SEVENTH_DEPARTURE, CHANNEL_EIGHT_DEPARTURE, CHANNEL_NINTH_DEPARTURE,
            CHANNEL_TENTH_DEPARTURE, CHANNEL_ELEVENTH_DEPARTURE, CHANNEL_TWELFTH_DEPARTURE,
            CHANNEL_THIRTEENTH_DEPARTURE, CHANNEL_FOURTEENTH_DEPARTURE, CHANNEL_FIFTEENTH_DEPARTURE,
            CHANNEL_SIXTEENTH_DEPARTURE, CHANNEL_SEVENTEENTH_DEPARTURE, CHANNEL_EIGHTEENTH_DEPARTURE,
            CHANNEL_NINETEENTH_DEPARTURE, CHANNEL_TWENTIETH_DEPARTURE);
}