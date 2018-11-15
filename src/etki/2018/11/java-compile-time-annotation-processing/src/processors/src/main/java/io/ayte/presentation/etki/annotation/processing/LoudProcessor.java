package io.ayte.presentation.etki.annotation.processing;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({Constants.ANNOTATION})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class LoudProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        annotations.stream()
                .flatMap(annotation -> environment.getElementsAnnotatedWith(annotation).stream())
                .forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Look what i've found!", element));
        return false;
    }
}
