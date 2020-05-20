package architecture.general;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "..com.soares..", importOptions = DoNotIncludeTests.class)
class LayeredArchitectureTest {

    private static final String ENTRY_POINT = "EntryPoint";
    private static final String DATA_PROVIDER = "DataProvider";
    private static final String USE_CASE = "UseCase";
    private static final String GATEWAY = "Gateway";

    @ArchTest
    static final ArchRule layeredArchitectureRules = Architectures.layeredArchitecture()
            .layer(ENTRY_POINT).definedBy("..app.entrypoint..")
            .layer(DATA_PROVIDER).definedBy("..app.dataprovider..")
            .layer(USE_CASE).definedBy("..core.usecase..")
            .layer(GATEWAY).definedBy("..core.gateway..")

            .whereLayer(ENTRY_POINT).mayNotBeAccessedByAnyLayer()
            .whereLayer(USE_CASE).mayOnlyBeAccessedByLayers(ENTRY_POINT)
            .whereLayer(GATEWAY).mayOnlyBeAccessedByLayers(DATA_PROVIDER, USE_CASE)
            .whereLayer(DATA_PROVIDER).mayOnlyBeAccessedByLayers(GATEWAY);
}
