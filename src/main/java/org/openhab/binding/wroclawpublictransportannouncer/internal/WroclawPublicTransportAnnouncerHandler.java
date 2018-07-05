/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wroclawpublictransportannouncer.internal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.wroclawpublictransportannouncer.internal.data.DataDownloader;
import org.openhab.binding.wroclawpublictransportannouncer.internal.data.Record;
import org.openhab.binding.wroclawpublictransportannouncer.internal.data.StopTable;
import org.openhab.binding.wroclawpublictransportannouncer.internal.data.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * The {@link WroclawPublicTransportAnnouncerHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
@NonNullByDefault
public class WroclawPublicTransportAnnouncerHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(WroclawPublicTransportAnnouncerHandler.class);

    @Nullable
    private WroclawPublicTransportAnnouncerConfiguration config;

    private static final String URL = "https://www.wroclaw.pl/open-data/6db186a9-9b94-4ed4-8d63-f3fa6d38dc5f/XML-rozkladyjazdy.zip";
    private static final DataDownloader DATA_DOWNLOADER = new DataDownloader();

    @Nullable
    private String stopName;
    @Nullable
    private StopTable stopTable;
    @Nullable
    private LocalDate dataDownloadDate;
    @Nullable
    private Queue<Record> currentDayDeparturesQueue;
    @Nullable
    private Queue<Record> nextDayDeparturesQueue;
    @Nullable
    private List<Record> currentDayDepartures;
    @Nullable
    private List<Record> nextDayDepartures;
    @Nullable
    private ScheduledFuture<?> refreshJob;

    public WroclawPublicTransportAnnouncerHandler(Thing thing) {
        super(thing);
    }

    @SuppressWarnings("null")
    private void startAutomaticRefresh() {
        if (refreshJob == null || refreshJob.isCancelled()) {
            Runnable runnable = () -> {
                if (stopTable == null || ChronoUnit.DAYS.between(dataDownloadDate, LocalDate.now()) >= 1) {
                    try {
                        updateData();
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                        logger.debug("An exception occured during updating data.");
                    }
                }
                if (currentDayDepartures != null) {
                    updateDepartures();
                }
            };
            refreshJob = scheduler.scheduleWithFixedDelay(runnable, 0, 30, TimeUnit.SECONDS);
        }
    }

    @SuppressWarnings("null")
    private void updateDepartures() {
        Calendar currentDate = Calendar.getInstance();
        Time currentTime = new Time(currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE));
        clearPastDepartures(currentTime);
        fillDeparturesArrays();
        int i = 0;
        for (String channel : WroclawPublicTransportAnnouncerBindingConstants.SUPPORTED_CHANNEL_IDS) {
            if (currentDayDepartures.size() > i) {
                Record departure = currentDayDepartures.get(i);
                updateState(channel, new StringType(departure.getLine() + ", " + departure.getDirection() + ", "
                        + departure.getTime().calculateTimeDifferenceInMinutes(currentTime) + " min\n"));
            } else {
                Record departure = nextDayDepartures.get(i - currentDayDepartures.size());
                updateState(channel,
                        new StringType(departure.getLine() + ", " + departure.getDirection() + ", "
                                + (currentTime.calculateTimeDifferenceInMinutes(new Time(24, 0))
                                        + departure.getTime().getHour() * 60 + departure.getTime().getMinute()
                                        + " min\n")));
            }
            i++;
        }
    }

    @SuppressWarnings("null")
    private void fillDeparturesArrays() {
        while (currentDayDepartures.size() + nextDayDepartures.size() < 20) {
            if (!currentDayDeparturesQueue.isEmpty()) {
                currentDayDepartures.add(currentDayDeparturesQueue.poll());
            } else {
                nextDayDepartures.add(nextDayDeparturesQueue.poll());
            }
        }
    }

    @SuppressWarnings("null")
    private void clearPastDepartures(Time currentTime) {
        while (!currentDayDepartures.isEmpty() && currentDayDepartures.get(0).getTime().compareTo(currentTime) < 1) {
            currentDayDepartures.remove(0);
        }
    }

    private void updateData() throws ParserConfigurationException, SAXException, IOException {
        stopTable = DATA_DOWNLOADER.getData(URL, stopName);
        dataDownloadDate = LocalDate.now();
        enqueueDepartures();
        currentDayDepartures = new ArrayList<>();
        nextDayDepartures = new ArrayList<>();
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            if (currentDayDepartures != null) {
                updateDepartures();
            }
        } else {
            logger.debug("Wroclaw Public Transport Announcer binding is read-only and can not handle command {}.",
                    command);
        }
    }

    @SuppressWarnings("null")
    @Override
    public void initialize() {
        config = getConfigAs(WroclawPublicTransportAnnouncerConfiguration.class);
        stopName = StringUtils.trimToNull(config.stopName);
        if (stopName != null) {
            updateStatus(ThingStatus.ONLINE);
            startAutomaticRefresh();
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Parameter 'apikey' is mandatory and must be configured");
        }
    }

    @SuppressWarnings("null")
    private void enqueueDepartures() {
        SortedSet<Record> currentDayQueueSource;
        SortedSet<Record> nextDayQueueSource;
        Calendar currentDate = Calendar.getInstance();
        switch (currentDate.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.FRIDAY:
                currentDayQueueSource = stopTable.getWorkingDays();
                nextDayQueueSource = stopTable.getSaturdays();
                break;
            case Calendar.SATURDAY:
                currentDayQueueSource = stopTable.getSaturdays();
                nextDayQueueSource = stopTable.getSundays();
                break;
            case Calendar.SUNDAY:
                currentDayQueueSource = stopTable.getSundays();
                nextDayQueueSource = stopTable.getWorkingDays();
                break;
            default:
                currentDayQueueSource = stopTable.getWorkingDays();
                nextDayQueueSource = stopTable.getWorkingDays();
                break;
        }
        Queue<Record> newCurrentDayDisplayQueue = new PriorityQueue<>(currentDayQueueSource);
        Queue<Record> newNextDayDisplayQueue = new PriorityQueue<>(nextDayQueueSource);
        Time currentTime = new Time(currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE));
        while (!newCurrentDayDisplayQueue.isEmpty()
                && newCurrentDayDisplayQueue.peek().getTime().compareTo(currentTime) < 0) {
            newCurrentDayDisplayQueue.poll();
        }
        currentDayDeparturesQueue = newCurrentDayDisplayQueue;
        nextDayDeparturesQueue = newNextDayDisplayQueue;
    }
}