# Android-BASE
Android Base MVP Library.

Home : [https://github.com/LiangMaYong/android-base](https://github.com/LiangMaYong/android-base)

## AS Plugins

Download plugins : [BindView1.1](https://raw.githubusercontent.com/LiangMaYong/android-base/master/expand/as-plugins/1.1/BindView-Plugin-1.1.zip)

Install plugins
```
Android studio -> file -> settings -> plugins -> Install plugins in disk...
```
## File Templates
add file templates
```
Android studio -> new -> Edit File Templates...-> Add
```
presenter file templates
```
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
import import com.liangmayong.base.binding.mvp.Presenter;
#parse("File Header.java")
public class ${NAME} extends Presenter<${NAME}.IView> {

    public interface IView {
        //efine interface
    }

    // TODO:Do something

}
```
## LICENSE
Copyright © LiangMaYong

Distributed under [MIT](https://github.com/LiangMaYong/android-base/blob/master/LICENSE) license.

## Author
LiangMaYong

ibeam@qq.com
