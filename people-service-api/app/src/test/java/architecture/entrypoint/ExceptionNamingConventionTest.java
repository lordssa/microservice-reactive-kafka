package architecture.entrypoint;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "..com.soares.entrypoint.exception", importOptions = {DoNotIncludeTests.class})
class ExceptionNamingConventionTest {

    @ArchTest
    static final ArchRule classThatResideInEntryPointExceptionPackageShouldHaveExceptionSuffix =
            ArchRuleDefinition.classes()
                .should().haveSimpleNameEndingWith("Exception");

}
