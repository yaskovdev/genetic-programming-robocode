package org.example;

import org.spiderland.Psh.GA;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.spiderland.Psh.GA.gaWithParameters;
import static org.spiderland.Psh.Params.readFromFile;

public class Main {

    public static void main(final String[] args) throws Exception {
        final GA ga = gaWithParameters(readFromFile(getFileFromResource("RobocodeRobotEvolution.pushgp")));
        ga.run();
    }

    private static File getFileFromResource(final String fileName) throws URISyntaxException {
        final ClassLoader classLoader = Main.class.getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File with name " + fileName + " was not found");
        } else {
            return new File(resource.toURI());
        }
    }
}
