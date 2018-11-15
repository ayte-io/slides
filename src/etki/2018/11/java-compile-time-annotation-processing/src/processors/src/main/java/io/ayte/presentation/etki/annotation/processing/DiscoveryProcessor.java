package io.ayte.presentation.etki.annotation.processing;

import com.google.auto.service.AutoService;
import lombok.Cleanup;
import lombok.val;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({Constants.ANNOTATION})
public class DiscoveryProcessor extends AbstractProcessor {
    private Set<Element> accumulator = new HashSet<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        if (!environment.processingOver()) {
            annotations.stream()
                    .flatMap(annotation -> environment.getElementsAnnotatedWith(annotation).stream())
                    .forEach(accumulator::add);
        } else {
            try {
                val resource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/discovered.res");
                @Cleanup
                val writer = resource.openWriter();
                for (val element : accumulator) {
                    writer
                            .append(stringify(element))
                            .append('\n');
                }
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to create discovery resource");
            }
        }
        return false;
    }

    private static String stringify(Element element) {
        StringBuilder builder = new StringBuilder();
        if (element.getEnclosingElement() != null) {
            builder
                    .append(stringify(element.getEnclosingElement()))
                    .append(" : ");
        }
        builder.append(element instanceof QualifiedNameable ? ((QualifiedNameable) element).getQualifiedName() : element.getSimpleName());
        return builder.toString();
    }
}
