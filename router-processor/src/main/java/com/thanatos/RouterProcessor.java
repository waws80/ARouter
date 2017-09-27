package com.thanatos;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;


@SupportedAnnotationTypes("com.thanatos.BindAlias")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //构建路由表
        RouterCreaterHelper.creat(processingEnv,roundEnvironment);
        //构建Activity的绑定UI类
        BindViewCreateHelper.creat(processingEnv,roundEnvironment);

        return true;
    }
}
