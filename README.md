# 自定义大逃杀击杀图标兼容 | CBR Killicon compat

[中文](#自定义大逃杀击杀图标兼容) | [English](#cbr-killicon-compat)

# 自定义大逃杀击杀图标兼容

本模组使[GD656击杀图标](https://www.curseforge.com/minecraft/mc-mods/gd656killicon)兼容[自定义大逃杀](https://github.com/XColorful/BattleRoyale)的机制

本模组目前处于**早期开发阶段**，仅适配`gd656killicon-1.0.9.002-1.20.1-forge.jar`

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

This project is currently in the **early stages of development**. It is only compatible with `gd656killicon-1.0.9.002-1.20.1-forge.jar`.

`This mod needs to be installed on the server.`

## Features

### Killicon Logic Override

- Disabled all server-side judgment logic from the original Killicon mod to prevent conflict with BattleRoyale mechanics.

### BattleRoyale Integration

- Trigger hit sound feedback when damaging game players.
- Send kill notifications upon knocking down game players.
- Send headshot kill notifications upon eliminating game players.