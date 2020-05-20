package architecture.general;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Component;

@AnalyzeClasses(packages = "com.soares..", importOptions = DoNotIncludeTests.class)
class AnnotationConventionTest {

    @ArchTest
    static final ArchRule classesConvertersShoulBeAnnotatedWithComponentsAnnotation = ArchRuleDefinition.classes()
            .that().resideInAPackage("..adapter..")
            .and().haveSimpleNameEndingWith("Converter")
            .and().areNotInterfaces()
            .should().beAnnotatedWith(Component.class);

    @ArchTest
    static final ArchRule classesGatewayShoulBeAnnotatedWithComponentsAnnotation = ArchRuleDefinition.classes()
            .that().resideInAPackage("..dataprovider..")
            .and().haveSimpleNameEndingWith("Gateway")
            .and().areNotInterfaces()
            .should().beAnnotatedWith(Component.class);

}
