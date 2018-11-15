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
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(Constants.ANNOTATION)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ResourceCreationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        if (environment.processingOver()) {
            try (val writer = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "writable.res").openWriter()) {
                writer.append("Writable file, hooray!");
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to read " + Constants.ANNOTATION + " source");
            }
        }
        return false;
    }
}
