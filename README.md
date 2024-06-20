<div align= center>
  
# PlantCarePal [Mobile Development Section]

</div>

> This repository is the **__Mobile Development__** part of the PlantCarePal application development for the full Repo, you can check it here [https://github.com/debb88/PlantCarePal]

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Usage](#usage)
- [Contributing](#contributing)

## Overview

The Plant Disease Detection App is an Android application designed to help users identify plant diseases using their camera. In addition to disease detection, the app offers user authentication, a community forum, and educational materials to help users better understand plant care and disease prevention.

## Features

- **Plant Disease Detection**: Use your camera to take a picture of a plant and get instant disease detection results.
- **User Authentication**: Login and registration system for users to create and manage their accounts for Forum and Detection History purposes.
- **Detection History**: Detection history for users to see their previous detection.
- **Community Forum**: A platform for users to communicate, share experiences, and seek advice from other users.
- **Guidance and Educational Material**: Comprehensive reading materials on plant care, disease prevention, and treatment.

## Screenshots

<div align="center">

| Splash Screen | Opening Page | Register Page | Login Page | Home Page | Post Detect Page | Analyze Page |
|---------|---------|---------|---------|---------|---------|---------|
| ![SplashScreen](https://github.com/Nox-Matt/PlantAppApplication/assets/110184326/aad24559-fadd-4ad6-966c-12f3e877ecc2)  | ![Opening Page](https://github.com/Nox-Matt/PlantAppApplication/assets/110184326/11ad2fa9-f7c1-48d6-a7db-3e8d56f0e765) | ![register](https://github.com/debb88/PlantCarePal/assets/120155046/9d59dfd1-cde2-40cd-9070-d6384fd56dba)  | ![login](https://github.com/debb88/PlantCarePal/assets/120155046/493f58db-af05-4e2c-93a1-732d1fc679b6)  | ![homepage](https://github.com/debb88/PlantCarePal/assets/120155046/8be5e38c-bc27-4cfa-b841-db61f6095812)  | ![post-photo](https://github.com/debb88/PlantCarePal/assets/120155046/b3c65374-1a8b-48a6-93de-f23bfdc70abe)  | ![analyze-page](https://github.com/debb88/PlantCarePal/assets/120155046/e5a086d8-37a0-4c51-b58e-a2daa359ca8f)  |

| Analyze Result Page | History Page | Discussion Page | Discussion Details Page | Post Question Page | Guides Page | Guide Details Page |
|---------|---------|---------|---------|---------|---------|---------|
| ![analyze-result](https://github.com/debb88/PlantCarePal/assets/120155046/7f109343-aebc-4be0-9eef-d4d967830d5d) | ![history](https://github.com/debb88/PlantCarePal/assets/120155046/2dc7078d-ca96-4daa-8252-807998b97412) | ![discussion-forum](https://github.com/debb88/PlantCarePal/assets/120155046/4cb5df0f-d9cd-47d8-a210-935ab4ba7088) | ![discuss-detail](https://github.com/debb88/PlantCarePal/assets/120155046/ffa13d01-2d29-4544-8e6c-60346462ab1e) | ![post-question](https://github.com/debb88/PlantCarePal/assets/120155046/f11602da-3890-443e-bfbb-1a3f48b4fa95) | ![guides](https://github.com/debb88/PlantCarePal/assets/120155046/c6d92873-3439-4ddf-b9a5-58132c4016c8) | ![guide-details](https://github.com/debb88/PlantCarePal/assets/120155046/2b13541c-a50c-48fc-b1bd-dc3567f8835d) |

</div>

## Installation

To get a local copy up and running, follow these simple steps:

1. **Clone the repo**
   ```sh
   git clone https://github.com/your_username/PlantDiseaseDetectionApp.git
   ```

2. **Open in Android Studio**
   - Open Android Studio and select "Open an existing project"
   - Navigate to the cloned repository folder and open it
3. **Build the project**
   - Wait for Gradle to build the project. This may take a few minutes.
   - Resolve any dependencies or updates if prompted.
4. **Run the application**
   - Connect an Android device or start an emulator.
   - Click the 'Run' button in Android Studio.
  
## Dependencies

These are some dependencies that we use for our application:

```sh
implementation(libs.androidx.viewpager2)
implementation(libs.androidx.datastore.preferences)
implementation(libs.glide)
implementation(libs.retrofit)
implementation(libs.retrofit2.converter.gson)
implementation(libs.logging.interceptor)
implementation(libs.androidx.exifinterface)
implementation(libs.styleabletoast)
implementation(libs.androidx.core.splashscreen)
implementation(libs.androidx.constraintlayout)
implementation(libs.androidx.lifecycle.livedata.ktx)
implementation(libs.androidx.lifecycle.viewmodel.ktx)
implementation(libs.androidx.navigation.fragment.ktx)
```

### Dependencies Usage In Project 

```sh
implementation(libs.androidx.viewpager2)
```


_Viewpager is used to cycle around fragment, mostly used for our guidance and result of plant detection_


```sh
implementation(libs.androidx.datastore.preferences)
```


_Datastore are used for session implementation for user convince, so they don't have to login everytime because their session is stored inside datastore_


```sh
implementation(libs.glide)
```


_Mostly used for showing image assets or drawable inside our project_


```sh
implementation(libs.retrofit)
```


_Retrofit are used to established connection between our API service and our application to be able to communicate for request, fetching, and gaining response._


```sh
implementation(libs.retrofit2.converter.gson)
```


_This dependencie are used to convert response from retrofit body into GSON so it can then be translated and used into our application._


```sh
implementation(libs.logging.interceptor)
```


_Interceptor Logging are used for simple security measure so the application connection to our API service are not logged by other unwanted parites._


```sh
implementation(libs.androidx.exifinterface)
```


_ExifInterface is a part of AndroidX that allows reading and writing of Exif metadata from JPEG and RAW image files. It is useful for handling image metadata, such as orientation._


```sh
implementation(libs.styleabletoast)
```


_StyleableToast is a customizable and easy-to-use library for creating stylized Toast messages in Android. It is used to show user-friendly messages throughout the app._


```sh
implementation(libs.androidx.core.splashscreen)
```


_This library provides a consistent way to implement splash screens in Android apps. It helps in displaying a splash screen while the app is loading._


```sh
implementation(libs.androidx.constraintlayout)
```


_ConstraintLayout is a powerful layout manager for Android that allows creating complex layouts with a flat view hierarchy. It is used for designing responsive UI layouts._


```sh
implementation(libs.androidx.lifecycle.livedata.ktx)
```


_LiveData is a lifecycle-aware data holder class that can be observed. The ktx version provides Kotlin extensions that make using LiveData more concise and idiomatic._


```sh
implementation(libs.androidx.lifecycle.viewmodel.ktx)
```


_ViewModel is a class that is responsible for preparing and managing the data for an Activity or a Fragment. The ktx version provides Kotlin extensions for better integration with Kotlin features._


```sh
implementation(libs.androidx.navigation.fragment.ktx)
```


_This library provides navigation components for handling fragment transactions and back stack management. The ktx version provides Kotlin extensions for easier and more concise navigation code._


## Usage

1. Login/Registration
   - Open the app and register for a new account or login with existing credentials.

2. Detect Plant Disease
   - Navigate to the disease detection feature.
   - Use the camera to take a picture of the plant.
   - View the detection results and suggested actions.

3. Participate in the Forum
   - Browse through the forum to read discussions.
   - Post your questions or share your experiences.

4. Read Guidance Materials
   - Access the guidance section for educational materials on plant care.

`Our usage are done with our API service, you can see the API service here` [https://naufalliandra.github.io/pcp-api-doc/#api-_]

## Contributing

You can contribute for this project if you want, such as cleaning the code or optimze it, give it a better design, or add more feature you can do it by:
1. Fork the Project
2. Create your Feature Branch ```git checkout -b feature/your_feature```
3. Commit your Changes ```git commit -m 'Add YOURFEATURENAME'```
4. Push to the Branch ```git push origin feature/your_feature```
5. Open a Pull Request

