package io.ayte.presentation.etki.annotation.processing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

@Explosive
public class Entrypoint {
    @Explosive
    public Entrypoint() {}

    @Explosive
    public static void main(@Explosive String[] args) {
        @Explosive
        URL resource = Entrypoint.class.getClassLoader().getResource("META-INF/discovered.res");
        blackhole(new Container<@Explosive URL>(resource));
        if (resource == null) {
            System.out.println("Couldn't find resource");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            System.out.println("Found annotated elements:");
            reader.lines().forEach(System.out::println);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find resource");
        }
    }

    @Explosive
    private static void blackhole(@Explosive Container<?> container) {}

    @Explosive
    private static class Container<@Explosive T> {
        @Explosive
        public Container(@Explosive T value) {
            this.value = value;
        }

        @Explosive
        private final T value;
    }
}
