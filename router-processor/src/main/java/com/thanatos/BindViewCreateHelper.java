package com.thanatos;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

/**
 * Created on 2017/9/27.
 * by liuxiongfei
 */

final class BindViewCreateHelper {

    private static final HashMap<TypeElement,List<VariableElement>> elements = new HashMap<>();

    static void creat(ProcessingEnvironment processingEnv,
                      RoundEnvironment roundEnvironment){

        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {

            List<VariableElement>  fileds = new ArrayList<>();
            TypeElement el = (TypeElement) element.getEnclosingElement();
            for (Element variable:roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
                if (((TypeElement) variable.getEnclosingElement()).getQualifiedName() == el.getQualifiedName()){
                    fileds.add((VariableElement) variable);
                }
            }
            elements.put(el, fileds);
            build(processingEnv);
        }

    }

    private static void build(ProcessingEnvironment processingEnv) {
        for (TypeElement typeElement : elements.keySet()) {
            List<VariableElement> vars = elements.get(typeElement);

            buildClass(processingEnv,typeElement,vars);
        }

    }

    private static void buildClass(ProcessingEnvironment processingEnv,
                                   TypeElement typeElement,
                                   List<VariableElement> vars) {
        StringBuilder builder = new StringBuilder();
        String className = typeElement.getQualifiedName().toString().replace(".","$");
        //类
        builder.append("package com.androidthanatos.annotationprocessor;\n\n")
                .append("public final class ")
                .append(className)
                .append("BindView{\n\n");
        //typeElement.getAnnotation(BindLayout.class) == null
        build(vars,builder,typeElement);

        builder.append("}");

        try {
            JavaFileObject source = processingEnv.getFiler()
                    .createSourceFile("com.androidthanatos.annotationprocessor."+className+"BindView");
            Writer writer = source.openWriter();
            //writer.write(builder.toString());
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException s) {
            // e.printStackTrace();
        }
    }

    private static void build(List<VariableElement> vars, StringBuilder builder, TypeElement typeElement) {
        int layoutId = -1;
        if (typeElement.getAnnotation(BindLayout.class) != null){
            layoutId = typeElement.getAnnotation(BindLayout.class).value();
        }
        String finder = "pw.androidthanatos.router.Finder";
        builder.append("\tpublic static void bindView(")
                    .append(typeElement.getQualifiedName())
                    .append("  target, "+finder+" finder ){\n\n");
        if (layoutId != -1){
            builder.append("\t\t//bind layout\n");
            builder.append("\t\tif( finder.getActivity() != null ){\n\n")
                    .append("\t\t\tfinder.getActivity().setContentView("+layoutId+");\n")
                    .append("\t\t}\n");
        }
        buidlCode(vars, builder,1);

        builder.append("\t}\n\n");


    }

    private static void buidlCode(List<VariableElement> vars, StringBuilder builder, int i) {

        builder.append("\t\t//bind views \n");

        for (VariableElement filed: vars) {

            builder.append("\t\ttarget.")
                    .append(filed.getSimpleName())
                    .append(" = (")
                    .append(filed.asType());
            builder.append(")finder.findView().findViewById(");
            builder.append(filed.getAnnotation(BindView.class).value())
                    .append(");\n");
        }
    }

    /**
     * bindlayout
     * @param builder StringBuilder
     * @param value  layout ID
     */
    private static void addLayout(StringBuilder builder, int value) {
        builder.append("\t\ttarget.setContentView(")
                .append(value)
                .append(");\n");
    }

}
