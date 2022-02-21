# ActivityResultLauncher

English | [中文](https://github.com/DylanCaiCoding/ActivityResultLauncher/blob/master/README_CN.md)

[![](https://www.jitpack.io/v/DylanCaiCoding/ActivityResultLauncher.svg)](https://www.jitpack.io/#DylanCaiCoding/ActivityResultLauncher) [![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/ActivityResultLauncher/blob/master/LICENSE)

[Activity Result API](https://developer.android.com/training/basics/intents/result) is an official tool used to replace the method of  `startActivityForResult()` and `onActivityResult()`. But the API is not very friendly to use, **so this library helps you use the Activity Result API in as many scenarios as possible with easier to use code, it also supports encapsulating the function that projects use with `startactivityForResult()`**。

## Feature

- Replace `startActivityForResult()` gracefully
- Support for the usage of Kotlin and Java
- Support for the usage of coroutine
- Support for taking picture (Adapted to Android 10)
- Support for taking video (Adapted to Android 10)
- Support for selecting pictures or videos
- Support for cropping pictures (Adapted to Android11)
- Support for requesting permission
- Support for enabling Bluetooth
- Support for enabling positioning
- Support for the use of storage access frameworks
- Support for selecting contacts

## Demo

<img src="image/screenshot-demo.jpg" alt="screenshot" width="400"/>

Click or scan the QR code to download

[![QR code](image/qr-code.png)](https://www.pgyer.com/activityresultlauncher)

## Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

Add dependencies：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding:ActivityResultLauncher:1.1.2'
}
```

## Wiki

#### Kotlin usage

- [Basic usage](https://dylancaicoding.github.io/ActivityResultLauncher/#/kotlin/basicusage)

- [Coroutine usage](https://dylancaicoding.github.io/ActivityResultLauncher/#/kotlin/coroutineusage)

- [Custom launcher](https://dylancaicoding.github.io/ActivityResultLauncher/#/kotlin/customlauncher)

#### Java usage

- [Basic usage](https://dylancaicoding.github.io/ActivityResultLauncher/#/java/basicusage)

- [Custom launcher](https://dylancaicoding.github.io/ActivityResultLauncher/#/java/customlauncher)

#### Others

- [Q&A](https://dylancaicoding.github.io/ActivityResultLauncher/#/others/q&a)

## Change log

[Releases](https://github.com/DylanCaiCoding/ActivityResultLauncher/releases)

## Author's other libraries

| Library                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Longan](https://github.com/DylanCaiCoding/Longan)           | A collection of Kotlin utils                                 |
| [LoadingStateView](https://github.com/DylanCaiCoding/LoadingStateView) | Decoupling the code of toolbar or loading status view.       |
| [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX) | The most comprehensive utils of ViewBinding.                 |
| [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX)       | Easier to use the MMKV.                                      |

## License

```
Copyright (C) 2021. Dylan Cai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
