package io.ayte.presentation.etki.annotation.processing;

import com.google.auto.service.AutoService;
import lombok.val;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(Constants.ANNOTATION)
public class CodeGenerationProcessor extends AbstractProcessor {
    private boolean executed = false;
    private int round = 1;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, getClass() + ": Round " + round++);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, getClass() + ": Processed annotations: " + annotations);
        if (environment.processingOver()) {
            return false;
        }
        if (!executed) {
            executed = true;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating new source file");
            try (val writer = processingEnv.getFiler().createSourceFile(Constants.MAIN_PACKAGE + ".Generated").openWriter()) {
                writer
                        .append("package " + Constants.MAIN_PACKAGE + ";\n\n")
                        .append("import " + Constants.ANNOTATION + ";\n\n")
                        .append("@Explosive\n")
                        .append("class Generated {}\n")
                        .flush();
            } catch (IOException e) {
                throw new RuntimeException("Failed to generate class", e);
            }
        }
        annotations.stream()
                .flatMap(annotation -> environment.getElementsAnnotatedWith(annotation).stream())
                .filter(element -> element instanceof TypeElement)
                .forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing element: " + element));
        return false;
    }
}
