package cn.tcc.foundation.core.log;

import java.time.LocalDateTime;

/**
 * Created by hbxia on 2017/3/23.
 */
public class TraceLogBuilder {

    private static final ThreadLocal<StringBuilder> traceLogBuilder = ThreadLocal.withInitial(() -> new StringBuilder(16 * 1024));
    private static final ThreadLocal<Integer> sequence = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Boolean> logSwitch = ThreadLocal.withInitial(() -> false);

    /**
     *
     */
    public static void start() {
        logSwitch.set(true);
        clear();
    }

    /**
     * @return
     */
    public static String end() {
        logSwitch.set(false);
        String message = traceLogBuilder.get().toString();
        System.out.println(message);
        clear();
        return message;
    }

    /**
     * @param message
     */
    public static void appendLine(String message) {
        appendLine(message, new Object[]{});
    }

    /**
     * @param message
     * @param args
     */
    public static void appendLine(String message, Object... args) {
        appendFormat(message, true, args);
    }

    /**
     * @param message
     */
    public static void append(String message) {
        append(message, new Object[]{});
    }

    public static void append(String message, Object... args) {
        appendFormat(message, false, args);
    }

    /**
     * @param message
     * @param args
     */
    private static void appendFormat(String message, boolean isNewLine, Object... args) {
        if (logSwitch.get()) {
            if (args != null && args.length > 0) {
                message = String.format(message, args);
            }
            String prefix = "";
            if (isNewLine) {
                String newLineStr = "<br/>\r\n";
                Long threadId = Thread.currentThread().getId();
                prefix = String.format("%s[%s][%s][%s]:", newLineStr, threadId, LocalDateTime.now().toString(), increaseSequence());
            }
            traceLogBuilder.get().append(prefix + message);
        }
    }

    /**
     *
     */
    private static void clear() {
        String message = traceLogBuilder.get().toString();
        traceLogBuilder.get().delete(0, message.length());
        sequence.set(0);
    }

    /**
     * @return
     */
    private static Integer increaseSequence() {
        Integer seq = sequence.get();
        ++seq;
        sequence.set(seq);
        return seq;
    }
}
