package com.isoft.tms;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.isoft.tms");

        noClasses()
            .that()
            .resideInAnyPackage("com.isoft.tms.service..")
            .or()
            .resideInAnyPackage("com.isoft.tms.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.isoft.tms.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
