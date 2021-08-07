package com.spring.aop.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/6  16:07
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： 切面表达式工具类
 */
final public class AspectExecutionEUtil {
    // execution表达式合法性检测的正则表达式
    final private static String EXECUTION_E = "^(((public|private|default|protected|\\\\*) )?((\\\\w+ )*|\\\\* )?(((\\\\w|\\\\.)+|\\\\*) )(\\\\w|\\\\*|\\\\.)+\\\\((\\\\w|,|\\\\.| )+\\\\)( (\\\\w|\\\\.|\\\\*)+)?)$";

    // 方法正则表达式
    final private static String METHOD_E = "^((\\\\w|\\\\*)+)$";

    // 访问权限正则表达式
    final private static String ACCESS_RIGHT = "^(public|private|default|protected|\\\\*)$";

    // execution合法性检测的正则表达式对象
    final public static Pattern executionRegex = Pattern.compile(EXECUTION_E);

    // 方法正则表达式对象
    final public static Pattern methodRegex = Pattern.compile(METHOD_E);

    // 访问权限正则表达式对象
    final public static Pattern accessRightRegex = Pattern.compile(ACCESS_RIGHT);

    private AspectExecutionEUtil() {}

    // 数据类型对照表
    private final static Map<String, String> basicDataTypeComparisonTable = new HashMap<>();

    // 访问权限类型：((public|private|default|protected) )?
    private final static String MODIFIERS_PATTERN = "((public|private|default|protected) )?";

    // 其它修饰符：((\\w+ )*)?
    private final static String OPTIONAL_MODIFIERS_PATTERN = "((\\\\w+ )*)?";

    // 返回类型：(((\\w|\\.)+|\\*) )
    private final static String RET_TYPE_PATTERN = "(((\\\\w|\\\\.)+|\\\\*) )";

    // 全限定类名.方法(形参)：(\\w|\\*|\\.)+\\((\\w|,|\\.)*\\)
    private final static String NAME_PATTERN = "(\\\\w|\\\\*|\\\\.)+\\\\((\\\\w|,|\\\\.)*\\\\)";

    // 异常：( throws (\\w|\\.|,)+)?
    private final static String THROW_PATTERN = "( throws (\\\\w|\\\\.|,)+)?";

    static {
        basicDataTypeComparisonTable.put("Byte", "java.lang.Byte");
        basicDataTypeComparisonTable.put("Character", "java.lang.Character");
        basicDataTypeComparisonTable.put("Short", "java.lang.Short");
        basicDataTypeComparisonTable.put("Integer", "java.lang.Integer");
        basicDataTypeComparisonTable.put("Long", "java.lang.Long");
        basicDataTypeComparisonTable.put("Float", "java.lang.Float");
        basicDataTypeComparisonTable.put("Double", "java.lang.Double");
        basicDataTypeComparisonTable.put("String", "java.lang.String");
    }

    /**
     * 判断execution表达式是否是合法的
     * @param execution
     * @return
     */
    public static boolean isLegalExecution(String execution) {
        Matcher matcher = executionRegex.matcher(execution);
        return matcher.matches();
    }


    /**
     * 取得executionE的正则表达式对象。
     *
     * 方法分五部分
     * private static java.lang.String com.study.User.search(java.lang.String,java.util.Date) throws java.lang.Exception
     * 以上面的方法为例子
     * 1、访问权限类型：private
     * 2、其它可选修饰符：static final synchronized 等等
     * 3、返回类型：java.lang.String
     * 4、全限定类名.方法(形参)：com.study.User.search(java.lang.String,java.util.Date)
     * 5、异常：throws java.lang.Exception
     * 最终拼凑的正则表达式必须包含这五部分，其中1、2、5为可选的，正则表达是应该给这三项的次数限定为零次或一次。
     * 方法匹配正则表达式：
     * ^(((public|private|default|protected) )?((\w+ )*)?(((\w|\.)+|\*) ){1}(\w|\*|\.)+\((\w|,|\.)*\)( throws (\w|\.|,)+)?)$
     *
     * 检测execution表达式合法性：
     * ^(((public|private|default|protected|\\*) )?((\\w+ )*|\\* )?(((\\w|\\.)+|\\*) )(\\w|\\*|\\.)+\\((\\w|,|\\.| )+\\)( (\\w|\\.|\\*)+)?)$
     *
     * execution表达式为四部分（这里融合全限定类名和方法）
     * 1、访问权限类型+其它修饰符（可选）             ((public|private|default|protected|\\*) )?((\\w+ )*|\\* )?
     * 2、返回值类型（必选）              (((\\w|\\.)+|\\*) )
     * 3、全限定类名（可选）+ 方法（必选）  (\\w|\\*|\\.)+\\((\\w|,|\\.| )+\\)
     * 4、异常类型（可选）               ( (\\w|\\.|\\*)+)?
     *
     * 由于全限定类名和方法是粘连在一起的，所以最少可以拆分成两个部分，
     * 因为其它修饰符数量理论无上限，所以拆分的数量理论上也无上限。
     * @param executionE
     * @return
     */
    public static Pattern getExecutionERegex(String executionE) {
        String modifiersPattern = MODIFIERS_PATTERN; // 访问权限
        String optionalModifiersPattern = OPTIONAL_MODIFIERS_PATTERN; // 可选修饰符
        String retTypePattern = RET_TYPE_PATTERN; // 返回值类型
        String namePattern = NAME_PATTERN; // 全限定类名.方法名(形参)
        String throwPattern = THROW_PATTERN; // 异常

        // 通过空格拆分判断哪些要匹配
        String[] strings = executionE.split(" ");
        if (strings.length == 2) {

            // 替换返回值类型
            retTypePattern = editRetTypePattern(strings[0]);

            // 取出形参
            namePattern = editNamePattern(strings[1]);

        } else if (strings.length == 3) {

            // 替换访问权限类型
            // * * com.study.User.setName(java.lang.String)
            // 明明可以省略访问权限类型的却非要用个*号，上面这种还好，恰好第一个就是想要的访问权限类型
            // 但是如果是这种奇怪的写法：* com.study.User.setName(java.lang.String) java.lang.IllegalStateException
            // 第一个不是预期的访问权限类型，而是代表返回类型的*就很淦了，那么还需要判断拆分三个字符串，第二个是否包含()，
            // 如果包含就是奇怪写法，如果不包含就是预期写法。
            // 包含访问权限类型
            if (accessRightRegex.matcher(strings[0]).matches()
                    && strings[1].indexOf("(") == -1 && !strings[1].endsWith(")")) {

                modifiersPattern = editModifiersPattern(strings[0]); // 替换访问权限类型
                retTypePattern = editRetTypePattern(strings[1]);
                namePattern = editNamePattern(strings[2]);

            } else {
                // 不包含访问权限类型，那么就包含抛出什么类型的异常
            }

        } else if (strings.length == 4){
            // 所有参数都写了

        } else {
            throw new IllegalStateException("execution表达式有误！" + executionE);
        }


        executionE = "^(" + modifiersPattern + optionalModifiersPattern + retTypePattern + namePattern + throwPattern + ")&";

        System.out.println(executionE);

        return Pattern.compile(executionE);
    }

    /**
     * 编辑访问权限类型
     * @param modifiersPattern
     * @return
     */
    private static String editModifiersPattern(String modifiersPattern) {
        if (!("*".equals(modifiersPattern))) {
            return "(" + modifiersPattern + " )"; // 注意反括号前应该有空格
        }

        return MODIFIERS_PATTERN;
    }

    /**
     * 编辑返回类型
     * @param retTypePattern
     * @return
     */
    private static String editRetTypePattern(String retTypePattern) {

        return retTypePattern.replace("..", "(\\\\w|\\\\.)+")
                .replace("*", "(\\\\w|\\\\.)*");
    }

    /**
     * 编辑全限定类名.方法(形参)
     * @param namePattern
     * @return
     */
    private static String editNamePattern(String namePattern) {
        // 取出形参
        String params = namePattern.substring(namePattern.indexOf("(") + 1, namePattern.indexOf(")"));
        namePattern = namePattern.substring(0, namePattern.indexOf("("));
        if ("..".equals(params)) {
            params = "\\\\((\\w|,|\\.| )+\\\\)";
        } else {
            String[] args = params.split(",");
            params = "";
            for (String arg : args) {
                arg = arg.strip();
                String value = basicDataTypeComparisonTable.get(arg);
                if (value != null) {
                    arg = value;
                } else {
                    arg = arg.replace("*", "(\\\\w)*")
                            .replace("..", "(\\\\.(\\\\w)+)+\\\\.");
                }
                params += arg + ",";
            }
            params = params.substring(0, params.length() - 1);
        }

        // 判断是否是直接的方法名
        if (methodRegex.matcher(namePattern).matches()) {
            namePattern = namePattern.replace("*", "(\\\\w)*")
                    .replace("(..)", "\\\\((\\\\w|,|\\\\.| )+\\\\)");
        } else {
            // 有全限定类名
            namePattern = namePattern.replace("*", "(\\\\w)*")
                    .replace("..", "(\\\\.(\\\\w)+)+\\\\.");
        }

        // 返回拼接的结果
        return namePattern + params;
    }
}
