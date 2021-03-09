# PilotLog

A simple logbook app to log flight times

[Demo video](https://user-images.githubusercontent.com/2794581/110432635-4aeef480-80a7-11eb-9928-ef0e87839016.mp4)

## Features

- [x] Date selection
- [x] Name, operating capacity
- [x] Aircraft type and registration
- [x] Departure and arrival name & ICAO
- [x] Departure and arrival times
- [x] Dark/Light mode
- [ ] Flying time entry for day, night, instrument, instrument (simulated)
- [ ] Take-off and landing count
- [ ] Pilot remarks
- [ ] Offline JSON export
- [ ] Logbook screen to view entries

# Tech

- Jetpack Datastore and protobuf for persisting draft entries, as well as aircraft and locations for reuse in future log entries
- Jetpack Navigation
- Hilt dependency injection
- Kotlin Coroutines with Flow
- MVVM/MVI
