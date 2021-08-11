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

    // 数据类型对照表
    private final static Map<String, String> basicDataTypeComparisonTable = new HashMap<>();

    /**
     * execution表达式合法性检测匿名内部类
     */
    private final static class ExecutionERegex {
        // execution表达式合法性检测的正则表达式
        final private static String EXECUTION_E = "^(( *(public|private|default|protected) +)?( *(((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)|(\\*)) +((((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)|(\\*)|(\\*.\\*))\\(((\\.\\.)|( *((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+(( *, *((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)+ *)?))\\)( +((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)? *)+(( *, *((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)+ *)?)?)$";

        // 方法（字符串）正则表达式，*转(\w)*一定在.转和..转之前，所以在*转后判断是否为方法名就需要把\*替换为\(\\w\)\*
        final private static String STRING_REGEX = "^(((\\(\\\\w\\)\\*)?\\w+(\\(\\\\w\\)\\*)?)+)$";

        // 访问权限正则表达式
        final private static String ACCESS_RIGHT = "^( *(public|private|default|protected))";

        // 方法定义正则表达式
        final private static String NAME_PATTERN = "((((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)|(\\*)|(\\*.\\*))\\(((\\.\\.)|( *((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+(( *, *((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)+ *)?))\\)";

        // 异常正则表达式
        final private static String THROW_PATTERN = "( +((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)? *)+(( *, *((\\*\\.)?((\\w|\\*)\\.\\w|\\w|\\w\\*|\\.\\*|(\\*|\\w)\\.\\.\\w)(\\.\\.\\*)?)+)+ *)?)$";

        // execution合法性检测的正则表达式对象
        final public static Pattern executionRegex = Pattern.compile(EXECUTION_E);

        // 方法正则表达式对象
        final public static Pattern methodRegex = Pattern.compile(STRING_REGEX);

        // 访问权限正则表达式对象
        final public static Pattern accessRightRegex = Pattern.compile(ACCESS_RIGHT);

        // 方法定义正则表达式对象
        final public static Pattern nameRegex = Pattern.compile(NAME_PATTERN);

        // 异常正则表达式对象
        final public static Pattern throwRegex = Pattern.compile(THROW_PATTERN);

    }

    /**
     * execution特殊符号转正则表达式匿名内部类
     */
    private final static class ETransitionRegex {
        // 访问权限类型：((public|private|default|protected) )?
        private final static String MODIFIERS_PATTERN = "((public|private|default|protected) )?";

        // 其它修饰符：((\\w+ )*)?
        private final static String OPTIONAL_MODIFIERS_PATTERN = "((\\w+ )*)?";

        // 返回类型：(((\\w|\\.)+|\\*) )
        private final static String RET_TYPE_PATTERN = "(((\\w|\\.)+) )";

        // 全限定类名.方法（这里没有形参）：(\\w|\\*|\\.)+\\((\\w|,|\\.)*\\)
        private final static String NAME_PATTERN = "(\\w|\\*|\\.)+";

        // 异常：( throws (\\w|\\.|,)+)?
        private final static String THROW_PATTERN = "( throws (\\w|\\.|,)+)?";

        // 字符匹配（把*替换为正则表达式）
        final private static String CHART_MATCHING = "(\\w)*";

        // 包匹配（把..替换为正则表达式）
        final private static String PACKAGE_MATCHING = "(\\.(\\w)+\\.)+";

        // 任意形参匹配
        final private static String ARBITRARILY_FORMAL = "(\\w|,|\\.)*";

        // 包名的.需要转义
        final private static String PACKAGE_POINT = "\\.";
    }

    /**
     * 初始化数据类型对照表
     */
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

    private AspectExecutionEUtil() {}

    /**
     * 判断execution表达式是否是合法的
     * @param execution
     * @return
     */
    public static boolean isLegalExecution(String execution) {
        Matcher matcher = ExecutionERegex.executionRegex.matcher(execution);
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
     *
     * 检测execution表达式合法性：
     * @see ExecutionERegex#EXECUTION_E
     *
     * execution表达式为四部分（这里融合全限定类名和方法）
     * 1、访问权限类型（可选）+其它修饰符（隐性的默认全匹配）
     * 2、返回值类型（必选）
     * 3、全限定类名（可选）+ 方法（必选）
     * 4、异常类型（可选）
     *
     * execution部分符号转正则表达式
     * 左边为execution表达式，右边为正则表达式
     * *转：* 等价于 (\w)*
     * .转：. 等价于 \.
     * ..转：.. 等价于 (\.(\w)+\.)+
     * 转换顺序一定严格按照* . ..的顺序 且在进行..转时 ..需要替换为\.\.
     *
     * 由于全限定类名和方法是粘连在一起的，所以最多可以拆分成四个部分，
     * 开发人员不写其它修饰符的匹配，隐性默认全匹配。
     * @param executionE
     * @return
     */
    public static Pattern getExecutionERegex(String executionE) {
        String modifiersPattern = ""; // 访问权限
        String retTypePattern = ""; // 返回值类型
        String namePattern = ""; // 方法声明
        String throwPattern = ""; // 异常

        // 不能单纯的用根据空格对execution表达式进行拆分
        // 例如：public void com.study.User.edit(java.lang.String, int) java.lang.Exception  ,  java.lang.IllegalAccessError
        // 多个形参之间、多个异常之间和不同部分允许开发人员敲多个空格，
        // 这样就需要增强空格拆分或分开进行正则匹配。
        Matcher accessRightMatcher = ExecutionERegex.accessRightRegex.matcher(executionE);
        Matcher nameMatcher = ExecutionERegex.nameRegex.matcher(executionE);
        Matcher throwMatcher = ExecutionERegex.throwRegex.matcher(executionE);

        // 匹配访问权限，不是完全匹配所以用find()
        if (accessRightMatcher.find()) {
            // 有访问权限，取出访问权限
            modifiersPattern = accessRightMatcher.group(0).strip();

            if (modifiersPattern == null || "".equals(modifiersPattern)) {
                throw new IllegalStateException("访问权限匹配失败！" + modifiersPattern);
            }
        }

        // 匹配方法定义，不是完全匹配所以用find()
        if (nameMatcher.find()) {
            // 方法定义匹配上了
            namePattern = nameMatcher.group(0).strip();

            if (namePattern == null || "".equals(namePattern)) {
                throw new IllegalStateException("方法定义匹配失败！" + namePattern);
            }
        } else {
            throw new IllegalStateException("execution表达式有误！" + executionE);
        }

        // 匹配异常，不是完全匹配所以用find()
        if (throwMatcher.find()) {
            // 异常匹配上了
            throwPattern = throwMatcher.group(0).strip();

            if (throwPattern == null || "".equals(throwPattern)) {
                throw new IllegalStateException("异常匹配失败！" + throwPattern);
            }
        }

        // 由于返回类型的匹配模式会匹配到其它部分，
        // 所以索性把匹配好了的其它部分从整个表达式扣除去，
        // 剩下的就是返回类型的execution表达式了。
        retTypePattern = executionE.replace(modifiersPattern, "")
                .replace(namePattern, "")
                .replace(throwPattern, "")
                .strip();

        if ("".equals(retTypePattern)) {
            throw new IllegalStateException("返回类型匹配失败！");
        }

        modifiersPattern = editModifiersPattern(modifiersPattern); // 编辑访问权限正则表达式
        retTypePattern = editRetTypePattern(retTypePattern); // 编辑返回类型的正则表达式
        namePattern = editNamePattern(namePattern); // 编辑方法声明的正则表达式
        throwPattern = editThrowPattern(throwPattern); // 编辑异常的正则表达式

        executionE = "^(" + modifiersPattern + ETransitionRegex.OPTIONAL_MODIFIERS_PATTERN + retTypePattern + namePattern + throwPattern + ")$";

        return Pattern.compile(executionE);
    }

    /**
     * 编辑异常正则表达式
     * @param throwPattern
     * @return
     */
    private static String editThrowPattern(String throwPattern) {
        if ("".equals(throwPattern)) {
            return ETransitionRegex.THROW_PATTERN;
        }

        // 按,号拆分异常
        String[] split = throwPattern.split(",");
        throwPattern = "";
        for (String tr : split) {
            tr = tr.strip();
            // 替换符号
            tr = tr.replace("*", ETransitionRegex.CHART_MATCHING)
                    .replace(".", ETransitionRegex.PACKAGE_POINT) // 替换包名之间的.
                    // 如果是..（子包）已经替换为了\.\.，所以两个连着的\.\.表示子包匹配
                    .replace("\\.\\.", ETransitionRegex.PACKAGE_MATCHING);

            throwPattern += tr + ",";
        }

        throwPattern = "( throws" + throwPattern.substring(0, throwPattern.length() - 1) + ")?";

        return throwPattern;
    }

    /**
     * 编辑访问权限类型
     * @param modifiersPattern
     * @return
     */
    private static String editModifiersPattern(String modifiersPattern) {
        if (!("*".equals(modifiersPattern) || "".equals(modifiersPattern))) {
            return "(" + modifiersPattern + " )"; // 注意反括号前应该有空格
        }

        return ETransitionRegex.MODIFIERS_PATTERN;
    }

    /**
     * 编辑返回类型
     * @param retTypePattern
     * @return
     */
    private static String editRetTypePattern(String retTypePattern) {
        // 尝试从基本数据类型包装类集合中取得全限定类名
        String retType = basicDataTypeComparisonTable.get(retTypePattern);
        if (retType != null) {
            retTypePattern = retType;
        } else if ("*".equals(retTypePattern)) {
            return ETransitionRegex.RET_TYPE_PATTERN;
        } else {
            retTypePattern = retTypePattern.replace("*", ETransitionRegex.CHART_MATCHING)
                    .replace(".", ETransitionRegex.PACKAGE_POINT) // 替换包名之间的.
                    // 如果是..（子包）已经替换为了\.\.，所以两个连着的\.\.表示子包匹配
                    .replace("\\.\\.", ETransitionRegex.PACKAGE_MATCHING);

        }
        return retTypePattern + " ";
    }

    /**
     * 编辑方法声明
     * 因为通过反射得到的方法名一定是符合格式的，
     * 所以在做匹配的时候就不用过于严谨，
     * 否则正则表达式会变得十分冗余
     * @param namePattern
     * @return
     */
    private static String editNamePattern(String namePattern) {
        // 取出形参
        String params = namePattern.substring(namePattern.indexOf("(") + 1, namePattern.indexOf(")"));
        namePattern = namePattern.substring(0, namePattern.indexOf("("));
        if ("..".equals(params)) {
            params = ETransitionRegex.ARBITRARILY_FORMAL;
        } else {
            String[] args = params.split(",");
            params = "";
            for (String arg : args) {
                // 下面两条代码才是用这此循环的目的，
                // 开发人员习惯性的会在形参之间打个空格，所以这里要去除空格,
                // 还有基本数据类型的封装类要与全限定名对应.
                arg = arg.strip();
                String value = basicDataTypeComparisonTable.get(arg);

                if (value != null) {
                    arg = value;
                } else {
                    // 反正都循环了，就在这里顺便替换符号了
                    arg = arg.replace("*", ETransitionRegex.CHART_MATCHING)
                            .replace(".", ETransitionRegex.PACKAGE_POINT) // 替换包名之间的.
                            // 如果是..（子包）已经替换为了\.\.，所以两个连着的\.\.表示子包匹配
                            .replace("\\.\\.", ETransitionRegex.PACKAGE_MATCHING);
                }
                params += arg + ",";
            }
            params = params.substring(0, params.length() - 1);
        }


        if ("*".equals(namePattern)) {
            namePattern = ETransitionRegex.NAME_PATTERN;
        } else {
            // 替换符号
            namePattern = namePattern.replace("*", ETransitionRegex.CHART_MATCHING);
            // 判断是否是直接的方法名
            if (ExecutionERegex.methodRegex.matcher(namePattern).matches()) {
                // 省略了全限定类名，namePattern前如果没有*号则不能模糊匹配其它字符，
                // 可ETransitionRegex.NAME_PATTERN却是通用的，没有进行严格判断，
                // 所以需要在二者之间加一个ETransitionRegex.PACKAGE_POINT
                namePattern = ETransitionRegex.NAME_PATTERN + ETransitionRegex.PACKAGE_POINT + namePattern;
            } else {
                // 有全限定类名
                namePattern = namePattern
                        .replace(".", ETransitionRegex.PACKAGE_POINT)
                        .replace("\\.\\.", ETransitionRegex.PACKAGE_MATCHING);
            }
        }

        // 返回拼接的结果
        return namePattern + "\\(" + params + "\\)";
    }
}
