# MyCycle – Menstrual Cycle Tracking App

## 1. Introduction

MyCycle is an Android application designed to help users track their menstrual cycles, periods, symptoms and moods in a simple and user-friendly way.

The application provides users with a convenient way to record their cycle information, view their cycle history and receive estimated information about upcoming periods.

MyCycle was developed as an Android prototype using Kotlin and Android Studio. The application also demonstrates the use of a RESTful API, database storage, authentication, offline functionality, automated testing and GitHub version control.

**Important:** Cycle predictions provided by MyCycle are estimates based on previously recorded information. They are not medically accurate predictions and should not be used as contraception or as a replacement for professional medical advice.



## 2. Purpose of the Application

The purpose of MyCycle is to provide a simple menstrual cycle tracking solution that allows users to:

- Register and log into the application.
- Record their period start and end dates.
- View their cycle information using a calendar.
- Track symptoms.
- Record their mood.
- View cycle history and statistics.
- Receive reminders and notifications.
- Manage application settings.
- Store information securely.
- Use the application when offline and synchronise information when an internet connection is available.

---

## 3. Main Features

### User Registration and Login

Users can create an account and log into the MyCycle application.

The authentication functionality provides:

- User registration
- User login
- Password protection
- Logout
- Validation of user input
- Error handling for invalid login information



### Period Tracking

Users can record their menstrual periods by entering:

- Period start date
- Period end date

The recorded information can be used to calculate cycle information and provide estimated future cycle dates.



### Cycle Calendar

The calendar allows users to view their cycle information in a visual format.

It can display:

- Previous period dates
- Current cycle information
- Estimated future period dates
- Cycle days



### Symptoms and Mood Tracking

Users can record common symptoms such as:

- Cramps
- Headaches
- Bloating
- Fatigue
- Back pain
- Breast tenderness

Users can also record their mood and add notes to their journal entries.

---

### Settings

The settings section allows users to manage application preferences.

Settings include options such as:

- Profile information
- Notifications
- Reminders
- Language preferences
- Privacy options
- Logout

MyCycle is designed to support English, isiZulu and Sepedi.



### Offline Tracking

MyCycle supports offline tracking through local storage.

When an internet connection is unavailable, information can be stored locally on the device. When the connection becomes available again, the application can synchronise the stored information with the online service.



## 4. Technologies Used

The following technologies were used to develop MyCycle:

- Kotlin
- Android Studio
- Jetpack Compose
- Retrofit
- RESTful API
- ASP.NET Core Web API
- Entity Framework Core
- SQL Server
- Room Database
- GitHub
- GitHub Actions



## 5. System Architecture

The application uses an Android frontend that communicates with a RESTful API.

text

Android Application
        |
        | Retrofit
        v
    RESTful API
        |
        | Entity Framework Core
        v
    SQL Server Database
