# 中国象棋 Android App

> 🎮 专为新手设计的中国象棋应用，本地运行，无需网络，带步骤提示和风险警告！

---

## 📱 快速获取APK（最简单）

### 方法一：使用 GitHub Actions 自动构建（推荐）✅

**只需要3分钟，完全免费！**

1. **注册 GitHub 账号** (如果还没有)
   - 访问 https://github.com

2. **创建仓库**
   - 点击 "+" → "New repository"
   - 仓库名：`ChineseChess`
   - 设为 Public

3. **上传文件**
   - 把 `/workspace` 文件夹下的所有文件上传到仓库
   - 或者使用 Git 命令

4. **等待自动构建**
   - 上传后，GitHub Actions 会自动开始构建（2-5分钟）
   - 点击仓库顶部的 "Actions" 标签查看进度
   - 构建成功后，在 Artifacts 中下载 APK

---

### 方法二：使用 Android Studio 构建（电脑）

1. 下载 Android Studio：https://developer.android.com/studio
2. 打开这个项目目录
3. 点击 Build → Build APK(s)

详细步骤请看：[手机安装指南.md](file:///workspace/手机安装指南.md)

---

## ✨ 应用功能

### 🎯 游戏功能
- ✅ 完整的中国象棋规则
- ✅ 双人对弈
- ✅ 本地运行，无需网络
- ✅ 悔棋功能
- ✅ 重新开始

### 🌟 新手友好
- ✅ 选中棋子后，绿色圆点显示可移动位置
- ✅ 被威胁的棋子显示红色边框
- ✅ 点击提示按钮，蓝色箭头显示推荐走法
- ✅ 将军时显示警告信息

### ⚙️ 可配置
- ✅ 可切换是否显示提示
- ✅ 可切换是否显示风险警告

---

## 📁 项目文件

```
/workspace/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/example/chineschess/
│   │   │   ├── ChessBoard.java       # 游戏逻辑
│   │   │   ├── ChessBoardView.java   # 棋盘视图
│   │   │   ├── ChessPiece.java       # 棋子类
│   │   │   ├── HintManager.java      # 提示系统
│   │   │   └── MainActivity.java     # 主界面
│   │   └── res/
│   │       ├── layout/
│   │       ├── values/
│   │       └── xml/
│   └── build.gradle
├── .github/workflows/build.yml       # GitHub Actions 构建脚本
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
├── APK构建指南.md                   # 📖 详细构建指南
└── 手机安装指南.md                   # 📱 安装说明
```

---

## 🛠️ 开发配置

- **最低 Android 版本**: 5.0 (API 21)
- **目标 Android 版本**: 13 (API 33)
- **开发语言**: Java 8
- **使用国内镜像源**，构建速度快！

---

## 🎉 项目特色

1. **完整的中国象棋规则** - 所有棋子移动规则都正确实现
2. **新手友好设计** - 视觉化提示，降低学习门槛
3. **无网络依赖** - 完全本地运行，保护隐私
4. **可定制设置** - 根据需要开关提示功能

---

## 📖 更多文档

- [APK构建指南.md](file:///workspace/APK构建指南.md) - 详细的构建教程
- [手机安装指南.md](file:///workspace/手机安装指南.md) - 如何安装到手机

---

## 📋 快速开始（GitHub Actions）

### 1️⃣ 上传项目到 GitHub
```bash
# 如果你使用 Git
cd /workspace
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/你的用户名/ChineseChess.git
git push -u origin main
```

### 2️⃣ 下载 APK
- 打开你的 GitHub 仓库
- 点击 Actions 标签
- 等待构建完成（自动开始）
- 在 Artifacts 里下载 `chinese-chess-apk`
- 解压得到 `app-debug.apk`

### 3️⃣ 安装到手机
- 把 APK 传到手机
- 允许未知来源应用
- 点击安装！

---

Enjoy the game! 🎊
