# Android-BASE
This is android base.

## Annotations

@BindLayout -> Activity and Fragment Layout

@BindTitle -> Activity or Fragment Title

@BindView -> View

@BindOnClik -> OnClickListener

@BindOnLongClik -> OnLongClickListener

@BindColor -> Color

@BindString -> String

@BindP -> Presenter

## BindView Plugins

download plugins : [BindView1.1](https://raw.githubusercontent.com/LiangMaYong/android-base/master/expand/as-plugins/1.1/BindView-Plugin-1.1.zip)

Install bindView plugins
```
Android studio -> file -> settings -> plugins -> Install plugins in disk...
```
## Presenter File Templates
add Presenter File Templates
```
Android studio -> new -> Edit File Templates...-> Add
```
file templates
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
```
Copyright 2016 LiangMaYong

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
