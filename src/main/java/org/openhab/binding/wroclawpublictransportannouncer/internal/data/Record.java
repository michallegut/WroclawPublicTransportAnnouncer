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
 * The {@link Record} class contains data of a single departure including time, line symbol and direction.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
public class Record implements Comparable<Record> {

    Time time;
    String line;
    String direction;

    public Record(int hour, int minute, String line, String direction) {
        time = new Time(hour, minute);
        this.line = line;
        this.direction = direction;
    }

    public Time getTime() {
        return time;
    }

    public String getLine() {
        return line;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public int compareTo(Record record) {
        int result = this.time.compareTo(record.time);
        if (result == 0) {
            result = this.line.compareTo(record.line);
            if (result == 0) {
                result = this.direction.compareTo(record.direction);
            }
        }
        return result;
    }
}