# Q&A

## 为什么实现了这么多启动器类？
会用其它的权限库。不过开了混淆会自动移除没有使用的类，这并不是什么问题。如果项目真的是不开混淆，可以考虑手动拷贝需要的类，个人认为至少 [StartActivityLauncher](https://github.com/DylanCaiCoding/ActivityResultLauncher/blob/master/library/src/main/java/com/dylanc/activityresult/launcher/StartActivityLauncher.kt) 是必备的，本库最主要是帮助大家用更优雅的方式实现 `startActivityForResult()` 功能。

## 只有 Uri 怎么上传？

最初拍照和选择图片等操作会返回 uri 和 file，大多数人是用 file 上传文件，所以两个都返回了。后续考虑增加协程的用法时，
原本 Activity Result API 已经有很多默认的协议类，都封装了对应的启动器类。大家可能不会用到所有类，比如权限功能，多数人发现有两个返回值并不好封装，并且 file 不是必要的，所以移除了。那么问题来了，只有 Uri 怎么上传文件呢？这里讲下思路，OkHttp 或 Retrofit 上传 uri，需要用 uri 打开输入流转为字节数组，再用字节数组转为 RequestBody 或 Part。