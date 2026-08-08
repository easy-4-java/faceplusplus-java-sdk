# faceplusplus-java-sdk

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://github.com/easy-4-java/faceplusplus-java-sdk) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](https://www.apache.org/licenses/LICENSE-2.0.txt)

面向 Face++（旷视）人脸识别 API 的 Java SDK。以模板风格 API 提供人脸检测、分析、比对、搜索、皮肤分析与人脸库（FaceSet）管理，底层基于 OkHttp 3 与 Jackson。

## 目录

- [1. 项目概览](#1-项目概览)
- [2. 功能与状态](#2-features--status)
- [3. 环境要求与兼容性](#3-requirements--compatibility)
- [4. 架构与模块](#4-architecture--modules)
- [5. 安装](#5-installation)
- [6. 快速开始](#6-quick-start)
- [7. 配置](#7-configuration)
- [8. 核心用法 / API](#8-core-usage--api)
- [9. 测试与构建](#9-testing--build)
- [10. 版本线与分支](#10-versioning--branches)
- [11. 参与贡献与许可协议](#11-contributing--license)

## 1. 项目概览

`faceplusplus-java-sdk` 将 Face++ REST API（`/facepp/v3/*`、`/facepp/v1/skinanalyze*`）封装为精简的模板风格 API：`FaceppTemplate` 暴露类型化操作组，`FaceppFaceOperations` / `FaceppFacesetOperations` 基于 `FaceppOkHttp3Template`（OkHttp 3 + Jackson）实现调用，并使用类型化响应类建模 API 结果。

| 是什么 | 不是什么 |
|:---|:---|
| Face++ 人脸识别 API 的类型化客户端 | Spring Boot Starter（不含自动配置） |
| 同步 + 异步操作变体（人脸 / 人脸库） | 人脸检测算法实现（图片发送到 Face++ 云端） |
| 支持 URL / Base64 / 文件三种图片输入 | 通用 HTTP 客户端框架 |

典型使用场景：

| 场景 | 操作 |
|:---|:---|
| 人脸检测与分析 | `detectUrl/Base64/File`、`analyze` |
| 人脸比对 | `compareUrl/Token/Base64/File` |
| 在人脸库中搜索人脸 | `searchUrl/Token/Base64/File` |
| 人脸库管理 | `createFaceset`、`updateFaceset`、`getFacesetList`、`getFacesetByToken/OuterId`、`addFaceWithToken/OuterId`、`removeFaceByToken/OuterId`、`getFaceDetail` |
| 皮肤分析 | `skinAnalyzeUrl/Base64/File`（基础版 / 高阶版 / 专业版） |
| 异步批量人脸管理 | `FaceppFaceAsyncOperations` / `FaceppFacesetAsyncOperations` |

**项目状态：** 活跃开发。

<a id="2-features--status"></a>
## 2. 功能与状态

| 能力 | 状态 | 说明 |
|:---|:---|:---|
| `FaceppTemplate` | 可用 | 入口：`opsForFaceDetect()` / `opsForFaceset()` |
| `FaceppFaceOperations` | 可用 | 检测 / 分析 / 比对 / 搜索 / 皮肤分析，支持 URL、Base64 或 `File` 输入 |
| `FaceppFacesetOperations` | 可用 | 人脸库 CRUD、添加/删除人脸（token 或 outerId）、人脸详情、设置 user id |
| 异步变体 | 可用 | `FaceppFaceAsyncOperations`、`FaceppFacesetAsyncOperations` |
| `FaceppOkHttp3Template` | 可用 | OkHttp 3 + Jackson HTTP 层：`post` / `get` / `doRequest` 重载、类型化响应映射 |
| `FaceppProperties` | 可用 | Host、应用凭据、OSS 区域、视图尺寸、token 有效期（默认 3600 秒） |
| 类型化响应 | 可用 | `FaceDetectResponse`、`FaceCompareResponse`、`FaceSearchResponse`、`Faceset*Response`、`FaceppResponse.isSuccess()` 等 |
| 请求选项 | 可用 | `FaceDetectOptions`（关键点、属性、颜值分范围）、`FaceAnalyzeOptions`、`FaceSearchOptions`、`SkinAnalyzeOptions`、`FacesetBo` |
| 单元测试 | 无 | 仓库中无测试源码 |
| CI 流水线 | 未配置 | 仓库中无 CI 工作流文件 |

<a id="3-requirements--compatibility"></a>
## 3. 环境要求与兼容性

| 依赖项 | 版本 |
|:---|:---|
| JDK | 8 |
| Maven | 3.0+ |
| OkHttp | 4.9.3 |
| Jackson | 2.17.2（`jackson-databind`） |
| Face++ API | Face++ v3 人脸 API（`api-cn.faceplusplus.com`） |

### 版本线矩阵

| 分支 | JDK | 版本号模式 |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

<a id="4-architecture--modules"></a>
## 4. 架构与模块

```text
  业务代码                      faceplusplus-java-sdk               Face++ 云端
  --------                      ---------------------               -----------
  FaceppProperties  ->  FaceppTemplate
                             |
              +--------------+--------------+
              |                             |
      FaceppFaceOperations           FaceppFacesetOperations
      （+Async）                       （+Async）
              |                             |
              +------------> FaceppOkHttp3Template <------------+
                                 （OkHttp 3 + Jackson）            |
                                       |                         |
                                       +--> POST /facepp/v3/* ---+
                                              （api-cn.faceplusplus.com）
                                       |
                                       v
                              类型化响应类（resp/*）
```

单一模块，jar 打包：

| 包 | 职责 |
|:---|:---|
| `com.faceplusplus.spring.boot` | `FaceppTemplate`、`FaceppProperties`、`FaceppOkHttp3Template`、操作类、常量 |
| `com.faceplusplus.spring.boot.req` | 类型化请求选项（`FaceDetectOptions`、`FacesetBo` 等） |
| `com.faceplusplus.spring.boot.resp` | 类型化响应模型（`FaceppResponse` 基类，detect/compare/search/faceset 响应等） |

<a id="5-installation"></a>
## 5. 安装

### Maven

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>faceplusplus-java-sdk</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.easy4j:faceplusplus-java-sdk:2.0.x.x.20260630-SNAPSHOT'
```

**可用性：** 构件发布至阿里云私有 Maven 仓库，并通过 GitHub Releases 分发；尚未发布到 Maven Central。

<a id="6-quick-start"></a>
## 6. 快速开始

```java
import com.faceplusplus.spring.boot.FaceppOkHttp3Template;
import com.faceplusplus.spring.boot.FaceppProperties;
import com.faceplusplus.spring.boot.FaceppTemplate;
import com.faceplusplus.spring.boot.req.FaceDetectOptions;
import com.faceplusplus.spring.boot.resp.FaceDetectResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

FaceppProperties properties = new FaceppProperties();
properties.setAppId("your-app-id");
properties.setAppCertificate("your-api-key");

FaceppOkHttp3Template http = new FaceppOkHttp3Template(new OkHttpClient(), new ObjectMapper(), properties);
FaceppTemplate template = new FaceppTemplate(http, properties);

FaceDetectOptions options = FaceDetectOptions.builder()
        .returnLandmark(1)
        .returnAttributes("gender,age")
        .build();

FaceDetectResponse response = template.opsForFaceDetect()
        .detectUrl("https://example.com/face.jpg", options);

System.out.println("success=" + response.isSuccess());
System.out.println("faces=" + response.getFaces());
```

预期结果：检测到的人脸（按需返回关键点与属性）封装在类型化 `FaceDetectResponse` 中；`isSuccess()` 反映 Face++ 的 `error_message`/`code` 契约。

<a id="7-configuration"></a>
## 7. 配置

配置保存在 `FaceppProperties` 中：

| 属性 | 默认值 | 说明 |
|:---|:---|:---|
| `host` | `https://api-cn.faceplusplus.com` | API 基础地址 |
| `appId` | — | Face++ API Key |
| `appCertificate` | — | Face++ API Secret |
| `expirationTimeInSeconds` | `3600` | Token 有效期（秒） |
| `loginKey` / `loginSecret` | — | 登录凭据（可选） |
| `ossRegion` | — | 云存储 OSS 区域（可选） |
| `viewWidth` / `viewHeight` | — | 视图尺寸（可选） |

凭据由应用提供，请勿提交到版本控制。

<a id="8-core-usage--api"></a>
## 8. 核心用法 / API

### 8.1 人脸操作

```java
// 本地文件检测
FaceDetectResponse detect = template.opsForFaceDetect()
        .detectFile(new File("face.jpg"), options);

// 两张图片 URL 比对
FaceCompareResponse compare = template.opsForFaceDetect()
        .compareUrl("https://a.example/1.jpg", "https://b.example/2.jpg");

// 按 face_token 搜索
FaceSearchResponse search = template.opsForFaceDetect()
        .searchToken(faceToken, FaceSearchOptions.builder().returnLandmark(1).build());
```

### 8.2 人脸库操作

```java
FacesetBo faceset = new FacesetBo();
faceset.setDisplayName("test set");
faceset.setOuterId("test_set");
faceset.setTags("demo");

FacesetCreateResponse created = template.opsForFaceset().createFaceset(faceset);
String facesetToken = created.getFacesetToken();

FaceAddResponse added = template.opsForFaceset()
        .addFaceWithToken(facesetToken, "faceToken1", "faceToken2");
```

### 8.3 API 端点覆盖

| Face++ API | SDK 方法 |
|:---|:---|
| `/facepp/v3/detect` | `detectUrl/Base64/File` |
| `/facepp/v3/face/analyze` | `analyze(faceTokens, options)` |
| `/facepp/v3/compare` | `compareUrl/Token/Base64/File` |
| `/facepp/v3/search` | `searchUrl/Token/Base64/File` |
| `/facepp/v3/faceset/create|update|delete` | `createFaceset` / `updateFaceset` / `deleteFaceset` |
| `/facepp/v3/faceset/getfacesets|getdetail` | `getFacesetList` / `getFacesetByToken/OuterId` |
| `/facepp/v3/faceset/addface|removeface` | `addFaceWithToken/OuterId`、`removeFaceByToken/OuterId` |
| `/facepp/v3/face/setuserid|getdetail` | `createFace(faceToken, userId)` / `getFaceDetail(faceToken)` |
| `/facepp/v3/faceset/async/addface|removeface`、`task_status` | 异步人脸库操作 |
| `/facepp/v1/skinanalyze(_advanced|_pro)` | `skinAnalyzeUrl/Base64/File` |

> **假设：** 端点常量维护在 `FaceppApiAddress` 中；具体路径请对照 Face++ 控制台文档中对应 API 版本核实。

<a id="9-testing--build"></a>
## 9. 测试与构建

```bash
./mvnw clean verify        # 编译、运行测试、生成覆盖率报告
./mvnw clean install       # 安装到本地仓库
```

- 仓库当前不含测试源码。
- 覆盖率由 JaCoCo Maven 插件度量（目标：90% 行覆盖率，`haltOnFailure=false`）。
- `release` profile 组装 GPG 签名 + 源码 + Javadoc + 部署（`./mvnw -Prelease clean deploy`）。

<a id="10-versioning--branches"></a>
## 10. 版本线与分支

仓库维护三条并行版本线：

| 分支 | JDK | 版本号模式 |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

维护策略：在 JDK 8 作为基线的同时，1.0.x 版本线接收缺陷修复；新功能开发主要面向 2.0.x / 3.0.x 版本线。

<a id="11-contributing--license"></a>
## 11. 参与贡献与许可协议

欢迎参与贡献——请通过 Issue 反馈问题，或向对应版本线分支提交 Pull Request（JDK 17 相关改动提交到 `feature/2.0.x`）。

本项目基于 [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) 许可发布。详见仓库根目录的 `LICENSE` 文件。
