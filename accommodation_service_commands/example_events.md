# Hotel
**Add hotel**
```json
{
  "source": "testSource",
  "hotelName": "Radisson Hotel",
  "city": "Gdańsk",
  "country": "Poland",
  "stars": 5,
  "photo": "url",
  "rooms": [
    {
      "capacity": 4, 
      "name": "Pokój 4-osobowy", 
      "features": "klimatyzacja|TV|telefon", 
      "numberOfRooms": 20
    },
    {
      "capacity": 2,
      "name": "Pokój 2-osobowy",
      "features": "klimatyzacja|telefon",
      "numberOfRooms": 10
    }
  ]
}
```

**Delete hotel**
```json
{
  "source": "testSource",
  "hotelId": 1
}
```

# Room
**Add room**
```json
{
  "source": "testSource",
  "roomNumber": "A201",
  "hotelId": 2,
  "price": 215.50,
  "capacity": 4
}
```

**Delete room**
```json
{
  "source": "testSource",
  "roomId": 1
}
```

# Reservations
**Reserve room** _Not implemented properly yet_
```json
{
  
}
```

**Confirm reservation**
```json
{
  
}
```

**Cancel reservation**
```json

```