# Wroclaw Public Transport Announcer Binding

This binding uses data from MPK Wroclaw for providing information on buses' and trams' departures from any stop in Wroclaw.

To use announcing feature, you first need to [register and get your API token](http://www.voicerss.org/personel) for Voice RSS and define a rule.

## Supported Things

There is exactly one supported thing type, which represents a list of soonest departures.
It has the `departures` id.
Of course, you can add multiple Things, e.g. for displaying departures from different stops.

## Discovery

Closest bus stop can't be autodiscovered based on system location.
You'll have complete default configuration with stopName.

## Binding Configuration

The binding has no configuration options, all configuration is done at Thing level.

## Thing Configuration

The thing has only one configuration parameter:

| Parameter | Description                                                 |
|-----------|-------------------------------------------------------------|
| stopName  | Name of the stop from which the timetable will be displayed |

Parameter is case insensitive, but apart from that entered name should be accurate.

## Channels

The information on soonest departures that is retrieved is available as these channels:

| Channel ID           | Item Type            | Description                          |
|-----------------     |----------------------|--------------------------------------|
| firstDeparture       | String               | First of the soones departures       |
| secondDeparture      | String               | Second of the soones departures      |
| thirdDeparture       | String               | Third of the soones departures       |
| fourthDeparture      | String               | Fourth of the soones departures      |
| fifthDeparture       | String               | Fifth of the soones departures       |
| sixthDeparture       | String               | Sixrh of the soones departures       |
| seventhDeparture     | String               | Seventh of the soones departures     |
| eigthDeparture       | String               | Eigth of the soones departures       |
| ninthDeparture       | String               | Ninth of the soones departures       |
| tenthDeparture       | String               | Tenth of the soones departures       |
| eleventhDeparture    | String               | Eleventh of the soones departures    |
| twelfthDeparture     | String               | Twelfth of the soones departures     |
| thirteenthDeparture  | String               | Thirteenth of the soones departures  |
| fourteenthDeparture  | String               | Fourteenth of the soones departures  |
| fifteenthDeparture   | String               | Fifteenth of the soones departures   |
| sixteenthDeparture   | String               | Sixteenth of the soones departures   |
| seventeenthDeparture | String               | Seventeenth of the soones departures |
| eighteenthDeparture  | String               | Eighteenth of the soones departures  |
| nineteenthDeparture  | String               | Nineteenth of the soones departures  |
| twentiethDeparture   | String               | Twentieth of the soones departures   |

## Full Example

wroclawpublictransportannouncer.things:

```java
Thing wroclawpublictransportannouncer:departures:<stopName> [ stopName="<stopName>" ]
```

wroclawpublictransportannouncer.items:

```java
String firstDeparture "1" {channel="wroclawpublictransportannouncer:departures:<stopName>:firstDeparture"}
String secondDeparture "2" {channel="wroclawpublictransportannouncer:departures:<stopName>:secondDeparture"}
String thirdDeparture "3" {channel="wroclawpublictransportannouncer:departures:<stopName>:thirdDeparture"}
String fourthDeparture "4" {channel="wroclawpublictransportannouncer:departures:<stopName>:fourthDeparture"}
String fifthDeparture "5" {channel="wroclawpublictransportannouncer:departures:<stopName>:fifthDeparture"}
String sixthDeparture "6" {channel="wroclawpublictransportannouncer:departures:<stopName>:sixthDeparture"}
String seventhDeparture "7" {channel="wroclawpublictransportannouncer:departures:<stopName>:seventhDeparture"}
String eightDeparture "8" {channel="wroclawpublictransportannouncer:departures:<stopName>:eightDeparture"}
String ninthDeparture "9" {channel="wroclawpublictransportannouncer:departures:<stopName>:ninthDeparture"}
String tenthDeparture "10" {channel="wroclawpublictransportannouncer:departures:<stopName>:tenthDeparture"}
String eleventhDeparture "11" {channel="wroclawpublictransportannouncer:departures:<stopName>:eleventhDeparture"}
String twelfthDeparture "12" {channel="wroclawpublictransportannouncer:departures:<stopName>:twelfthDeparture"}
String thirteenthDeparture "13" {channel="wroclawpublictransportannouncer:departures:<stopName>:thirteenthDeparture"}
String fourteenthDeparture "14" {channel="wroclawpublictransportannouncer:departures:<stopName>:fourteenthDeparture"}
String fifteenthDeparture "15" {channel="wroclawpublictransportannouncer:departures:<stopName>:fifteenthDeparture"}
String sixteenthDeparture "16" {channel="wroclawpublictransportannouncer:departures:<stopName>:sixteenthDeparture"}
String seventeenthDeparture "17" {channel="wroclawpublictransportannouncer:departures:<stopName>:seventeenthDeparture"}
String eighteenthDeparture "18" {channel="wroclawpublictransportannouncer:departures:<stopName>:eighteenthDeparture"}
String nineteenthDeparture "19" {channel="wroclawpublictransportannouncer:departures:<stopName>:nineteenthDeparture"}
String twentiethDeparture "20" {channel="wroclawpublictransportannouncer:departures:<stopName>:twentiethDeparture"}
```

wroclawpublictransportannouncer.sitemap:

```perl
sitemap wroclawpublictransportannouncer label="Wroclaw Public Transport Announcer" {
    Frame label="<stopName>" {
        Text item=firstDeparture icon="none"
        Text item=secondDeparture icon="none"
        Text item=thirdDeparture icon="none"
        Text item=fourthDeparture icon="none"
        Text item=fifthDeparture icon="none"
        Text item=sixthDeparture icon="none"
        Text item=seventhDeparture icon="none"
        Text item=eightDeparture icon="none"
        Text item=ninthDeparture icon="none"
        Text item=tenthDeparture icon="none"
        Text item=eleventhDeparture icon="none"
        Text item=twelfthDeparture icon="none"
        Text item=thirteenthDeparture icon="none"
        Text item=fourteenthDeparture icon="none"
        Text item=fifteenthDeparture icon="none"
        Text item=sixteenthDeparture icon="none"
        Text item=seventeenthDeparture icon="none"
        Text item=eighteenthDeparture icon="none"
        Text item=nineteenthDeparture icon="none"
        Text item=twentiethDeparture icon="none"
    }
}
```

wroclawpublictransportannouncer.rules:

```java
rule "Announce departures"
when
    Time cron "0 0/5 * * * ?"
then
{
    var announcement = (firstDeparture.state.toString + ", " +
        secondDeparture.state.toString + ", " +
        thirdDeparture.state.toString + ", " +
        fourthDeparture.state.toString + ", " +
        fifthDeparture.state.toString + ", " +
        sixthDeparture.state.toString + ", " +
        seventhDeparture.state.toString + ", " +
        eightDeparture.state.toString + ", " +
        ninthDeparture.state.toString + ", " +
        tenthDeparture.state.toString + ", " +
        eleventhDeparture.state.toString + ", " +
        twelfthDeparture.state.toString + ", " +
        thirteenthDeparture.state.toString + ", " +
        fourteenthDeparture.state.toString + ", " +
        fifteenthDeparture.state.toString + ", " +
        sixteenthDeparture.state.toString + ", " +
        seventeenthDeparture.state.toString + ", " +
        eighteenthDeparture.state.toString + ", " +
        nineteenthDeparture.state.toString + ", " +
        twentiethDeparture.state.toString).split(", ").iterator
    say("Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + "." +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ". " +
        "Linia " + announcement.next + ". Kierunek " + announcement.next + ". Odjazd za " + announcement.next + ".")
}
end
```

voicerss.cfg:

```java
apiKey=<apiKey>
```