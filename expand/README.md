# Expand
Android Base Expand

## Android Studio Plugins

### BindView

Version 1.1

Download : [Download](https://raw.githubusercontent.com/LiangMaYong/android-base/master/expand/as-plugins/bindview/bindview-plugin-1.1.zip)


### Install plugin
Install plugins
```
Android studio -> file -> settings -> plugins -> Install plugins in disk...
```
## Android Studio File Templates

### Persenter File Templates
```
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
import com.liangmayong.base.binding.mvp.Presenter;
#parse("File Header.java")
public class ${NAME} extends Presenter<${NAME}.IView> {

    public interface IView {
        //efine interface
    }

    // TODO:Do something

}
```
### Add File Templates
```
Android studio -> new -> Edit File Templates...-> Add
```
## IconFont Tool

```cmd
>IconFont -help
|-----------------------------------|
|===========     HELP    ===========|
|-----------------------------------|
|                                   |
| -help :   show help         ------|
| -d    :   Input dir         ------|
| -p    :   Package name      ------|
| -f    :   Font file path    ------|
| -n    :   Class name        ------|
| -o    :   Output dir        ------|
| -v    :   Icon font version ------|
| -x    :   Icon value prefix ------|
|                                   |
|-----------------------------------|

```

## LICENSE
Copyright © LiangMaYong

Distributed under [MIT](https://github.com/LiangMaYong/android-base/blob/master/LICENSE.txt) license.

## Author
LiangMaYong

ibeam@qq.com
