package architecture.dataprovider;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "..com.soares.dataprovider", importOptions = {DoNotIncludeTests.class})
class GatewayNamingConventionTest {

    @ArchTest
    static final ArchRule classThatResideInDataproviderPackageShouldHaveGatewaySuffix =
            ArchRuleDefinition.classes()
                .should().haveSimpleNameEndingWith("Gateway");

}
