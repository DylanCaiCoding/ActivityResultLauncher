# 自定义启动器

可以对项目用到 `startActivityForResult()` 的功能进行封装，方便后续的使用。

下面用一个简单的示例来讲解：跳转到输入文字的页面，点击保存按钮后关闭页面，回调输入的字符串。

主要有以下两个步骤：

### 1. 实现协议类

自定义协议，继承 Activity Result API 的 `ActivityResultContract` 类。

`ActivityResultContract` 的作用是：

- 定义输入类和输出类，即输入什么和输出什么；
- 创建 Intent，对应 `startActivityForResult()` 所传入的 `Intent` 对象；
- 解析结果，对应在 `onActivityResult()` 解析数据；

如果不需要任何输入，可以用 `Unit` 作为类型。如果是多个输入参数，需要再定义一个类将参数进行整合。

比如我们需要能自定义输入页面的标题、提示语句、默认值：

```kotlin
class InputTextRequest(
  val title: String,
  val hint: String? = null,
  val value: String? = null
)
```

确定了输入类和输出类就能编写协议类，下面代码应该很好懂就不多讲解了。

```kotlin
class InputTextResultContract : ActivityResultContract<InputTextRequest, String>() {
  override fun createIntent(context: Context, input: InputTextRequest) =
    Intent(context, InputTextActivity::class.java).apply {
      putExtra(KEY_TITLE, input.title)
      putExtra(KEY_HINT, input.hint)
      putExtra(KEY_VALUE, input.value)
    }

  override fun parseResult(resultCode: Int, intent: Intent?): String? =
    if (resultCode == Activity.RESULT_OK) intent?.getStringExtra(KEY_VALUE) else null

  companion object {
    const val KEY_TITLE = "title"
    const val KEY_HINT = "hint"
    const val KEY_VALUE = "value"
  }
}
```

### 2. 实现启动器类

继承 `BaseActivityResultLauncher`，泛型与协议类一致，在构造方法直接传入对应的协议类对象。

```kotlin
class InputTextLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<InputTextRequest, String>(caller, InputTextResultContract()) 
```

实现了启动器类就能使用了。

```kotlin
val inputTextRequest = InputTextRequest(
  title = "Nickname", 
  hint = "Input the nickname"
)
inputTextLauncher.launch(inputTextRequest) {
  if (it != null) {
    toast(it)
  }
}
```

我们还能对用法进行优化，比如上面每次调用 `launch()` 方法都要创建个对象有点繁琐，可以在启动器类写个重载方法把对象去掉。比如：

```kotlin
class InputTextLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<InputTextRequest, String>(caller, InputTextResultContract()) {

  fun launch(title: String, hint: String? = null, value: String? = null) =
    launch(InputTextRequest(title, hint, value), onActivityResult)
}
```

这样代码就更简洁了。

```kotlin
inputTextLauncher.launch(title = "Nickname", hint = "Input the nickname") {
  if (it != null) {
    toast(it)
  }
}
```

还能将多个启动器进行整合，比如获取权限后再执行操作，可参考 [GetContentLauncher](https://github.com/DylanCaiCoding/ActivityResultLauncher/blob/master/library/src/main/java/com/dylanc/activityresult/launcher/GetContentLauncher.kt)。