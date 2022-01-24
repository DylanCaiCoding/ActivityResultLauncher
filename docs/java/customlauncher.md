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

如果不需要任何输入，可以用 `Void` 作为类型。如果是多个输入参数，需要再定义一个类将参数进行整合。

比如我们需要能自定义输入页面的标题、提示语句、默认值：

```java
public class InputTextRequest {
  @NonNull
  private final String title;
  @NonNull
  private final String hint;
  @Nullable
  private final String value;

  public InputTextRequest(@NonNull String title, @NonNull String hint, @Nullable String value) {
    this.title = title;
    this.hint= hint;
    this.value = value;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  @NonNull
  public String getHint() {
    return hint;
  }

  @Nullable
  public String getValue() {
    return value;
  }
}

```

确定了输入类和输出类就能编写协议类，下面代码应该很好懂就不多讲解了。

```java
public class InputTextResultContract extends ActivityResultContract<InputTextRequest, String> {
  public static final String KEY_TITLE = "title";
  public static final String KEY_HINT = "hint";
  public static final String KEY_VALUE = "value";

  @NotNull
  @Override
  public Intent createIntent(@NonNull Context context, InputTextRequest input) {
    Intent intent = new Intent(context, InputTextActivity.class);
    intent.putExtra(KEY_TITLE, input.getTitle());
    intent.putExtra(KEY_HINT, input.getHint());
    intent.putExtra(KEY_VALUE, input.getValue());
    return intent;
  }

  @Override
  public String parseResult(int resultCode, @Nullable Intent intent) {
    if (resultCode == Activity.RESULT_OK && intent != null) {
      return intent.getStringExtra(KEY_VALUE);
    }
    return null;
  }
}
```

### 2. 实现启动器类

继承 `BaseActivityResultLauncher`，泛型与协议类一致，在构造方法直接传入对应的协议类对象。

```java
public class InputTextLauncher extends BaseActivityResultLauncher<InputTextRequest, String> {

  public InputTextLauncher(@NonNull ActivityResultCaller caller) {
    super(caller, new InputTextResultContract());
  }
}
```

实现了启动器类就能使用了。

```java
InputTextRequest inputTextRequest = new InputTextRequest("Nickname", "Input the nickname", null);
inputTextLauncher.launch(inputTextRequest, (value) -> {
  if (value != null) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
  }
});
```

我们还能对用法进行优化，比如上面每次调用 `launch()` 方法都要创建个对象有点繁琐，可以在启动器类写个重载方法把创建对象的过程去掉。比如：

```java
public class InputTextLauncher extends BaseActivityResultLauncher<InputTextRequest, String> {

  public InputTextLauncher(@NonNull ActivityResultCaller caller) {
    super(caller, new InputTextResultContract());
  }

  public void launch(@NonNull String title, @NonNull String hint, @NonNull ActivityResultCallback<String> callback) {
    launch(title, hint, null, callback);
  }

  public void launch(@NonNull String title, @NonNull String hint, 
                     @Nullable String value, @NonNull ActivityResultCallback<String> callback) {
    launch(new InputTextRequest(title, hint, value), callback);
  }
}
```

这样代码就更简洁了。

```java
inputTextLauncher.launch("Nickname", "Input the nickname", (value) -> {
  if (value != null) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
  }
});
```

还能将多个启动器进行整合，比如获取权限后再执行操作，可参考 [GetContentLauncher](https://github.com/DylanCaiCoding/ActivityResultLauncher/blob/master/library/src/main/java/com/dylanc/activityresult/launcher/GetContentLauncher.kt)。