# android-base
README: [English](https://github.com/LiangMaYong/android-base/blob/master/README.md) | [中文](https://github.com/LiangMaYong/android-base/blob/master/README_ZH.md)

这是一个Android基础框架

下载AAR : [base-2.0.0.aar](https://raw.githubusercontent.com/LiangMaYong/android-base/master/release/2.0.0/base-2.0.0.aar)

## 功能
1,Database

2,Http

3,Web

4,Fragment

5,Skin

6,MVP

7,SuperListView

8,DefaultToolbar

9,Iconfont

10,Annotation

## 注解

@BindLayout -> Activity and Fragment Layout

@BindTitle -> Activity Title

@BindView -> View

@BindOnClik -> OnClickListener

@BindOnLongClik -> OnLongClickListener

@BindColor -> Color

@BindString -> String

@BindP -> Presenter

## BindView插件

下载插件 : [BindView1.1](https://raw.githubusercontent.com/LiangMaYong/android-base/master/plugins/1.1/BindView-Plugin-1.1.zip)

安装BindView插件
```
Android studio -> file -> settings -> plugins -> Install plugins in disk...
```
## Presenter文件模板
添加Presenter文件模板
```
Android studio -> new -> Edit File Templates...-> Add
```
模板内容
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
## Fresco
默认引入fresco作为图片加载库，不需要可以移除依赖:
移除Fresco库
```
compile(project(':base')) {
    exclude group: 'com.facebook.fresco'
}
```

## Skin控件属性
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
        <enum name="default_type" value="0" />
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
