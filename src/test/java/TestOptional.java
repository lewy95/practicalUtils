import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TestOptional {

    // ===================== 创建 Optional =====================
    // 用来创建一个空的 Optional
    @Test
    public void create_optional_with_empty() {
        Optional<String> empty = Optional.empty();
        // 可以使用 isPresent 判断 Optional 的值是否为空，非 null，则返回 true，反之则 false。
        // Java 11 开始可以使用 isEmpty，isEmpty 与 isPresent 相反，如果为 null 返回 true。
        assertFalse(empty.isPresent());
    }

    // 用来创建一个非空的 Optional 需要注意的是，参数不能为 null，否则会抛空指针异常
    @Test
    public void create_optional_with_of() {
        Optional<String> java = Optional.of("Java");
        //Optional<String> java = Optional.of(null);
        assertTrue(java.isPresent());
    }

    // 用来创建一个可能为空的 Optional
    @Test
    public void create_optional_with_ofNullable() {
        Optional<String> java = Optional.ofNullable("Java");
        assertTrue(java.isPresent());
        // Optional 提供了一个 get 方法获取值，但 get 方法只能在 Optional 有值时使用，
        // 否则会抛出 NoSuchElementException 异常。
        // assertEquals("java", java.get());

        Optional<Object> o = Optional.ofNullable(null);
        assertFalse(o.isPresent());
    }

    // ===================== 获取值 =====================
    // Optional 提供了一个 get 方法获取值，但 get 方法只能在 Optional 有值时使用，否则会抛出 NoSuchElementException 异常
    @Test
    public void get_optional_with_of() {
        Optional<String> java = Optional.of("java");
        assertEquals("java", java.get());
        System.out.println(java.get());
    }

    //@Test(expected = NoSuchElementException.class)
    //public void get_optional_with_of_with_null() {
    //    Optional.empty().get();
    //}

    // ===================== 条件动作 =====================
    // ifPresent 接受一个 Consumer，在 Optional 值非 null 时调用，并接受 Optional 的值。
    @Test
    public void condition_action_ifPresent() {
        Optional<String> java = Optional.ofNullable("java");
        java.ifPresent((value) -> System.out.println("ifPresent accept " + value));

        Optional<Object> aNull = Optional.ofNullable(null);
        aNull.ifPresent(value -> System.out.println("this will never execute"));
    }

    // orElse 在 Optional 值为 null 时触发，它接受一个参数，作为 Optional 的默认值。
    @Test
    public void condition_action_orElse() {
        assertTrue(Optional.ofNullable("java").orElse("javascript").equals("java"));
        assertTrue(Optional.ofNullable(null).orElse("java").equals("java"));
    }

    // orElseGet 与 orElse 类似，但 orElseGet 接受的是一个 Supplier，Supplier 返回的值作为 Optional 的默认值。
    @Test
    public void condition_action_orElseGet() {
        assertTrue(Optional.ofNullable("java").orElseGet(() -> "javascript").equals("java"));
        assertTrue(Optional.ofNullable(null).orElseGet(() -> "java").equals("java"));
    }

    // orElseThrow 与 orElse 一样也在当 Optional 值为 null 时触发，但与之不同的是会抛出指定的异常。
    //@Test(expected = IllegalArgumentException.class)
    //public void condition_action_orElseThrow() {
    //    Optional.ofNullable(null).orElseThrow(IllegalArgumentException::new);
    //}

    // or 是 Java 9 中新增方法。与 orElseGet 很相似，or 也接受一个 Supplier，但 or 返回的是一个新的 Optional。
    //@Test
    //public void condition_or_optional() {
    //    Optional<String> java = Optional.of("java").or(() -> Optional.of("javascript"));
    //    Optional<Object> java1 = Optional.empty().or(() -> Optional.of("java"));
    //    assertEquals("java", java.get());
    //    assertEquals("java", java1.get());
    //}

    // ifPresentOrElse 是 Java 9 中新增的方法。ifPresent 就如同命令式编程中的 if-else，它接受两个参数，第一个为 Consumer，在 Optional 有值时调用，第二个为 Runnable，在无值时调用。
    //@Test
    //public void condition_ifPresentOrElse() {
    //    // value is java
    //    Optional.of("java")
    //            .ifPresentOrElse(value -> System.out.println("value is " + value), () -> System.out.println("ooops"));
    //    // ooops
    //    Optional.empty()
    //            .ifPresentOrElse(value -> System.out.println("value is " + value), () -> System.out.println("ooops"));
    //}

    // ===================== map =====================
    @Test
    public void map_optional() {
        Optional<String> java = Optional.of("java");
        String result = java.map(String::toUpperCase).orElse("");
        assertEquals("JAVA", result);
    }
}
