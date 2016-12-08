# android-base

README: [English](https://github.com/LiangMaYong/android-base/blob/master/README.md) | [中文](https://github.com/LiangMaYong/android-base/blob/master/README_ZH.md)

This is android base.

Download AAR : [base-1.3.0.aar](https://raw.githubusercontent.com/LiangMaYong/android-base/master/release/1.3.0/base-1.3.0.aar)

## Function
1,Database

2,Http

3,Web

4,Fragment

5,Skin

6,MVP

7,SuperListView

8,DefualtToolbar

9,Iconfont

10,Annotation

## Annotations

@BindLayout -> Activity and Fragment Layout

@BindTitle -> Activity Title

@BindView -> View

@BindOnClik -> OnClickListener

@BindOnLongClik -> OnLongClickListener

@BindColor -> Color

@BindString -> String

@BindP -> Presenter

## BindView Plugins

download plugins : [BindView1.1](https://raw.githubusercontent.com/LiangMaYong/android-base/master/plugins/1.1/BindView-Plugin-1.1.zip)

Install bindView plugins
```
Android studio -> file -> settings -> plugins -> Install plugins in disk...
```

## Fresco

Exclude Fresco library
```
compile(project(':base')) {
    exclude group: 'com.facebook.fresco'
}
```
## Presenter File Templates
add Presenter File Templates
```
Android studio -> new -> Edit File Templates...-> Add
```
file templates
```
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
import com.liangmayong.base.support.binding.Presenter;
#parse("File Header.java")
public class ${NAME} extends Presenter<${NAME}.IView> {

    public interface IView {
        //efine interface
    }

    // TODO:Do something

}
```
## Skin Styleable
```
<declare-styleable name="SkinStyleable">
    <attr name="pressed_color" format="color" />
    <attr name="pressed_alpha" format="integer" />
    <attr name="background_transparent" format="boolean" />
    <attr name="background_cover" format="color" />
    <attr name="background_alpha" format="integer" />
    <attr name="stroke_width" format="dimension" />
    <attr name="radius" format="dimension" />
    <attr name="shape_type" format="enum">
        <enum name="round" value="0" />
        <enum name="rectangle" value="1" />
        <enum name="stroke" value="2" />
        <enum name="oval" value="3" />
        <enum name="transparent" value="4" />
    </attr>
    <attr name="skin_color" format="color" />
    <attr name="skin_text_color" format="color" />
    <attr name="skin_type" format="enum">
        <enum name="defualt" value="0" />
        <enum name="primary" value="1" />
        <enum name="success" value="2" />
        <enum name="info" value="3" />
        <enum name="warning" value="4" />
        <enum name="danger" value="5" />
        <enum name="white" value="6" />
        <enum name="gray" value="7" />
        <enum name="black" value="8" />
    </attr>
</declare-styleable>
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
