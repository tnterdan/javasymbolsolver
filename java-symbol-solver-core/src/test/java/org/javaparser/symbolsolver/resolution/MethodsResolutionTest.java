/*
 * Copyright 2016 Federico Tomassetti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.javaparser.symbolsolver.resolution;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import org.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import org.javaparser.symbolsolver.javaparser.Navigator;
import org.javaparser.symbolsolver.resolution.typesolvers.JreTypeSolver;
import org.javaparser.symbolsolver.model.usages.MethodUsage;
import org.javaparser.symbolsolver.model.usages.typesystem.Type;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MethodsResolutionTest extends AbstractResolutionTest {

    @Test
    public void solveMethodAccessThroughSuper() throws ParseException {
        CompilationUnit cu = parseSample("AccessThroughSuper");
        com.github.javaparser.ast.body.ClassOrInterfaceDeclaration clazz = Navigator.demandClass(cu, "AccessThroughSuper.SubClass");
        MethodDeclaration method = Navigator.demandMethod(clazz, "methodTest");
        ReturnStmt returnStmt = (ReturnStmt)method.getBody().getStmts().get(0);
        Expression expression = returnStmt.getExpr();

        Type ref = JavaParserFacade.get(new JreTypeSolver()).getType(expression);
        assertEquals("java.lang.String", ref.describe());
    }
    
    @Test
    public void solveMethodWithClassExpressionAsParameter() throws ParseException {
        CompilationUnit cu = parseSample("ClassExpression");
        com.github.javaparser.ast.body.ClassOrInterfaceDeclaration clazz = Navigator.demandClass(cu, "ClassExpression");
        MethodDeclaration method = Navigator.demandMethod(clazz, "foo");
        MethodCallExpr expression = Navigator.findMethodCall(method, "noneOf");

        MethodUsage methodUsage = JavaParserFacade.get(new JreTypeSolver()).solveMethodAsUsage(expression);
        assertEquals("noneOf", methodUsage.getName());
    }
}
