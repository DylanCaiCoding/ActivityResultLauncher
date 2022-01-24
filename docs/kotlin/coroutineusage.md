# 协程用法

从 `1.1.0` 版本开始支持了协程用法，在[创建 Launcher 对象](/kotlin/basicusage?id=第一步，创建对应功能的启动器对象)后，可在协程作用域内调用 `launchForResult()` 方法得到返回的结果，或者调用 `launchForFlow()` 得到数据流进行流式编程。

#### 替代 startActivityForResult()

跳转 activity 得到回调结果：

```kotlin
val activityResult = startActivityLauncher.launchForResult<InputTextActivity>(KEY_NAME to "nickname")
if (activityResult.resultCode == RESULT_OK) {
  val value = activityResult.data?.getStringExtra(KEY_VALUE)
  // 处理回调结果
}
```

也可以通过 intent 进行跳转：

```kotlin
val activityResult = startActivityLauncher.launchForResult(intent)
if (activityResult.resultCode == RESULT_OK) {
  // 处理回调结果
}
```

#### 拍照预览

调用系统相册拍照后返回 Bitmap，仅仅用作展示。

```kotlin
val bitmap = takePicturePreviewLauncher.launchForResult()
```

#### 拍照

调用系统相机拍照，保存到应用缓存目录：

```kotlin
val uri = takePictureLauncher.launchForResult() // 上传或取消等操作后建议把缓存文件删除
```

保存到系统相册：

```kotlin
val uri = takePictureLauncher.launchForMediaImageResult() 
```

#### 录像

调用系统相机录像，保存到应用缓存目录：

```kotlin
val uri = takeVideoLauncher.launchForResult() // 上传或取消等操作后建议把缓存文件删除
```

保存到系统相册：

```kotlin
val uri = takeVideoLauncher.launchForMediaVideoResult()
```

#### 选择单个图片或视频

有 `PickContentLauncher` 和 `GetContentLauncher` 可供选择，对应 Intent 的 action 分别是 `Intent.ACTION_PICK` 和 `Intent.ACTION_GET_CONTENT`。官方建议用 `Intent.ACTION_GET_CONTENT`，但是会跳转一个 Material Design 的选择文件页面，比较有割裂感通常不符合需求，而 `Intent.ACTION_PICK` 才会跳转相册页面。可以两个都试一下再做选择。

选择图片：

```kotlin
val uri = pickContentLauncher.launchForImageResult()
```

选择视频：

```kotlin
val uri = pickContentLauncher.launchForVideoResult()
```

#### 选择多个图片或视频

只有 `GetMultipleContentsLauncher` 可以选择，对应 Intent 的 action 是 `Intent.ACTION_GET_CONTENT`。`Intent.ACTION_PICK` 不支持多选。

选择图片：

```kotlin
val uris = getMultipleContentsLauncher.launchForImageResult()
```

选择视频：

```kotlin
val uris = getMultipleContentsLauncher.launchForVideoResult()
```

#### 裁剪图片

```kotlin
val uri = cropPictureLauncher.launchForResult(inputUri)
```

#### 请求单个权限

```kotlin
requestPermissionLauncher.launchForFlow(Manifest.permission.ACCESS_FINE_LOCATION,
  onDenied = { settingsLauncher ->
    // 拒绝且不再询问，可引导用户到设置里授权该权限  
  },
  onExplainRequest = {
    // （可选）拒绝了一次，可弹框解释为什么要获取该权限
  })
  .collect {
     // 已同意
  }
```

#### 请求多个权限

```kotlin
requestMultiplePermissionsLauncher.launchForFlow(
  Manifest.permission.ACCESS_FINE_LOCATION,
  Manifest.permission.READ_EXTERNAL_STORAGE,
  onDenied = { deniedList, settingsLauncher ->
    // 拒绝且不再询问，可引导用户到设置里授权该权限
  },
  onExplainRequest = { deniedList ->
    // （可选）拒绝了一次，可弹框解释为什么要获取该权限
  })
  .collect {
     // 已全部同意
  }
```

#### 打开系统设置的 App 详情页

```kotlin
appDetailsSettingsLauncher.launchForResult()
```

#### 打开蓝牙

```kotlin
if (enableBluetoothLauncher.launchForResult()) {
  // 已打开蓝牙
}
```

#### 打开定位

```kotlin
if (enableLocationLauncher.launchForResult()) {
  // 已开启定位
}
```

#### 创建文档

```kotlin
val uri = createDocumentLauncher.launchForResult(filename)
```

#### 打开单个文档

```kotlin
val uri = openDocumentLauncher.launchForResult("application/*")
```

#### 打开多个文档

```kotlin
val uris = openMultipleDocumentsLauncher.launchForNonEmptyResult("application/*")
```

#### 访问目录内容

```kotlin
val uri = openDocumentTreeLauncher.launchForResult()
```

#### 选择联系人

```kotlin
val uri = pickContactLauncher.launchForResult()
```

#### 替代 startIntentSender()

```kotlin
val activityResult = startIntentSenderLauncher.launchForResult(intentSender, fillInIntent, flagsValues, flagsMask) 
if (activityResult.resultCode == RESULT_OK) {
  // 处理回调结果
}
```