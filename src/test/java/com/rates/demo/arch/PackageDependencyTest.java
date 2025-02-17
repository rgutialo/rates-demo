package com.rates.demo.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(packages = "com.rates.demo")
public class PackageDependencyTest {

    private static final JavaClasses classes =
            new ClassFileImporter().importPackages("com.rates.demo");

    @ArchTest
    public static final ArchRule applicationShouldNotDependOnInfrastructure =
            noClasses()
                    .that()
                    .resideInAPackage("..application..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..infrastructure..");

    @Test
    public void applicationShouldNotDependOnInfrastructure() {
        applicationShouldNotDependOnInfrastructure.check(classes);
    }
}
