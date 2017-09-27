package com.thanatos;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * 创建AndroidRouter路由表
 */

final class RouterCreaterHelper {

    static void creat(ProcessingEnvironment processingEnv,
                      RoundEnvironment roundEnvironment){

        StringBuilder builder = new StringBuilder();
        builder.append("package com.androidthanatos.annotationprocessor;\n\n")
                .append("import java.util.HashMap;\n\n")
                .append("public final class RouterHelper{\n\n")
                .append("\tprivate static HashMap<String, Class> mGloabMap = new HashMap<>();\n\n")
                .append("\tpublic static HashMap<String, Class> routerTabs(){\n\n");

        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindAlias.class)) {

            builder.append("\t\tmGloabMap.put(\"");
            String value = element.getAnnotation(BindAlias.class).value();

            if (value.isEmpty() || value.length() == 0){
                value = element.getSimpleName().toString();
            }
            TypeElement element1 = (TypeElement) element;

            builder.append(value)
                    .append("\", ")
                    .append(""+element1.getQualifiedName().toString()+".class")
                    .append(");\n\n");

        }
        builder.append("\t\treturn mGloabMap;\n\n");
        builder.append("\t}\n").append("}\n");

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.androidthanatos.annotationprocessor.RouterHelper");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }

    }
}
