# 📺 NewsHeadlinesTV

An Android TV application built using **Kotlin**, **Jetpack Compose**, **MVVM**, **Retrofit**, and **Kotlin Coroutines** that fetches and displays the latest news headlines from **NewsAPI**.

This project was developed as part of an Android TV assignment focusing on networking, concurrency, Android TV navigation, and clean architecture.

---

# ✨ Features

- 📺 Android TV Optimized UI
- 📰 Latest News Headlines
- 🖼️ News Images
- 📄 Headline with Summary
- 🔄 Long Press DPAD Down to Refresh
- ⏳ Loading Indicator
- ❌ Network Error Handling
- 🎯 TV Remote Navigation
- ↔️ Horizontal News Browsing
- 🚀 Parallel API Requests
- 🧩 MVVM Architecture
- ⚡ Kotlin Coroutines
- 🏗️ Repository Pattern
- 🌐 Retrofit Networking
- 🖼️ Coil Image Loading

---
# 📸 Screenshots

## Home Screen

<img src="https://github.com/hitanshigajjar1/NewsHeadlinesTV/blob/master/app/src/main/assets/screenshot.jpeg" alt="Home Screen" width="350">

---

# 🎥 Demo

A short demonstration of the application.

<video src="https://github.com/hitanshigajjar1/NewsHeadlinesTV/blob/master/app/src/main/assets/demo.mp4" width="100%" controls></video>
---
# 🛠 Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Retrofit
- Gson Converter
- Kotlin Coroutines
- StateFlow
- Coil
- Material 3

---

# 📂 Project Structure

```
app
│
├── data
│   ├── api
│   │   ├── NewsApi.kt
│   │   └── RetrofitClient.kt
│   │
│   ├── model
│   │   ├── Article.kt
│   │   ├── ArticleDto.kt
│   │   ├── Mapper.kt
│   │   ├── NewsResponse.kt
│   │   └── SourceDto.kt
│   │
│   └── repository
│       ├── NewsRepository.kt
│       └── NewsRepositoryImpl.kt
│
├── ui
│   ├── navigation
│   │   └── NewsNavGraph.kt
│   │
│   ├── screens
│   │   ├── NewsScreen.kt
│   │   └── NewsCard.kt
│   │
│   └── theme
│
├── utils
│   └── Constants.kt
│
├── viewmodel
│   ├── NewsUiState.kt
│   ├── NewsViewModel.kt
│   ├── NewsViewModelFactory.kt
│   └── UiState.kt
│
├── MainActivity.kt
└── NewsApplication.kt
```

---

# 🏛 Architecture

```
Presentation (Compose UI)
        │
        ▼
ViewModel (StateFlow)
        │
        ▼
Repository
        │
        ▼
Retrofit API
        │
        ▼
NewsAPI Server
```

The project follows the **MVVM (Model–View–ViewModel)** architecture for better separation of concerns and maintainability.

---

# 🌐 API

This application uses **NewsAPI**.

Endpoint

```
GET /v2/top-headlines
```

Parameters

- country
- category
- pageSize
- apiKey

---

# 📺 Android TV Features

- TV Optimized Layout
- Horizontal News Browsing
- DPAD Navigation
- Focusable Cards
- Long Press DPAD Down Refresh
- Loading Indicator During Refresh

---

# 📊 Assignment Requirement Status

| Requirement | Status |
|------------|--------|
| News API Integration | ✅ |
| Retrofit Networking | ✅ |
| Headlines Display | ✅ |
| Summary Display | ✅ |
| News Images | ✅ |
| Android TV UI | ✅ |
| Horizontal Navigation | ✅ |
| DPAD Navigation | ✅ |
| Long Press DPAD Down Refresh | ✅ |
| Loading Indicator | ✅ |
| Error Handling | ✅ |
| MVVM Architecture | ✅ |
| Structured Concurrency | ✅ |
| Parallel API Calls | ✅ |
| Expand / Collapse Summary | 🚧 Work In Progress |
| Offline Cache (Room Database) | 🚧 Planned |

---

# 🚀 Setup

## Clone Repository

```bash
git clone https://github.com/hitanshigajjar1/NewsHeadlinesTV.git
```

---

## Open Project

Open the project in **Android Studio**.

---

## Add API Key

Open

```
Constants.kt
```

Replace

```kotlin
const val API_KEY = "YOUR_NEWS_API_KEY"
```

with your own NewsAPI key.

---

## Build

- Sync Gradle
- Build Project
- Run on

  - Android TV Emulator

or

  - Physical Android TV

---

# 📦 Dependencies

- Retrofit
- Gson Converter
- Coil Compose
- Kotlin Coroutines
- Jetpack Compose
- Material3

---

# 🚧 Future Improvements

- Offline Room Database Cache
- Expand / Collapse Summary Animation
- Better Focus Animations
- Search Headlines
- Category Filter
- Pagination Support
- Retry Failed Requests

---

# 👩‍💻 Author

**Hitanshi Gajjar**

---

# 📜 License

This project was created for educational and assignment purposes.
