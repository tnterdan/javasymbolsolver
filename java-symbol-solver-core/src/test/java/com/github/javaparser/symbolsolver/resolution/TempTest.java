package com.github.javaparser.symbolsolver.resolution;

import com.github.javaparser.ParseException;
import com.github.javaparser.symbolsolver.SourceFileInfoExtractor;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by federico on 09/03/2017.
 */
public class TempTest {

    @Test
    public void issue112() throws IOException, ParseException {
        File rootDir = new File("/Users/federico/repos/TestJavaSymbolSolver/app/src/main/java/");
        TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(),
                new JarTypeSolver("/Users/federico/repos/TestJavaSymbolSolver/libs/android.jar"),
                new JavaParserTypeSolver(rootDir));
        SourceFileInfoExtractor sourceFileInfoExtractor = new SourceFileInfoExtractor();
        sourceFileInfoExtractor.setTypeSolver(typeSolver);
        sourceFileInfoExtractor.setVerbose(true);
        sourceFileInfoExtractor.solve(rootDir);

    }
}
