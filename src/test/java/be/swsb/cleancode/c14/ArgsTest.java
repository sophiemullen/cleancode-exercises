package be.swsb.cleancode.c14;

import be.swsb.cleancode.ch14.args.Args;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArgsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCreateWithNoSchemaOrArguments() throws Exception {
        Args args = new Args("", new String[0]);
        assertThat(args.cardinality()).isEqualTo(0);
    }

    @Test
    public void testNonLetterSchema() throws Exception {
        assertThatThrownBy(() -> new Args("*", new String[]{}))
                .isInstanceOf(ParseException.class)
                .hasMessage("Bad character:*in Args format: *");
    }

    @Test
    public void testInvalidArgumentFormat() throws Exception {
        assertThatThrownBy(() -> new Args("f~", new String[]{}))
                .isInstanceOf(ParseException.class)
                .hasMessage("Argument: f has invalid format: ~.");
    }

    @Test
    public void testSimpleBooleanPresent() throws Exception {
        Args args = new Args("x", new String[]{"-x"});
        assertThat(args.cardinality()).isEqualTo(1);
        assertThat(args.getBoolean('x')).isEqualTo(true);
    }

    @Test
    public void testSimpleStringPresent() throws Exception {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertThat(args.cardinality()).isEqualTo(1);
        assertThat(args.has('x')).isTrue();
        assertThat(args.getString('x')).isEqualTo("param");
    }

    @Test
    public void testSpacesInFormat() throws Exception {
        Args args = new Args("x, y", new String[]{"-xy"});
        assertThat(args.cardinality()).isEqualTo(2);
        assertThat(args.has('x')).isTrue();
        assertThat(args.has('y')).isTrue();
    }

    @Test
    public void testSimpleIntPresent() throws Exception {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertThat(args.cardinality()).isEqualTo(1);
        assertThat(args.has('x')).isTrue();
        assertThat(args.getInt('x')).isEqualTo(42);
    }
}