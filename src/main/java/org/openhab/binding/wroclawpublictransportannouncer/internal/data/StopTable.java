/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wroclawpublictransportannouncer.internal.data;

import java.util.SortedSet;

/**
 * The {@link StopTable} class contains times of busses and trams departures for working days, saturdays and sundays in
 * form of Record class instances.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
public class StopTable {

    SortedSet<Record> workingDays;
    SortedSet<Record> saturdays;
    SortedSet<Record> sundays;

    public StopTable(SortedSet<Record> workingDays, SortedSet<Record> saturdays, SortedSet<Record> sundays) {
        this.workingDays = workingDays;
        this.saturdays = saturdays;
        this.sundays = sundays;
    }

    public SortedSet<Record> getWorkingDays() {
        return workingDays;
    }

    public SortedSet<Record> getSaturdays() {
        return saturdays;
    }

    public SortedSet<Record> getSundays() {
        return sundays;
    }
}