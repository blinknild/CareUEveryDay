package cn.ofpp.core;

/**
 * JLPT N2 语法配置项，与 {@code n2-grammar.json} 中字段对应。
 */
public class N2GrammarItem {

    private int id;
    private String title;
    private String pattern;
    private String example;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
