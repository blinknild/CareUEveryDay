package cn.ofpp.core;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 从 {@code n2-grammar.json} 读取 N2 语法，按「北京日历日」选取一条：
 * 当日 {@code day % 10} 与条目的 {@code id % 10} 相同；同一余数多条时按月份与日期轮转。
 */
public final class N2GrammarPicker {

    /** 与天气、业务日一致，按中国日期选语法 */
    public static final ZoneId DAY_ZONE = ZoneId.of("Asia/Shanghai");

    private static final List<N2GrammarItem> ITEMS = loadItems();

    private N2GrammarPicker() {
    }

    private static List<N2GrammarItem> loadItems() {
        try {
            String json = ResourceUtil.readUtf8Str("n2-grammar.json");
            return Collections.unmodifiableList(
                    JSONUtil.toList(JSONUtil.parseObj(json).getJSONArray("items"), N2GrammarItem.class));
        } catch (Exception e) {
            System.err.println("读取 n2-grammar.json 失败: " + e.getMessage());
            return List.of(fallbackItem());
        }
    }

    private static N2GrammarItem fallbackItem() {
        N2GrammarItem it = new N2GrammarItem();
        it.setId(0);
        it.setTitle("（未加载）");
        it.setPattern("-");
        it.setExample("请检查 classpath 下 n2-grammar.json");
        return it;
    }

    /**
     * 按 {@link #DAY_ZONE} 的「今天」选取一条语法。
     */
    public static N2GrammarItem pickForToday() {
        return pickFor(LocalDate.now(DAY_ZONE));
    }

    /**
     * @param date 日历日（通常传入 {@link LocalDate#now(ZoneId)} 使用 {@link #DAY_ZONE}）
     */
    public static N2GrammarItem pickFor(LocalDate date) {
        if (ITEMS.isEmpty()) {
            return fallbackItem();
        }
        int day = date.getDayOfMonth();
        int remainder = day % 10;
        List<N2GrammarItem> matches = new ArrayList<>();
        for (N2GrammarItem item : ITEMS) {
            if (Math.floorMod(item.getId(), 10) == remainder) {
                matches.add(item);
            }
        }
        if (matches.isEmpty()) {
            return ITEMS.get(Math.floorMod(day - 1, ITEMS.size()));
        }
        int idx = Math.floorMod(date.getMonthValue() * 31 + day - 1, matches.size());
        return matches.get(idx);
    }
}
