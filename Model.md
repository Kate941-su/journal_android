```mermaid
---
title: Journal Data
---
classDiagram
    class Journal{
        + String id
        + LocalDate date
        + String title
        + String content
        + List<Image> pictures
        + Location location
        + List<Tag> tags
        + List<Person> persons
    }
```

# example to show diagram

    <!-- note "From Duck till Zebra"
    Animal <|-- Duck
    note for Duck "can fly\ncan swim\ncan dive\ncan help in debugging"
    Animal <|-- Fish
    Animal <|-- Zebra
    Animal : +int age
    Animal : +String gender
    Animal: +isMammal()
    Animal: +mate() -->
