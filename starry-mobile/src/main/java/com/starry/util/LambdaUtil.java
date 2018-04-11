package com.starry.util;

import com.starry.Exception.BusiRuntimeWrapException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * java8 lambda表达式帮助类
 */
public class LambdaUtil {

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     *  处理Lambda表达式里面的错误
     */
    public static final Consumer<Throwable> THROWABLE_TO_RUNTIME_EXCEPTION = (t) -> {
        if(t instanceof Error) {
            throw (Error)t;
        } else if(t instanceof RuntimeException) {
            throw (RuntimeException)t;
        } else if(t instanceof Exception){
            throw new BusiRuntimeWrapException((Exception)t);
        }
    };

    @FunctionalInterface
    public interface CheckedExceptionConsumer<T> {
        void accept(T var1) throws Throwable;
    }

	@FunctionalInterface
	public interface CheckedExceptionSupplier<T> {
		T get() throws Throwable;
	}

	@FunctionalInterface
	public interface CheckedExceptionPredicate<T> {
		boolean test(T t) throws Throwable;
	}

    @FunctionalInterface
    public interface CheckedExceptionFunction<T, R> {
        R apply(T var1) throws Throwable;
    }

    /**
     * 包装错误处理
     * @param consumer
     * @param handler
     * @param <T>
     * @return
     */
    public static <T> Consumer<T> wrapException(CheckedExceptionConsumer<T> consumer, Consumer<Throwable> handler) {
        return (t) -> {
            try {
                consumer.accept(t);
            } catch (Throwable tx) {
                handler.accept(tx);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", tx);
            }
        };
    }

    /**
     * 处理lambda表示式 each,
     * @param consumer
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Consumer<T> wrapException(CheckedExceptionConsumer<T> consumer) throws Exception {
        return wrapException(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

	public static <T> Supplier<T> wrapSupplier(CheckedExceptionSupplier<T> supplier, Consumer<Throwable> handler) {
		return () -> {
			try {
				return supplier.get();
			} catch (Throwable tx) {
				handler.accept(tx);
				throw new IllegalStateException("Exception handler must throw a RuntimeException", tx);
			}
		};
	}

	public static <T> Supplier<T> wrapSupplier(CheckedExceptionSupplier<T> supplier) throws Exception {
		return wrapSupplier(supplier, THROWABLE_TO_RUNTIME_EXCEPTION);
	}

	public static <T> Predicate<T> wrapPredicate(CheckedExceptionPredicate<T> predicate, Consumer<Throwable> handler) {
		return (t) -> {
			try {
				return predicate.test(t);
			} catch (Throwable tx) {
				handler.accept(tx);
				throw new IllegalStateException("Exception handler must throw a RuntimeException", tx);
			}
		};
	}

	public static <T> Predicate<T> wrapPredicate(CheckedExceptionPredicate<T> predicate) throws Exception {
		return wrapPredicate(predicate, THROWABLE_TO_RUNTIME_EXCEPTION);
	}

    /**
     *  处理lambda表示式 map
     * @param function
     * @param handler
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<T, R> wrapFunction(CheckedExceptionFunction<T, R> function, Consumer<Throwable> handler) {
        return (t) -> {
            try {
                return function.apply(t);
            } catch (Throwable tx) {
                handler.accept(tx);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", tx);
            }
        };
    }

    /**
     * 处理lambda表达式的map, 等方法的报错
     * @param function
     * @param <T>
     * @param <R>
     * @return
     * @throws Exception
     */
    public static <T, R> Function<T, R> wrapFunction(CheckedExceptionFunction<T, R> function) throws Exception {
        return wrapFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }
}
