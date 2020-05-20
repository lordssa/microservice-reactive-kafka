package architecture.entrypoint;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "..com.soares.entrypoint.adapter", importOptions = {DoNotIncludeTests.class})
class AdapterNamingConventionTest {

    @ArchTest
    static final ArchRule classThatResideInEntryPointAdapterPackageShouldHaveConverterSuffix =
            ArchRuleDefinition.classes()
                .should().haveSimpleNameEndingWith("Converter");

}
