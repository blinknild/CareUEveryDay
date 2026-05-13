package cn.ofpp;

import cn.hutool.core.util.StrUtil;
import cn.ofpp.core.Wx;

/**
 * 引导配置
 *
 * @author DokiYolo
 * Date 2022-08-22
 */
public class Bootstrap {

    /**
     * 公众号AppID
     */
    public static final String APP_ID = "wxe2dc9885e82d223a";

    /**
     * 公众号秘钥
     */
    public static final String SECRET = "54aa8fda19c60b7655b0da3bc3739525";

    /**
     * 全局模板ID  也可针对单个Friend指定模板
     */
    public static final String TEMPLATE_ID = "pnJwNjqLSeCWDRmvyCwQPjwdjXdwLG8ezMTVbqiaeJQ";

    /**
     * 为 true 时，N2 相关三个模板变量会经 {@link cn.ofpp.core.WeChatThingText#limitForThing} 做约 20 码点截断（面向公众号后台里
     * {@code thing}/{@code thing1} 等短字段）。该截断在本地按码点截断，无法阻止微信服务端对 thing 再截断，故仍可能出现「~基」类残缺。
     * <p>
     * 若你已在公众平台把「今日 N2 / 接续 / 例句」改成<strong>短语</strong>等更长类型，请设为 {@code false}，将直接发送 JSON 中的完整日文
     * （SDK 侧不会对假名做过滤）。
     */
    public static final boolean N2_APPLY_THING_CHAR_LIMIT = true;

    /**
     * 为 true 时，「今日 N2 / 接续 / 例句」三格固定为极简日文（不读 JSON），用于排查推送或编码问题。
     * 确认正常后请改回 {@code false}。
     */
    public static final boolean N2_MINIMAL_JP_TEST = true;

    /**
     * 初始化
     */
    public static void init() {
        if (StrUtil.hasEmpty(APP_ID, SECRET, TEMPLATE_ID)) {
            throw new IllegalArgumentException("请检查配置");
        }
        Wx.init();
    }

}
