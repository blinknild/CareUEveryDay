package cn.ofpp.core;

import cn.hutool.core.util.StrUtil;

/**
 * 公众号模板里若关键词为 {@code thing*} 类型，官方限制单字段 value 最多约 20 个「字符」；
 * 超长会被服务端截断，易出现「〜に基づいて」变成「~基」这类残缺。
 */
public final class WeChatThingText {

    /** 与微信文档中 thing 类型上限对齐，略保守 */
    public static final int THING_MAX_CODE_POINTS = 20;

    private WeChatThingText() {
    }

    /**
     * 去除换行、将全角波浪改为半角（减少异常符号风险），并按 Unicode 码点截断到上限内。
     */
    public static String limitForThing(String raw) {
        if (StrUtil.isBlank(raw)) {
            return "-";
        }
        String t = raw.replace('\r', ' ').replace('\n', ' ').replace('〜', '~').trim();
        if (t.isEmpty()) {
            return "-";
        }
        int[] cps = t.codePoints().toArray();
        if (cps.length <= THING_MAX_CODE_POINTS) {
            return t;
        }
        StringBuilder sb = new StringBuilder(THING_MAX_CODE_POINTS);
        for (int i = 0; i < THING_MAX_CODE_POINTS; i++) {
            sb.appendCodePoint(cps[i]);
        }
        return sb.toString();
    }
}
