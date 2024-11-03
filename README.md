# Media Carousel App

![License](https://img.shields.io/github/license/your-repo-name/media-carousel-app)  
![Build Status](https://img.shields.io/github/actions/workflow/status/your-repo-name/ci.yml)

## 🚀 Overview
The Media Carousel App is an Android application designed to display media items, grouped by types and organized into separate carousels. This app is built using Kotlin, Jetpack Compose, Hilt for dependency injection, Navigation Graph for seamless navigation, Paging 3 for efficient data loading, and Retrofit for network requests. The project adheres to MVVM and Clean Architecture principles to ensure testability, scalability, and maintainability.

## 📋 Table of Contents
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Features](#features)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

## 🏗 Architecture
This app is structured following **MVVM** (Model-View-ViewModel) and **Clean Architecture** principles.

### MVVM Pattern
- **Model:** Manages the data, including network requests and data processing.
- **View:** The UI layer, built using Jetpack Compose, displays data and handles user interactions.
- **ViewModel:** Acts as a bridge between the Model and the View, handling business logic and UI state management.

### Clean Architecture
The app is divided into three main layers:
- **Presentation Layer:** Contains all UI elements and ViewModels.
- **Domain Layer:** Houses business logic and use cases.
- **Data Layer:** Manages data sources (e.g., network and database), implementing repository patterns.

## 📁 Project Structure
The project is organized into two primary modules:
- **Library Module:** Contains network and pagination logic, isolated for reusability.
- **App Module:** Responsible for UI and presentation logic, retrieving data from the library module and displaying it.


## ✨ Features
- **Carousel Display**: Media items fetched from an API are grouped by `media_type` and displayed in separate carousels.
- **Modularized Data Logic**: Network and pagination logic are encapsulated in a library module.
- **MVVM + Clean Architecture**: Ensures a clear separation of concerns for better scalability and testability.
- **Dependency Injection**: Hilt manages dependency injection throughout the project.
- **Paging and Navigation**: Efficiently handles large data sets with Paging 3 and provides seamless transitions using Navigation Graph.

## 📦 Dependencies
- **Kotlin**: Main programming language.
- **Jetpack Compose**: For UI development.
- **Hilt**: For dependency injection.
- **Navigation Component**: Manages navigation and back stack.
- **Paging 3**: Efficiently loads and displays paginated data.
- **Retrofit**: For API requests and network handling.

## 🤝 Contributing
Contributions are welcome! Please open an issue or submit a pull request to propose changes.

## 📄 License
This project is licensed under the MIT License.
