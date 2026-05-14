# 由于最近请求量过多 该秘钥被高德封禁了 后续大家只能使用自己的高德秘钥啦

## 近日发现代码中的高德地图的配额每天几乎都超限了(一天5000配额)..建议大家申请自己的高德地图秘钥进行使用 避免因为超限导致的调用失败
[点我进入高德开放平台](https://lbs.amap.com)  进行申请，申请成功后大家修改 `/src/main/java/cn/ofpp/core/GaodeUtil.java` 这个文件中的key为自己申请的key即可

### 更新记录:  
0829: 修复动态古诗词  
0826: 临时移除一言随机古诗的动态获取，有超时问题等待换接口  

  
  

最近在抖音上的看到挺多人发的
然后就自己随便做了一个玩了一下

本项目不需要服务器
基于 Github Actions 实现每天固定运行一次；工作流按 **UTC** 调度，北京时间 = UTC+8。

`友情提示：master 分支 Github Actions 为每天北京时间 20:00 左右执行一次（cron 对应 UTC 12:00；实际触发可能有数分钟漂移）`
`如果需要监听推送执行 请切换workflow_on_push分支`

博客可能有些慢 我将内容移植到了语雀笔记 国内访问会很快
[点击访问语雀笔记](https://www.yuque.com/docs/share/66a239d8-f272-4a90-81bc-b2f5e30ccce6#3cfdeb22)


为了照顾小白，特地写了一篇完整详细的详细教程放到了我的博客以供参考
[点击访问我的博客](https://blog.ofpp.cn)

由于 博客服务器托管在GitPage节点在国外且前段时间cdn到期了 可能会访问比较慢～

![效果展示](https://static.marketup.cn/marketup/company/151/2022/822/cu/2809847835962369/20220822233123039-kesngm1h01iu.jpeg)
 
 
#### 公众号模板内容 可以根据自己的需要变更内容
```text
你叫{{friendName.DATA}}
今年{{howOld.DATA}}
距离下一次生日{{nextBirthday.DATA}}天
具体我们的下一次纪念日{{nextMemorialDay.DATA}}天
现在在{{province.DATA}}{{city.DATA}}
当前天气{{weather.DATA}}
当前气温{{temperature.DATA}}
风力描述{{winddirection.DATA}}
风力级别{{windpower.DATA}}
空气湿度{{humidity.DATA}}
今日N2{{n2Title.DATA}}
接续{{n2Pattern.DATA}}
例句{{n2Example.DATA}}
{{author.DATA}}
{{origin.DATA}}
{{content.DATA}}
```
其中
```text
{{author.DATA}}
{{origin.DATA}}
{{content.DATA}}
```
是古诗的变量，如果`Bootstrap`中配置未开启随机古诗 那么这三个就是不要的

`今日N2 / 接续 / 例句` 三个变量来自 `src/main/resources/n2-grammar.json`：按**中国时区当天日期**的「日」对 10 取余，与条目的 `id` 对 10 取余相同则入选；同一余数多条时在月内轮转。公众号后台模板里需增加对应关键字 `n2Title`、`n2Pattern`、`n2Example`（与代码中 `MessageFactory` 一致）。

**重要：** 若你在公众号模板里把这三项设成了 **`thing`（事物）** 类型，微信规定 **每个 value 最多约 20 个字符**，超长会被**硬截断**（例如「〜に基づいて」只显示成「~基」）。本项目已用 `WeChatThingText` 做码点级截断，并把 JSON 里的接续、例句改成短句以尽量整段显示。若仍不够长，请在公众平台把对应关键词改为允许更长内容的类型，或拆成多个 `thing` 字段分段展示。


#### 核心类介绍

```
Application.java          // 启动类
Bootstrap.java            // 一些启动配置(公众号信息在这里配置)
core/MessageFactory.java  // 创建微信消息对象的代码就在这里面 有需要可以自己修改下变量
src/main/resources/n2-grammar.json  // JLPT N2 语法与例句（可自增条目）
core/N2GrammarPicker.java // 按日期从 JSON 选取一条语法
core/WeChatThingText.java // 公众号 thing 类字段约 20 字截断与符号处理
```
