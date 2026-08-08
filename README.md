# faceplusplus-java-sdk

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://github.com/easy-4-java/faceplusplus-java-sdk) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](https://www.apache.org/licenses/LICENSE-2.0.txt)

A Java SDK for the Face++ (Megvii) face recognition API. Template-style operations for face detection, analysis, comparison, search, skin analysis and faceset (face group) management, powered by OkHttp 3 and Jackson.

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

`faceplusplus-java-sdk` wraps the Face++ REST API (`/facepp/v3/*`, `/facepp/v1/skinanalyze*`) in a small template-style API: `FaceppTemplate` exposes typed operation groups, `FaceppFaceOperations` / `FaceppFacesetOperations` implement the calls over `FaceppOkHttp3Template` (OkHttp 3 + Jackson), and typed response classes model the API results.

| What it is | What it is not |
|:---|:---|
| A typed client for the Face++ face recognition API | A Spring Boot starter (no auto-configuration) |
| Synchronous + async operation variants (face / faceset) | A face-detection implementation (images are sent to the Face++ cloud) |
| URL / Base64 / file input for images | A general HTTP client framework |

Typical use cases:

| Use case | Operations |
|:---|:---|
| Face detection & analysis | `detectUrl/Base64/File`, `analyze` |
| Face comparison | `compareUrl/Token/Base64/File` |
| Face search in a faceset | `searchUrl/Token/Base64/File` |
| Faceset management | `createFaceset`, `updateFaceset`, `getFacesetList`, `getFacesetByToken/OuterId`, `addFaceWithToken/OuterId`, `removeFaceByToken/OuterId`, `getFaceDetail` |
| Skin analysis | `skinAnalyzeUrl/Base64/File` (basic / advanced / pro) |
| Async batch face management | `FaceppFaceAsyncOperations` / `FaceppFacesetAsyncOperations` |

**Project status:** active development.

## 2. Features & Status

| Feature | Status | Notes |
|:---|:---|:---|
| `FaceppTemplate` | Available | Entry point: `opsForFaceDetect()` / `opsForFaceset()` |
| `FaceppFaceOperations` | Available | Detect / analyze / compare / search / skin-analyze with URL, Base64 or `File` input |
| `FaceppFacesetOperations` | Available | Faceset CRUD, add/remove faces (token or outerId), face detail, set user id |
| Async variants | Available | `FaceppFaceAsyncOperations`, `FaceppFacesetAsyncOperations` |
| `FaceppOkHttp3Template` | Available | OkHttp 3 + Jackson HTTP layer: `post` / `get` / `doRequest` overloads, typed response mapping |
| `FaceppProperties` | Available | Host, app credentials, OSS region, view size, token expiration (default 3600 s) |
| Typed responses | Available | `FaceDetectResponse`, `FaceCompareResponse`, `FaceSearchResponse`, `Faceset*Response`, `FaceppResponse.isSuccess()`, ... |
| Request options | Available | `FaceDetectOptions` (landmark, attributes, beauty score range), `FaceAnalyzeOptions`, `FaceSearchOptions`, `SkinAnalyzeOptions`, `FacesetBo` |
| Unit tests | Not present | No test sources in the repository |
| CI pipeline | Not configured | No CI workflow files in the repository |

## 3. Requirements & Compatibility

| Requirement | Version |
|:---|:---|
| JDK | 8 |
| Maven | 3.0+ |
| OkHttp | 4.9.3 |
| Jackson | 2.17.2 (`jackson-databind`) |
| Face++ API | Face++ v3 face API (`api-cn.faceplusplus.com`) |

### Version lines

| Branch | JDK | Version pattern |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
  Your code                     faceplusplus-java-sdk                  Face++ cloud
  ---------                     ---------------------                  ------------
  FaceppProperties  ->  FaceppTemplate
                             |
              +--------------+--------------+
              |                             |
      FaceppFaceOperations           FaceppFacesetOperations
      (+Async)                       (+Async)
              |                             |
              +------------> FaceppOkHttp3Template <------------+
                                 (OkHttp 3 + Jackson)            |
                                       |                         |
                                       +--> POST /facepp/v3/* ---+
                                              (api-cn.faceplusplus.com)
                                       |
                                       v
                              typed response classes (resp/*)
```

Single module, jar packaging:

| Package | Responsibility |
|:---|:---|
| `com.faceplusplus.spring.boot` | `FaceppTemplate`, `FaceppProperties`, `FaceppOkHttp3Template`, operation classes, constants |
| `com.faceplusplus.spring.boot.req` | Typed request options (`FaceDetectOptions`, `FacesetBo`, ...) |
| `com.faceplusplus.spring.boot.resp` | Typed response models (`FaceppResponse` base, detect/compare/search/faceset responses, ...) |

## 5. Installation

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

**Availability:** the artifact is published to the Aliyun private Maven repository and distributed through GitHub Releases; it has not yet been published to Maven Central.

## 6. Quick Start

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

Expected result: the detected faces (with landmarks/attributes when requested) are returned in the typed `FaceDetectResponse`; `isSuccess()` reflects the Face++ `error_message`/`code` contract.

## 7. Configuration

Configuration is held in `FaceppProperties`:

| Property | Default | Description |
|:---|:---|:---|
| `host` | `https://api-cn.faceplusplus.com` | API base URL |
| `appId` | — | Face++ API Key |
| `appCertificate` | — | Face++ API Secret |
| `expirationTimeInSeconds` | `3600` | Token expiration (seconds) |
| `loginKey` / `loginSecret` | — | Login credentials (optional) |
| `ossRegion` | — | OSS region for cloud storage (optional) |
| `viewWidth` / `viewHeight` | — | View size (optional) |

Credentials are supplied by the application; keep them out of source control.

## 8. Core Usage / API

### 8.1 Face operations

```java
// Detect from a local file
FaceDetectResponse detect = template.opsForFaceDetect()
        .detectFile(new File("face.jpg"), options);

// Compare two images by URL
FaceCompareResponse compare = template.opsForFaceDetect()
        .compareUrl("https://a.example/1.jpg", "https://b.example/2.jpg");

// Search within a faceset (by face token)
FaceSearchResponse search = template.opsForFaceDetect()
        .searchToken(faceToken, FaceSearchOptions.builder().returnLandmark(1).build());
```

### 8.2 Faceset operations

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

### 8.3 API endpoint coverage

| Face++ API | SDK method |
|:---|:---|
| `/facepp/v3/detect` | `detectUrl/Base64/File` |
| `/facepp/v3/face/analyze` | `analyze(faceTokens, options)` |
| `/facepp/v3/compare` | `compareUrl/Token/Base64/File` |
| `/facepp/v3/search` | `searchUrl/Token/Base64/File` |
| `/facepp/v3/faceset/create|update|delete` | `createFaceset` / `updateFaceset` / `deleteFaceset` |
| `/facepp/v3/faceset/getfacesets|getdetail` | `getFacesetList` / `getFacesetByToken/OuterId` |
| `/facepp/v3/faceset/addface|removeface` | `addFaceWithToken/OuterId`, `removeFaceByToken/OuterId` |
| `/facepp/v3/face/setuserid|getdetail` | `createFace(faceToken, userId)` / `getFaceDetail(faceToken)` |
| `/facepp/v3/faceset/async/addface|removeface`, `task_status` | Async faceset operations |
| `/facepp/v1/skinanalyze(_advanced|_pro)` | `skinAnalyzeUrl/Base64/File` |

> **Assumption:** endpoint constants are maintained in `FaceppApiAddress`; verify the exact paths against the Face++ console documentation for your API version.

## 9. Testing & Build

```bash
./mvnw clean verify        # compile, run tests, generate coverage report
./mvnw clean install       # install into the local repository
```

- The repository currently contains no test sources.
- Coverage is measured with the JaCoCo Maven plugin (target: 90% line coverage, `haltOnFailure=false`).
- The `release` profile assembles GPG signing + sources + Javadoc + deployment (`./mvnw -Prelease clean deploy`).

## 10. Versioning & Branches

Three parallel version lines are maintained:

| Branch | JDK | Version pattern |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

Maintenance strategy: the 1.0.x line receives bug fixes while JDK 8 remains the baseline; feature development primarily targets the 2.0.x / 3.0.x lines.

## 11. Contributing & License

Contributions are welcome — open an issue or submit a pull request against the matching version-line branch (`feature/2.0.x` for JDK 17 changes).

This project is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0). See the `LICENSE` file in the repository root for details.
