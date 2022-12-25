package org.example;

import org.spiderland.Psh.GA;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.spiderland.Psh.GA.GAWithParameters;
import static org.spiderland.Psh.Params.ReadFromFile;

public class Main {

    public static void main(final String[] args) throws Exception {
        final GA ga = GAWithParameters(ReadFromFile(getFileFromResource("RobocodeRobotEvolution.pushgp")));
        ga.Run();
    }

    private static File getFileFromResource(final String fileName) throws URISyntaxException {
        final ClassLoader classLoader = Main.class.getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}
