package xml;

import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * JSON 转换 该程序将XML文档显示为JSON格式的树。
 *
 * @author zt1994 2019/5/23 22:35
 */
public class JSONConverter {

    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String filename;
        if (args.length == 0) {
            try (Scanner in = new Scanner(System.in)) {
                System.out.print("输入文件绝对路径：");
                filename = in.nextLine();
            }
        } else {
            filename = args[0];
        }
        // 1、要读入一个XML文档，首先需要一个DocumentBuilder对象，从DocumentBuilderFactory中获取
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        Document document = builder.parse(filename);
        // document.getDocumentElement() 获取根元素
        Element root = document.getDocumentElement();
        System.out.println(convert(root, 0));
    }


    /**
     * 转换XML文档
     *
     * @param node  节点
     * @param level 节点等级
     * @return
     */
    public static StringBuilder convert(Node node, int level) {
        if (node instanceof Element) {
            return elementObject((Element) node, level);
        } else if (node instanceof CharacterData) {
            return characterString((CharacterData) node, level);
        } else {
            return pad(new StringBuilder(), level).append(jsonEscape(node.getClass().getName()));
        }
    }


    /**
     * 替换MAP
     */
    private static Map<Character, String> replacements = Map.of('\b', "\b", '\f', "\\f", '\n', "\\n",
            '\r', "\\r", '\t', "\\t", '"', "\\\"", '\\', "\\\\");


    /**
     * 过滤不是json的内容 空格等字符
     *
     * @param str
     * @return
     */
    private static StringBuilder jsonEscape(String str) {
        StringBuilder result = new StringBuilder("\"");
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String replacement = replacements.get(ch);
            if (replacement == null) {
                result.append(ch);
            } else {
                result.append(replacement);
            }
        }
        result.append("\"");
        return result;
    }


    private static StringBuilder pad(StringBuilder builder, int level) {
        for (int i = 0; i < level; i++) {
            builder.append("  ");
        }
        return builder;
    }


    /**
     * 字符String
     *
     * @param node
     * @param level
     * @return
     */
    private static StringBuilder characterString(CharacterData node, int level) {
        StringBuilder result = new StringBuilder();
        StringBuilder data = jsonEscape(node.getData());
        if (node instanceof Comment) {
            data.insert(1, "Comment: ");
        }
        pad(result, level).append(data);
        return result;
    }


    /**
     * 属性对象
     *
     * @param attrs
     * @return
     */
    private static StringBuilder attributeObject(NamedNodeMap attrs) {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < attrs.getLength(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(jsonEscape(attrs.item(i).getNodeName()));
            result.append(": ");
            result.append(jsonEscape(attrs.item(i).getNodeValue()));
        }
        result.append("}");
        return result;
    }


    /**
     * 元素对象
     *
     * @param element
     * @param level
     * @return
     */
    private static StringBuilder elementObject(Element element, int level) {
        StringBuilder result = new StringBuilder();
        pad(result, level).append("{\n");
        pad(result, level + 1).append("\"name\": ");
        result.append(jsonEscape(element.getTagName()));
        // 获取当前元素所有属性
        NamedNodeMap attrs = element.getAttributes();
        if (attrs.getLength() > 0) {
            pad(result.append(", \n"), level + 1).append("\"attributes\": ");
            result.append(attributeObject(attrs));
        }
        // 获取当前元素所有子节点
        NodeList children = element.getChildNodes();
        if (children.getLength() > 0) {
            pad(result.append(",\n"), level + 1).append("\"children\": [\n");
            for (int i = 0; i < children.getLength(); i++) {
                if (i > 0) {
                    result.append(",\n");
                }
                result.append(convert(children.item(i), level + 2));
            }
            result.append("\n");
            pad(result, level + 1).append("]\n");
        }
        pad(result, level).append("}");
        return result;
    }

}
