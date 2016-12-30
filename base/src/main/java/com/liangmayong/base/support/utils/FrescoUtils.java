package com.liangmayong.base.support.utils;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by LiangMaYong on 2016/10/26.
 */
public final class FrescoUtils {

    private static boolean isInit = false;

    /**
     * showThumb
     *
     * @param draweeView     draweeView
     * @param url            url
     * @param resizeWidthDp  resizeWidth
     * @param resizeHeightDp resizeHeight
     */
    public static void showThumb(SimpleDraweeView draweeView, String url, int resizeWidthDp, int resizeHeightDp) {
        try {
            if (!isInit)
                return;
            if (url == null || "".equals(url))
                return;
            if (draweeView == null)
                return;
            initialize(draweeView.getContext());
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .setResizeOptions(new ResizeOptions(DimenUtils.dip2px(draweeView.getContext(), resizeWidthDp), DimenUtils.dip2px(draweeView.getContext(), resizeHeightDp)))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(draweeView.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
        }
    }

    /**
     * setControllerListener
     *
     * @param simpleDraweeView simpleDraweeView
     * @param imagePath        imagePath
     * @param imageWidth       imageWidth
     */
    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath, final int imageWidth) {
        try {
            if (!isInit)
                return;
            final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                    if (imageInfo == null) {
                        return;
                    }
                    int height = imageInfo.getHeight();
                    int width = imageInfo.getWidth();
                    layoutParams.width = imageWidth;
                    layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
                    simpleDraweeView.setLayoutParams(layoutParams);
                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    throwable.printStackTrace();
                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setUri(Uri.parse(imagePath)).build();
            simpleDraweeView.setController(controller);
        } catch (Exception e) {
        }
    }

    /**
     * initialize
     *
     * @param context context
     */
    public static void initialize(Context context) {
        try {
            if (isInit)
                return;
            Class<?> clazz = Class.forName("com.facebook.imagepipeline.core.ImagePipelineConfig");
            if (clazz != null) {
                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                        .setDownsampleEnabled(true)
                        .build();
                Fresco.initialize(context, config);
                isInit = true;
            }
        } catch (Exception e) {
        }
    }
}
