/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wroclawpublictransportannouncer.internal.data;

/**
 * The {@link Time} class represents time consisting of hour and minute.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
public class Time implements Comparable<Time> {

    int hour;
    int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int calculateTimeDifferenceInMinutes(Time time) {
        return Math.abs((time.hour - this.hour) * 60 + time.minute - this.minute);
    }

    @Override
    public int compareTo(Time time) {
        int result = this.hour - time.hour;
        if (result == 0) {
            result = this.minute - time.minute;
        }
        return result;
    }
}