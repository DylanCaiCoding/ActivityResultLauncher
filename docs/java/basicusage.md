# 基础用法

## 开始使用

### 1. 创建对应功能的 Launcher 对象

在 `ComponentActivity` 或 `Fragment` 创建对应的对象，需要注意创建对象的时机要在 `onStart()` 之前。

例如创建调用通用的启动器：

```java
private final StartActivityLauncher startActivityLauncher = new StartActivityLauncher(this);
```

提供以下默认的启动类：

| 启动器 | 作用 |
| --- | --- |
| [StartActivityLauncher](/java/basicusage?id=替代-startActivityForResult) | 替代 `startActivityForResult()` |
| [StartIntentSenderLauncher](/java/basicusage?id=替代-startIntentSender) | 替代 `startIntentSender()` |
| [TakePicturePreviewLauncher](/java/basicusage?id=拍照预览) | 调用系统相机拍照预览，返回 Bitmap |
| [TakePictureLauncher](/java/basicusage?id=拍照) | 调用系统相机拍照，返回 Uri |
| [TakeVideoLauncher](/java/basicusage?id=录像) | 调用系统相机录像，返回 Uri |
| [PickContentLauncher, GetContentLauncher](/java/basicusage?id=选择单个图片或视频) | 选择单个图片或视频，已适配 Android 10 |
| [GetMultipleContentsLauncher](/java/basicusage?id=选择多个图片或视频) | 选择多个图片或视频，已适配 Android 10 |
| [CropPictureLauncher](/java/basicusage?id=裁剪图片) | 裁剪图片，已适配 Android 11 |
| [RequestPermissionLauncher](/java/basicusage?id=请求单个权限) | 请求单个权限 |
| [RequestMultiplePermissionsLauncher](/java/basicusage?id=请求多个权限) | 请求多个权限 |
| [AppDetailsSettingsLauncher](/java/basicusage?id=打开系统设置的-App-详情页) | 打开系统设置的 App 详情页 |
| [EnableBluetoothLauncher](/java/basicusage?id=打开蓝牙) | 打开蓝牙 |
| [EnableLocationLauncher](/java/basicusage?id=打开定位) | 打开定位 |
| [CreateDocumentLauncher](/java/basicusage?id=创建文档) | 创建文档 |
| [OpenDocumentLauncher](/java/basicusage?id=打开单个文档) | 打开单个文档 |
| [OpenMultipleDocumentsLauncher](/java/basicusage?id=打开多个文档) | 打开多个文档 |
| [OpenDocumentTreeLauncher](/java/basicusage?id=访问目录内容) | 访问目录内容 |
| [PickContactLauncher](/java/basicusage?id=选择联系人) | 选择联系人 |

### 2. 调用对应的 launch 方法

#### 替代 startActivityForResult()

```java
startActivityLauncher.launch(intent, (resultCode, data) -> {
  if (resultCode == RESULT_OK) {
    // 处理回调结果
  }
});
```

#### 替代 startIntentSender()

```java
startIntentSenderLauncher.launch(intentSender, fillInIntent, flagsValues, flagsMask, (resultCode, data) -> {
  if (resultCode == RESULT_OK) {
    // 处理回调结果
  }
});
```

#### 拍照预览

调用系统相册拍照后返回 Bitmap，仅仅用作展示。

```java
takePicturePreviewLauncher.launch((bitmap) -> {
  if (bitmap != null) {
    imageView.setImageBitmap(bitmap);
  }
});
```

#### 拍照

调用系统相机拍照，保存到应用缓存目录：

```java
takePictureLauncher.launch((uri) -> {
  if (uri != null) {
    // 拍照成功，上传或取消等操作后建议把缓存文件删除
  }
});
```

保存到系统相册：

```java
takePictureLauncher.launchForMediaImage ((uri) -> {
  if (uri != null) {
    // 拍照成功
  }
});
```

#### 录像

调用系统相机录像，保存到应用缓存目录：

```java
takeVideoLauncher.launch((uri) -> {
  if (uri != null) {
    // 录像成功，上传或取消等操作后建议把缓存文件删除
});
```

如果需要录像保存到系统相册：

```java
takeVideoLauncher.launchForMediaVideo((uri) -> {
  if (uri != null) {
    // 录像成功
});
```

#### 选择单个图片或视频

有 `PickContentLauncher` 和 `GetContentLauncher` 可供选择，对应 Intent 的 action 分别是 `Intent.ACTION_PICK` 和 `Intent.ACTION_GET_CONTENT`。官方建议用 `Intent.ACTION_GET_CONTENT`，但是会跳转一个 Material Design 的选择文件页面，比较有割裂感通常不符合需求，而 `Intent.ACTION_PICK` 才会跳转相册页面。可以两个都试一下再做选择。

选择图片调用 `launchForImage()`，选择视频调用 `launchForVideo()`。

```java
getContentLauncher.launchForImage(
  (uri) -> {
    if (uri != nul) {
      // 处理 uri
    }
  },
  (settingsLauncher) -> {
    // 拒绝了读取权限且不再询问，可引导用户到设置里授权该权限
  },
  () -> {
    // （可选）拒绝了一次读取权限，可弹框解释为什么要获取该权限
  }
);
```

#### 选择多个图片或视频

只有 `GetMultipleContentsLauncher` 可以选择，对应 Intent 的 action 是 `Intent.ACTION_GET_CONTENT`。`Intent.ACTION_PICK` 不支持多选。

选择图片调用 `launchForImage()`，选择视频调用 `launchForVideo()`。

```java
getMultipleContentsLauncher.launchForImage(
  (uris) -> {
    if (uris.size() > 0) {
      // 处理 uri 列表
    }
  },
  (settingsLauncher) -> {
    // 拒绝了读取权限且不再询问，可引导用户到设置里授权该权限
  },
  () -> {
    // （可选）拒绝了一次读取权限，可弹框解释为什么要获取该权限
  }
);
```

#### 裁剪图片

```java
cropPictureLauncher.launch(inputUri, (uri) -> {
  if (uri != null) {
    // 处理 uri
  }
});
```

#### 请求单个权限

```java
requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION,
  () -> {
    // 已同意
  },
  (settingsLauncher) -> {
    // 拒绝且不再询问，可引导用户到设置里授权该权限
  },
  () -> {
    // （可选）拒绝了一次，可弹框解释为什么要获取该权限
  }
);
```

#### 请求多个权限

```java
requestMultiplePermissionsLauncher.launch(
  Manifest.permission.ACCESS_FINE_LOCATION,
  Manifest.permission.READ_EXTERNAL_STORAGE,
  () -> {
    // 已全部同意
  },
  (list, settingsLauncher) -> {
    // 拒绝且不再询问，可引导用户到设置里授权该权限
  },
  (list) -> {
    // （可选）拒绝了一次，可弹框解释为什么要获取该权限
  }
);
```

#### 打开系统设置的 App 详情页

```java
// appDetailsSettingsLauncher.launch();
appDetailsSettingsLauncher.launch(() -> {
  // 回调逻辑
});
```

#### 打开蓝牙

由于蓝牙功能需要 GPS 定位，除了打开蓝牙，还会需要授权定位权限和确保定位已打开才能正常使用，所以增加以下用法。

```java
enableBluetoothLauncher.launchAndEnableLocation(
  "为保证蓝牙正常使用，请开启定位",  // 已授权权限但未开启定位，会跳转对应设置页面，并吐司该字符串
  (enabled) -> {
    if (enabled) {
      // 已开启了蓝牙，并且授权了位置权限和打开了定位
    }
  },
  (settingsLauncher) -> {
    // 拒绝了位置权限且不再询问，可引导用户到设置里授权该权限
  },
  () -> {
    // （可选）拒绝了一次位置权限，可弹框解释为什么要获取该权限
  }
);
```

#### 打开定位

```java
enableLocationLauncher.launch((enabled) -> {
  if (enabled) {
    // 已开启定位
  }
});
```

#### 创建文档

```java
createDocumentLauncher.launch(filename, (uri) -> {
  if (uri != null) {
    // 处理 uri
  }
});
```

#### 打开单个文档

```java
openDocumentLauncher.launch("application/*", (uri) -> {
  if (uri != null) {
    // 处理 uri
  }
});
```

#### 打开多个文档

```java
openMultipleDocumentsLauncher.launch("application/*", (uris) -> {
  if (uris.size() > 0) {
    // 处理 uri 列表
  }
});
```

#### 访问目录内容

```java
openDocumentTreeLauncher.launch((uri) -> {
  if (uri != null) {
    DocumentFile documentFile = DocumentFile.fromTreeUri(this, uri);
    // 处理文档文件
  }
});
```

#### 选择联系人

```java
pickContactLauncher.launch((uri) -> {
  if (uri != null) {
    // 处理 uri
  }
});
```