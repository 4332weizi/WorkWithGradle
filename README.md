# WorkWithGradle
the basic usage of gradle
**[完整gradle文件](app/build.gradle)**
1.使用`buildConfigField`
--
`buildConfigField`可以在`pakagename`包下生成一个`BuildConfig`类，其中包含`buildConfigField`中声明的字段。
在未声明任何`buildConfigField`的情况下，也会生成默认的`BuildConfig`，其中包含：
```java
public static final boolean DEBUG = Boolean.parseBoolean("true");  
public static final String APPLICATION_ID = "net.funol.workwithgradle";  
public static final String BUILD_TYPE = "debug";  
public static final String FLAVOR = "";  
public static final int VERSION_CODE = 1;  
public static final String VERSION_NAME = "1.0";
```
也可以声明自己的`buildConfigField`。同一字段在不同的`buildTypes`中声明不同的值，可以在构建的不同版本中使用，加以区分它们。比如在`release`版本中使用生产环境的`host`,在`debug`版本中使用测试环境的`host`，发布的时候就可以免去手动修改造成的一些不必要的麻烦。当然用gradle也可以一键打包所有版本。
```gradle
buildTypes {
        debug {
            ......
            buildConfigField "String", "HOST", '"https://debug.funol.net"'
            buildConfigField "boolean", "isRelease", 'false'
            buildConfigField "int", "versionCode", "${defaultConfig.versionCode}"
        }
        release {
            ......
            buildConfigField "String", "HOST", '"https://release.funol.net"'
            buildConfigField "boolean", "isRelease", 'true'
            buildConfigField "int", "versionCode", "${defaultConfig.versionCode}"
        }
}
```
2.使用`productFlavors`
--
一个product flavor定义了从项目中构建了一个应用的自定义版本。一个单一的项目可以同时定义多个不同的flavor来改变应用的输出。虽然最项目终生成了多个定制的版本，但是它们本质上都是同一个应用，这种做法是比使用库项目更好的实现方式。
通常情况下，我们会在不同的应用市场发布应用。为了区分这些应用市场，我们会修改工程中的某个参数然后将工程分别打包。这个工作简单，但是频繁修改会增大出错的概率，而且发布的市场比较多的时候，这会占用很大一部分时间。为了节省时间，我们可以使用`productFlavors`，在不同的flavor中定制应用。
```gradle
    productFlavors {
        official{
			//更多的定制信息
			......
            versionName "1.0-official"
        }
        google {
			......
            versionName "1.0-google"
        }
        wandoujia {
			......
            versionName "1.0-wandoujia"
        }
		......
    }
```
这样定义完成之后，执行`gradlew assembleRelease`就可以打包出flavor中的所有版本。输出路径为`<ProjectFolder>\app\build\outputs\apk\`
3.使用`manifestPlaceholders`
--
有时候我们需要在AndroidManifest中使用不同的key来定制不同的版本，这时候可以使用`manifestPlaceholders`来替换AndroidManifest中的值。
在AndroidManifest中需要替换值得地方声明`${DEMO_VALUE}`
```xml
<meta-data
    android:name="DEMO_NAME"
    android:value="${DEMO_VALUE}"/>
```
在gradle中使用`manifestPlaceholders`即可将其替换
```gradle
manifestPlaceholders = [DEMO_VALUE: "demo-value"]
```
`manifestPlaceholders`的数据类型为`Map`，如果要一次替换多个值，写法如下：
```gradle
manifestPlaceholders = [DEMO_VALUE1: "demo-value1", 
						DEMO_VALUE2: "demo-value2"]
```
通常使用统计SDK的时候会对不同渠道的app加以区分，比如友盟统计在`AndroidManifest`中的`meta-data`声明不同的`UMENG_CHANNEL`值来区分不同渠道。这时可以在`productFlavors`中使用manifestPlaceholders替换`meta-data`中的内容。如果使用`flavor`的名字作为`UMENG_CHANNEL`的值，可以统一声明。
```gradle
productFlavors.all { flavor ->
    flavor.manifestPlaceholders = [UMENG_CHANNEL: name]
}
```
有时会在`buildTypes`中定义不同版本的app，在这些app中使用了不同的第三方sdk的key，发布的时候又需要分发到不同的渠道。这时可以在`buildTypes`和`productFlavors`混合使用`manifestPlaceholders`来定制版本。
```gradle
	buildTypes {
        debug {
			......
            manifestPlaceholders.putAll([
                    UMENG_APP_KEY     : "55a4612067e58ebxxxxxxxxx"])
        }
        release {
            ......
            manifestPlaceholders.putAll([
                    UMENG_APP_KEY     : "55a45f2967e58e1xxxxxxxxx"])
        }
        toTest {
			......
            manifestPlaceholders.putAll([
                    UMENG_APP_KEY     : "55a4612067e58ebxxxxxxxxx"])
        }
    }
	......
	productFlavors.all { flavor ->
        flavor.manifestPlaceholders.putAll([
                AMAP_API_KEY      : "e86b3a2aa88d36b36322e58xxxxxxxxx",
                UMENG_CHANNEL     : name])
    }
```
