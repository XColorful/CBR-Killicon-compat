# 自定义大逃杀击杀图标兼容 | CBR Killicon compat

[中文](#自定义大逃杀击杀图标兼容) | [English](#cbr-killicon-compat)

**停止维护说明**：由于[GD656击杀图标](https://github.com/MinecraftGD656/gd656killicon)非标准的项目结构极难进行标准化适配与长期维护，且截至 1.1.1.002 仍未提供正式 MIT LICENSE 文件，故本兼容模组现已停止开发。

**Deprecation Notice**: Due to the non-standard project structure of [GD656Killicon](https://github.com/MinecraftGD656/gd656killicon), which makes standardized maintenance unfeasible, and the absence of a formal MIT LICENSE file as of version 1.1.1.002, this compatibility mod is no longer under development.

# 自定义大逃杀击杀图标兼容

本模组使[GD656击杀图标](https://www.curseforge.com/minecraft/mc-mods/gd656killicon)兼容[自定义大逃杀](https://github.com/XColorful/BattleRoyale)的机制

---

`该模组需要安装在服务端`

## 主要功能

### 移除击杀图标判定

- 移除了击杀图标的全部服务端判定逻辑

### 兼容自定义大逃杀

- 对游戏玩家造成伤害时，发送声音提示
- 击倒游戏玩家时，发送击杀提示
- 淘汰游戏玩家时，发送爆头击杀提示

# CBR Killicon compat

This mod provides compatibility between [GD656Killicon](https://www.curseforge.com/minecraft/mc-mods/gd656killicon) and [Custom BattleRoyale](https://github.com/XColorful/BattleRoyale).

`This mod needs to be installed on the server.`

## Features

### Killicon Logic Override

- Disabled all server-side judgment logic from the original Killicon mod to prevent conflict with BattleRoyale mechanics.

### BattleRoyale Integration

- Trigger hit sound feedback when damaging game players.
- Send kill notifications upon knocking down game players.
- Send headshot kill notifications upon eliminating game players.