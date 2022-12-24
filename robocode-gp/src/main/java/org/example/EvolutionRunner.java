package org.example;

import org.spiderland.Psh.GA;
import org.spiderland.Psh.Params;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class EvolutionRunner {

    public static void main(final String[] args) throws Exception {
        final EvolutionRunner runner = new EvolutionRunner();
        final GA ga = GA.GAWithParameters(Params.ReadFromFile(runner.getFileFromResource("RobocodeRobotEvolution.pushgp")));
        ga.Run();
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }
}
