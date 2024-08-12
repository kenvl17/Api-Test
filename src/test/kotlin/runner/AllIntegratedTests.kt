package runner

import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite
import tests.ItemsTests

@Suite
@SelectClasses(
  ItemsTests::class
)
class AllIntegratedTests



