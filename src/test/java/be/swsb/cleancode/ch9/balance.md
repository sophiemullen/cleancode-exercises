## Advantages
### Readability
Extremely well readable. There is no more _translation_ going on in your head, in comparison to the _hBChl_ string DSL.

### Highly reusable
When `MockControlHardware` is a collaborator of many classes you can reuse the same extremely readable asserts.

### Plays nice with AssertJ
Because we `extend Assertions` you can still just have the one static import and still use the regular `assertThat("someString").isEqualTo("someString")` assertions.

## Disadvantages
### Coupling to stub class
Tightly coupled to both the `MockControlHardware` class, which isn't that much of an issue since our test is always going to be coupled to that stub implementation.

### Coupling to AssertJ
Tightly coupled to AssertJ with both the `extends AbstractAssert`, `extends Assertions` and the usage of its `Strings` util class.

Like any other library that changes fairly frequently, you might need to take into account how much you'll need to change when you want to update your AssertJ dependency.

It might be better to instead write a wrapper.

### Forgetting to call `.hasTheRestOff()` can cause false positives
If you don't explicitly call `.hasTheRestOff()` on the custom assert, toggles that should be _the default_ can still be turned on, and the tests wouldn't fail.

This is a design issue rather than a test issue. Don't fall into the trap of trying to fix the tests. Fix the design issue instead.