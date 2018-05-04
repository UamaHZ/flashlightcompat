# FlashlightCompat

[![](https://jitpack.io/v/UamaHZ/flashlightcompat.svg)](https://jitpack.io/#UamaHZ/flashlightcompat)

闪光灯开关兼容工具类，在 6.0 及以上的系统中通过 `android.hardware.camera2.CameraManager` 实现，在 6.0 以下的系统中通过 `android.hardware.Camera` 实现。

## 下载

在 **project** 的 `build.gradle` 文件中增加如下配置。

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

在 **module** 的 `build.gralde` 文件中增加如下依赖。

```
implementation 'com.github.UamaHZ:flashlightcompat:0.1'
```

## 使用

`FlashlightCompat` 中包含三个方法，分别用于打开、关闭和切换状态。

### Java

打开：

```java
FlashlightCompat.INSTANCE.turnOn(context);
```

关闭：

```java
FlashlightCompat.INSTANCE.turnOff(context);
```

切换状态：

```java
FlashlightCompat.INSTANCE.turn(context, true);
FlashlightCompat.INSTANCE.turn(context, false);
```

### Kotlin

打开：

```kotlin
FlashlightCompat.turnOn(context)
```

关闭：

```kotlin
FlashlightCompat.turnOn(context)
```

切换状态：

```kotlin
FlashlightCompat.turn(this, true)
FlashlightCompat.turn(this, false)
```