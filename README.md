# Journal

## Tech Stack
- User interface => Jetpack Compose
- State management => Flow
- Asynchronous programming => coroutione
- Architecture => MVVM
- Persistent data => Room
- Deep injection => Dagger Hilt
- Screen transition => Navigation graph

### Jetpack Compose
Creating an Android application UI decrelatively. My first impression, I have experienced
to create Flutter applications that has a concept that all of things are widget.

On the other hand, Jetpack uses `@Component` annotation to define a UI component such as

```kotlin
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (countDownValue) {
            // When the value is between 1 to 10, show the countDownValue 
            in 1..10 -> {
                CountdownText("Countdown: $countDownValue")
            }
            // Else it's already new year, so its time to wish your friends! 
            else -> {
                CountdownText("HAPPY NEW YEAR!!!!", Color.Magenta)
            }
        }
    }
```

I felt a bit complicated because I learn Flutter at first and why it needs traling clousure.
But after a day, I could understand that there are some components in the closure and counstructor 
arguments are attributes of each componet.




